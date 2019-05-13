package com.jgw.supercodeplatform.marketing.service.user;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.enums.portrait.PortraitTypeEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayTradeNoGenerator;

@Service
public class MarketingMembersService extends AbstractPageService<MarketingMembersListParam> {
	protected static Logger logger = LoggerFactory.getLogger(MarketingMembersService.class);
	@Value("${cookie.domain}")
	private String cookieDomain;
	//	@Value( "${注册短信模板外部配置key}")
	@Value( "亲爱的{{user}},恭喜成功注册成为{{organization}}的会员")
	private  String registerMsgContent ;
	@Value("${rest.user.url}")
	private String userServiceUrl;
	@Autowired
	private MarketingMembersMapper marketingMembersMapper;

	@Autowired
	private OrganizationPortraitMapper organizationPortraitMapper;

	@Autowired
	private MarketingActivitySetMapper mSetMapper;

	@Autowired
	private MarketingPrizeTypeMapper mMarketingPrizeTypeMapper;

	@Autowired
	private MarketingActivityMapper mActivityMapper;

	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;


	@Autowired
	private MarketingMembersWinRecordMapper mWinRecordMapper;

	@Autowired
	private WXPayTradeOrderMapper wXPayTradeOrderMapper;

	@Autowired
	private IntegralRuleMapperExt integralRuleDao;
	
	@Autowired
	RedisLockUtil lock;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private WXPayService wxpService;

	@Autowired
	private CodeEsService codeEsService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OrganizationPortraitService organizationPortraitService;

	@Autowired
	private GlobalRamCache globalRamCache;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IntegralRecordMapperExt integralRecordDao;

	@Value("${marketing.server.ip}")
	private String serverIp;

	@Autowired
	private WXPayTradeNoGenerator wXPayTradeNoGenerator ;

	private static SimpleDateFormat staticESSafeFormat=new SimpleDateFormat("yyyy-MM-dd");


	@Override
	protected List<Map<String, Object>> searchResult(MarketingMembersListParam searchParams) throws Exception {

		String listSQl = listSql(searchParams,false);
		List<Map<String, Object>> data=marketingMembersMapper.dynamicList(listSQl);
		return data;
	}

	/**
	 * 动态处理标签信息和画像信息
	 * @param searchParams
	 * @param isCount
	 * @return
	 * @throws SuperCodeException
	 */
	private String listSql(MarketingMembersListParam searchParams,boolean isCount) throws SuperCodeException {
		String organizationId=commonUtil.getOrganizationId();
		// 获取画像和标签
		List<MarketingOrganizationPortraitListParam> mPortraitListParams=organizationPortraitMapper.getSelectedPortraitAndLabel(organizationId);
		if (null==mPortraitListParams || mPortraitListParams.isEmpty()) {
			throw new SuperCodeException("企业未设置画像", 500);
		}
		StringBuffer fieldsbuf=new StringBuffer();
		StringBuffer commonSearchbuf=new StringBuffer();
		int i=0;
		boolean commonsearch=false;
		String search=searchParams.getSearch();
		if (StringUtils.isNotBlank(search)) {
			commonsearch=true;
			commonSearchbuf.append(" AND (");
		}
		fieldsbuf.append("Id,State,Openid,WxName,date_format(RegistDate ,'%Y-%m-%d %H:%i:%S') RegistDate,");
		for (MarketingOrganizationPortraitListParam marketingOrganizationPortraitListParam : mPortraitListParams) {
			String code=marketingOrganizationPortraitListParam.getCodeId();
			if( "birthday".equalsIgnoreCase(code)){
				fieldsbuf.append(" date_format(birthday ,'%Y-%m-%d' ) Birthday ");

			} else if("babyBirthday".equalsIgnoreCase(code)){
				fieldsbuf.append(" date_format(babyBirthday ,'%Y-%m-%d' ) BabyBirthday ");
			// TODO 这个月份是按天【DATEDIFF】计算还是跨月【period_dif，%Y%mf】计算
			}else if("NoIntegralWithOneMonth".equalsIgnoreCase(code)){
				fieldsbuf.append(" if(period_diff(date_format(now(),'%Y%m'),date_format(IntegralReceiveDate, '%Y%m')) <= 1 ,1,0) NoIntegralWithOneMonth ");

			}else if("NoIntegralWithThreeMonth".equalsIgnoreCase(code)){
				fieldsbuf.append(" if(period_diff(date_format(now(),'%Y%m'),date_format(IntegralReceiveDate, '%Y%m')) <= 3 ,1,0) NoIntegralWithThreeMonth ");

			}else if("NoIntegralWithSixMonth".equalsIgnoreCase(code)){
			    // if(DATEDIFF(now(),IntegralReceiveDate)<=180,1,0 ) NoIntegralWithOneMonth  1,表示6月内有领取，0表示没有
				fieldsbuf.append(" if(period_diff(date_format(now(),'%Y%m'),date_format(IntegralReceiveDate, '%Y%m')) <= 6 ,1,0) NoIntegralWithSixMonth ");

			}else{
				fieldsbuf.append(code);

			}
			if(i<mPortraitListParams.size()-1) {
				fieldsbuf.append(",");
			}
			if (commonsearch) {
				if (i>0) {
					commonSearchbuf.append(" OR ");
				}
				commonSearchbuf.append(code).append(" like ");
				if (code.contains("Date")) {
					commonSearchbuf.append("binary");
				}
				commonSearchbuf.append("CONCAT('%',").append("'").append(search).append("'").append(",'%')");
				if(i==mPortraitListParams.size()-1) {
					commonSearchbuf.append(")");
				}
			}
			i++;
		}
		String from=" from marketing_members ";
		String where=" where State!=2 and OrganizationId='"+organizationId+"'";
		String sql=null;
		if (isCount) {
			sql=" select count(*) "+from+where;
			if (commonsearch) {
				sql+=commonSearchbuf.toString();
			}
		}else {
			Integer startNum=searchParams.getStartNumber();
			Integer pagesize=searchParams.getPageSize();
			sql=" select "+fieldsbuf.toString()+from+where;
			if (commonsearch) {
				sql+=commonSearchbuf.toString();
			}
			if (null!=startNum && null!=pagesize) {
				sql+=" order by RegistDate desc limit "+startNum+","+pagesize;
			}
		}
		return sql;
	}

	@Override
	protected int count(MarketingMembersListParam searchParams) throws Exception {
		String listSQl = listSql(searchParams,true);
		Integer count=marketingMembersMapper.dynamicCount(listSQl);
		return count;
	}

	/**
	 * 会员注册
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int addMember(MarketingMembersAddParam marketingMembersAddParam) throws Exception{
		String mobile = marketingMembersAddParam.getMobile();
		String redisPhoneCode=redisUtil.get(RedisKey.phone_code_prefix+ mobile);
		if (StringUtils.isBlank(redisPhoneCode) ) {
			throw new SuperCodeException("验证码不存在或已过期请重新获取验证码",500);
		}

		if (!redisPhoneCode.equals(marketingMembersAddParam.getVerificationCode())) {
			throw new SuperCodeException("验证码不正确",500);
		}
		String organizationId=marketingMembersAddParam.getOrganizationId();
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("组织id获取失败", 500);
		}
		List<MarketingOrganizationPortraitListParam> selectedPortrait = organizationPortraitService.getSelectedPortrait(organizationId);
		if(CollectionUtils.isEmpty(selectedPortrait) ){
			throw new SuperCodeException("组织id不存在", 500);

		}
		if(StringUtils.isBlank(marketingMembersAddParam.getBabyBirthday())){
			marketingMembersAddParam.setBabyBirthday(null);
		}

		if(StringUtils.isBlank(marketingMembersAddParam.getBirthday())){
			marketingMembersAddParam.setBirthday(null);
		}
		// 校验是否已经注册
		Map<String, Object> map = new HashMap<>();
		map.put("organizationId",organizationId);
		map.put("mobile", mobile);
		Integer allMarketingMembersCount = marketingMembersMapper.getAllMarketingMembersCount(map);

		if(allMarketingMembersCount >= 1){
			logger.error(mobile + "手机号已注册");
			throw  new SuperCodeException("手机号已注册",500);
		}
		String userId = getUUID();
		marketingMembersAddParam.setUserId(userId);
		marketingMembersAddParam.setState(1);
		MarketingMembers members=modelMapper.map(marketingMembersAddParam,MarketingMembers.class);
		members.setIsRegistered((byte)1);//手机号注册默认为已完善过信息
		int result = marketingMembersMapper.insert(members);
		// 调用用户模块发送短信
		if(1 == result){
			IntegralRule rule=integralRuleDao.selectByOrgId(organizationId);
			if (null!=rule) {
				Byte registerState=rule.getIntegralByRegisterStatus();
				if (null!=registerState && registerState.intValue()==1) {
					members.setHaveIntegral(rule.getIntegralByRegister());
					marketingMembersMapper.update(members);
					 IntegralRecord integralRecord=new IntegralRecord();
					 integralRecord.setIntegralNum(rule.getIntegralByRegister());
					 integralRecord.setIntegralReasonCode(IntegralReasonEnum.REGISTER_MEMBER.getIntegralReasonCode());
					 integralRecord.setIntegralReason(IntegralReasonEnum.REGISTER_MEMBER.getIntegralReason());
					 integralRecord.setCustomerId(members.getCustomerId());
					 integralRecord.setCustomerName(members.getCustomerName());
					 integralRecord.setMemberId(members.getId());
					 integralRecord.setMemberName(members.getUserName());
					 integralRecord.setMemberType(members.getMemberType());
					 integralRecord.setMobile(members.getMobile());
					 integralRecord.setOrganizationId(organizationId);
					 integralRecord.setIntegralType(0);
					 integralRecordDao.insert(integralRecord);
				}
			}
			String msg = msgTimplate(marketingMembersAddParam.getUserName(),selectedPortrait.get(0).getOrganizationFullName());
			sendRegisterMessage(mobile,msg);

		}else {
			throw  new SuperCodeException("保存注册数据失败",500);
		}
		return  result;
	}

	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param msg 短信内容
	 */
	public void sendRegisterMessage(String mobile, String msg) throws UnsupportedEncodingException {

		try {
			Map msgData = new HashMap();
			msgData.put("mobileId",mobile);
			msgData.put("sendContent",msg);
			// 发送短信
			restTemplate.postForEntity(userServiceUrl + WechatConstants.SMS_SEND_PHONE_MESSGAE, msgData, RestResult.class);

		} catch (Exception e) {
			if(logger.isInfoEnabled()){
				logger.info("注册用户的短信欢迎信息发送失败"+e.getMessage());
			}
			e.printStackTrace();
		}

	}

	/**
	 * 注册短信内容模板生成器
	 * @param userName
	 * @param organizationFullName
	 * @return
	 */
	private String msgTimplate(String userName, String organizationFullName) {
		return  registerMsgContent.replace("{{user}}",userName).replace("{{organization}}",organizationFullName);

	}


	/**
	 * 条件查询会员数量
	 * @param map
	 * @return
	 */
	public Integer getAllMarketingMembersCount(Map<String,Object> map){
		return marketingMembersMapper.getAllMarketingMembersCount(map);
	}

	/**
	 * 修改会员信息
	 * @param map
	 * @return
	 */
	public int updateMembers(MarketingMembersUpdateParam membersUpdateParam) throws SuperCodeException{
		if(membersUpdateParam == null || membersUpdateParam.getId() == null || membersUpdateParam.getId() <= 0){
			throw new SuperCodeException("完善信息未获取到会员唯一性ID",500);
		}
		if(StringUtils.isBlank(membersUpdateParam.getCustomerId()) &&  !StringUtils.isBlank(membersUpdateParam.getCustomerName())){
			throw new SuperCodeException("门店编码和名称信息丢失：门店编码",500);
		}
		if(!StringUtils.isBlank(membersUpdateParam.getCustomerId()) &&  StringUtils.isBlank(membersUpdateParam.getCustomerName())){
			throw new SuperCodeException("门店编码和名称信息丢失：门店名称",500);
		}

		if("".equals(membersUpdateParam.getBabyBirthday())){
			membersUpdateParam.setBabyBirthday(null);
		}
		if("".equals(membersUpdateParam.getBirthday())){
			membersUpdateParam.setBirthday(null);
		}
		MarketingMembers members=new MarketingMembers();
		// datetime类型处理
		if(!StringUtils.isBlank(membersUpdateParam.getBabyBirthday())){
			members.setBabyBirthday(membersUpdateParam.getBabyBirthday());

		}
		if(!StringUtils.isBlank(membersUpdateParam.getBirthday())){
			members.setBirthday(membersUpdateParam.getBirthday());
		}
		members.setCustomerId(membersUpdateParam.getCustomerId());
		members.setCustomerName(membersUpdateParam.getCustomerName());
		members.setMobile(membersUpdateParam.getMobile());
		members.setOpenid(membersUpdateParam.getOpenid());
		members.setpCCcode(membersUpdateParam.getpCCcode());
		members.setSex(membersUpdateParam.getSex());
		members.setState(membersUpdateParam.getState());
		members.setWxName(membersUpdateParam.getWxName());
		members.setUserName(membersUpdateParam.getUserName());
		members.setId(membersUpdateParam.getId());
		members.setIsRegistered((byte)1);
		return marketingMembersMapper.update(members);
	}

	/**
	 * 修改会员状态
	 * @param map
	 * @return
	 * @throws SuperCodeException
	 */
	public int updateMembersStatus(Long id,int status) throws SuperCodeException{
		return marketingMembersMapper.updateMembersStatus(id,status);
	}

	/**
	 * 获取单个会员信息
	 * @param map
	 * @return
	 * @throws SuperCodeException
	 */
	public MarketingMembers getMemberById(Long id) throws SuperCodeException{
		return marketingMembersMapper.getMemberById(id);
	}

	public MarketingMembers selectByOpenIdAndOrgId(String openid, String organizationId) {
		return marketingMembersMapper.selectByOpenIdAndOrgId(openid,organizationId);
	}
	/**
	 * h5页面登录接口--既然已经执行登录接口那肯定是该活动中奖页设置了手机登录（通过活动设置id查询中奖页信息得知）
	 * @param mobile
	 * @param openId
	 * @param activitySetId
	 * @param verificationCode
	 * @param openid 
	 * @param organizationId 
	 * @param response 
	 * @return
	 * @throws SuperCodeException
	 *
	 * 1、根据手机号和组织id查
	 *   1.1、查的到则说明该用户注册过(再根据用户已注册字段确认)--这时需要把openid的记录和手机号两条记录合并
	 *   1.2、如果查不到
	 *      1.2.1、用户注册过但手机号换了---根据openid查，如果查出的记录用户已注册字段为是则验证该情况
	 *      1.2.2、用户未注册                       ---根据openid查，如果查出的记录用户已注册字段为否则验证该情况
	 *

	 */

	public RestResult<H5LoginVO> login(String mobile, String wxstate, String verificationCode, String openid, String organizationId, HttpServletResponse response) throws SuperCodeException {
		RestResult<H5LoginVO> restResult=new RestResult<H5LoginVO>();
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(verificationCode)) {
			restResult.setState(500);
			restResult.setMsg("请检查参数，手机号和验证码参数不能为空");
			return restResult;
		}

		String redisPhoneCode=redisUtil.get(RedisKey.phone_code_prefix+mobile);
		if (StringUtils.isBlank(redisPhoneCode) ) {
			restResult.setState(500);
			restResult.setMsg("验证码不存在或已过期请重新获取验证码");
			return restResult;
		}

		if (!redisPhoneCode.equals(verificationCode)) {
			restResult.setState(500);
			restResult.setMsg("验证码不正确");
			return restResult;
		}
		
		List<MarketingOrganizationPortraitListParam> mPortraits = organizationPortraitMapper
				.getSelectedPortrait(organizationId, PortraitTypeEnum.PORTRAIT.getTypeId());
		if (null == mPortraits || mPortraits.isEmpty()) {
			throw new SuperCodeException("登录时获取企业画像设置为空，无法进行后续逻辑", 500);
		}
		
		H5LoginVO h5LoginVO =null;
		if (StringUtils.isNotBlank(wxstate)) {
			 h5LoginVO = loginWithWxstate(mobile, wxstate,mPortraits.size());
			restResult.setResults(h5LoginVO);
		}else {
			h5LoginVO = commonLogin(mobile, openid, organizationId,mPortraits.size());
		}
		try {
			String jwtToken=JWTUtil.createTokenWithClaim(h5LoginVO);
			Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
			// jwt有效期为2小时，保持一致
			jwtTokenCookie.setMaxAge(60*60*2);
			// 待补充： 其他参数基于传递状况
			// jwtTokenCookie.setPath();
			jwtTokenCookie.setPath("/");
			jwtTokenCookie.setDomain(cookieDomain);
			response.addCookie(jwtTokenCookie);
//			response.addHeader("jwt-token", jwtToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		restResult.setResults(h5LoginVO);
		restResult.setState(200);
		restResult.setMsg("登录成功...");
		return restResult;
	}

	private H5LoginVO commonLogin(String mobile, String openid, String organizationId, int portraitsSize) throws SuperCodeException {
		H5LoginVO h5LoginVO;
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("积分领取登录时组织id必传", 500);
		}
		h5LoginVO=new H5LoginVO();
		h5LoginVO.setMobile(mobile);			//积分登录openid不一定存在
		MarketingMembers trueMember=null;
		
		if (StringUtils.isBlank(openid)) {
			MarketingMembers memberByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
			if (null==memberByPhone) {
				memberByPhone=new MarketingMembers();
				memberByPhone.setMobile(mobile);
				memberByPhone.setState((byte)1);
				memberByPhone.setHaveIntegral(0);
				if (portraitsSize>1) {
					memberByPhone.setIsRegistered((byte)0);
				}else {
					memberByPhone.setIsRegistered((byte)1);
				}
				memberByPhone.setMemberType((byte)0);
				memberByPhone.setOrganizationId(organizationId);
				marketingMembersMapper.insert(memberByPhone);
				
				trueMember=memberByPhone;
			}
		}else {
			MarketingMembers memberByOpenId=marketingMembersMapper.selectByOpenIdAndOrgId(openid, organizationId);
			if (null==memberByOpenId) {
				throw new SuperCodeException("登录失败，无此微信用户", 500);
			}
			
			Byte state=memberByOpenId.getState()==null?1:memberByOpenId.getState();//如果是null可能是之前创建用户时忘记设置值的可能性更大则默认为合法用户
			//如果当前微信用户已存在且状态为1 则判断手机号是否一致
			if (state.byteValue()!=2) {
				String exMobile=memberByOpenId.getMobile();
				if (!mobile.equals(exMobile)) {
					throw new SuperCodeException("登录的手机号与当前微信用户手机号不一致无法登录", 500);
				}
				if (state.byteValue()==0) {
					throw new SuperCodeException("您已被禁用，请联系管理员", 500);
				}
				//设置用户
				trueMember=memberByOpenId;
			}else{
				//如果state为2则表示该用户未激活刚刚授权状态
				
				//判断手机号用户是否存在，判断手机号用户是否已绑定微信号，判断手机号用户的微信号是否与登录微信号一直
				MarketingMembers memberByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
				if (null==memberByPhone) {
					//如果手机号用户不存在，微信用户又是未激活则表明这个是彻底的新用户
					memberByOpenId.setMobile(mobile);
					memberByOpenId.setState((byte)1);
					marketingMembersMapper.update(memberByOpenId);
					
					//设置用户
					trueMember=memberByOpenId;
				}else {
					String exOpenid=memberByPhone.getOpenid();
					if (StringUtils.isBlank(exOpenid)) {
						//微信号用户为未激活刚注册用户且如果当前手机号用户未绑定微信号但被禁用了则不允许登录
						if (memberByPhone.getState().byteValue()==0) {
							throw new SuperCodeException("当前手机号用户已被禁用，请联系管理员", 500);
						}
						//如果已存在的手机号用户未绑定微信号则直接绑定手机号
						memberByPhone.setOpenid(openid);
						memberByPhone.setWxName(memberByOpenId.getWxName());
						memberByPhone.setWechatHeadImgUrl(memberByOpenId.getWechatHeadImgUrl());
						marketingMembersMapper.update(memberByPhone);
					}else {
						//如果已存在的手机号用户已绑定了微信openid且与当前登录openid不一致则不允许登录
						if (!exOpenid.equals(openid)) {
							throw new SuperCodeException("登录的手机号已绑定其它微信号不可以登录当前微信号", 500);
						}
						if (memberByPhone.getState().byteValue()==0) {
							throw new SuperCodeException("当前手机号用户已被禁用，请联系管理员", 500);
						}
					}
					
					//设置用户
					trueMember=memberByPhone;
				}
			}
		}
		h5LoginVO.setWechatHeadImgUrl(trueMember.getWechatHeadImgUrl());
		h5LoginVO.setMemberId(trueMember.getId());
		h5LoginVO.setHaveIntegral(trueMember.getHaveIntegral()==null?0:trueMember.getHaveIntegral());
		h5LoginVO.setRegistered(trueMember.getIsRegistered().intValue());
		return h5LoginVO;
	}
	
    /**
     * 扫描产品防伪码登录
     * @param mobile
     * @param wxstate
     * @param portraitsSize 
     * @return
     * @throws SuperCodeException
     */
	private H5LoginVO loginWithWxstate(String mobile, String wxstate, int portraitsSize) throws SuperCodeException {
		ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(wxstate);
		if (null == scanCodeInfoMO) {
			throw new SuperCodeException("参数wxstate对应的后台扫码缓存信息不存在，请重新扫码", 500);
		}
		// 登录时候保存手机信息用于后续接口获取
		scanCodeInfoMO.setMobile(mobile);
		globalRamCache.putScanCodeInfoMO(wxstate, scanCodeInfoMO);

		Long activitySetId = scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet maActivitySet = mSetMapper.selectById(activitySetId);
		if (null == maActivitySet) {
			throw new SuperCodeException("该活动设置id不存在", 500);
		}
		String organizationId = maActivitySet.getOrganizationId();
		H5LoginVO h5LoginVO = commonLogin(mobile, scanCodeInfoMO.getOpenId(), organizationId,portraitsSize);
		return h5LoginVO;
	}
	/**
	 * 点击中奖逻辑
	 * @param activitySetId
	 * @param openId
	 * @return
	 * @throws SuperCodeException
	 */
	public RestResult<String> lottery(String wxstate) throws SuperCodeException, ParseException {
		RestResult<String> restResult=new RestResult<String>();

		ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(wxstate);
		if (null==scanCodeInfoMO) {
			restResult.setState(500);
			restResult.setMsg("不存在扫码唯一纪录="+wxstate+"的扫码缓存信息，请重新扫码");
			return restResult;
		}

		// 手机校验,抛出异常
		String mobile=scanCodeInfoMO.getMobile();
		if (StringUtils.isNotBlank(mobile)) {
			checkPhoneFormat(mobile);
		}

		String openId=scanCodeInfoMO.getOpenId();
		if (StringUtils.isBlank(openId)) {
			restResult.setState(500);
			restResult.setMsg("openId参数不能为空");
			return restResult;
		}
		Long activitySetId=scanCodeInfoMO.getActivitySetId();

		MarketingActivitySet mActivitySet=mSetMapper.selectById(activitySetId);
		if (null==mActivitySet) {
			restResult.setState(500);
			restResult.setMsg("该活动设置不存在");
			return restResult;
		}

		MarketingActivity activity=mActivityMapper.selectById(mActivitySet.getActivityId());
		if (null==activity) {
			restResult.setState(500);
			restResult.setMsg("该活动设置对应的活动不存在");
			return restResult;
		}

		MarketingMembers marketingMembersInfo = marketingMembersMapper.selectByOpenIdAndOrgId(openId, scanCodeInfoMO.getOrganizationId());
		if(marketingMembersInfo == null){
			throw  new SuperCodeException("会员信息不存在",500);
		}
		if( null!=marketingMembersInfo.getState() && marketingMembersInfo.getState() == 0){
			throw  new SuperCodeException("对不起,该会员已被加入黑名单",500);
		}

		Integer consumeIntegralNum=mActivitySet.getConsumeIntegralNum();
		Integer haveIntegral=marketingMembersInfo.getHaveIntegral();
		if (null!=consumeIntegralNum) {
			if (null==haveIntegral || haveIntegral.intValue()<consumeIntegralNum.intValue()) {
				throw  new SuperCodeException("对不起,领取本活动需要消耗"+consumeIntegralNum+"积分，您的积分不够",500);
			}
		}
		
		String organizationId=mActivitySet.getOrganizationId();
		MarketingWxMerchants mWxMerchants=mWxMerchantsMapper.selectByOrganizationId(organizationId);
		if (null==mWxMerchants) {
			restResult.setState(500);
			restResult.setMsg("当前企业未绑定公众号数据");
			return restResult;
		}

		String codeId=scanCodeInfoMO.getCodeId();
		String codeTypeId=scanCodeInfoMO.getCodeTypeId();

		//同步代码块**很重要，要先查询该码此时是不是被其它用户已扫过，如果扫过就不能发起微信支付等操作
		MarketingPrizeTypeMO mPrizeTypeMO =null;
		boolean acquireLock = false;
 		try {
			// 超时时间,重试次数，重试间隔
			acquireLock = lock.lock(activitySetId + ":" + codeId + ":" + codeTypeId,5000,5,200);
			if(acquireLock){
				List<MarketingPrizeTypeMO> moPrizeTypes=mMarketingPrizeTypeMapper.selectMOByActivitySetIdIncludeUnreal(activitySetId);
				if (null==moPrizeTypes || moPrizeTypes.isEmpty()) {
					restResult.setState(500);
					restResult.setMsg("该活动未设置中奖奖次");
					return restResult;
				}
				mPrizeTypeMO=LotteryUtilWithOutCodeNum.startLottery(moPrizeTypes);
				String nowTime=staticESSafeFormat.format(new Date());
				long nowTtimeStemp=staticESSafeFormat.parse(nowTime).getTime();
				Long codeCount=codeEsService.countByCode(codeId, codeTypeId);
				String opneIdNoSpecialChactar=CommonUtil.replaceSpicialChactar(openId);
				logger.info("领取方法=====：根据codeId="+codeId+",codeTypeId="+codeTypeId+"获得的扫码记录次数为="+codeCount);
				//校验码有没有被扫过
				if (null==codeCount ||codeCount.intValue()<1) {
					Integer scanLimit=mActivitySet.getEachDayNumber();
					//校验有没有设置活动用户扫码量限制
					if (null!=scanLimit&& scanLimit.intValue()>0) {
						Long userscanNum=codeEsService.countByUserAndActivityQuantum(opneIdNoSpecialChactar, activitySetId, nowTtimeStemp);
						logger.info("领取方法=====：根据openId="+opneIdNoSpecialChactar+",activitySetId="+activitySetId+",nowTime="+nowTime+"获得的用户扫码记录次数为="+userscanNum+",当前活动扫码限制次数为："+scanLimit);
						if (null!=userscanNum && userscanNum.intValue()>=scanLimit.intValue()) {
							restResult.setState(500);
							restResult.setMsg("您今日扫码已超过该活动限制数量");
							return restResult;
						}

					}
				}else {
					restResult.setState(200);
					restResult.setMsg("您手速太慢，刚刚该码已被其它用户扫过");
					return restResult;
				}

				//更新奖次被扫码数量
				mPrizeTypeMO.setWiningNum(mPrizeTypeMO.getWiningNum() + 1);
				MarketingPrizeType marketingPrizeType =new MarketingPrizeType();
				marketingPrizeType.setId(mPrizeTypeMO.getId());
				marketingPrizeType.setWiningNum(mPrizeTypeMO.getWiningNum());
				mMarketingPrizeTypeMapper.update(marketingPrizeType);

				codeEsService.addScanCodeRecord(opneIdNoSpecialChactar, scanCodeInfoMO.getProductId(), scanCodeInfoMO.getProductBatchId(), codeId, codeTypeId, activitySetId,nowTtimeStemp,organizationId);
				logger.info("领取方法====：抽奖数据已保存到es");
			}else {
				logger.error("{锁获取失败:" +activitySetId + codeId +codeTypeId+ ",请检查}");
				// 统计失败
				redisUtil.hmSet("marketing:lock:fail",activitySetId + codeId +codeTypeId,new Date());
				restResult.setState(500);
				restResult.setMsg("扫码人数过多,请稍后再试");
				return restResult;
			}
		}catch (Exception e) {
			e.printStackTrace();
			restResult.setState(500);
			restResult.setMsg(e.getLocalizedMessage());
			return restResult;
		}finally {
			if(acquireLock){
				try{
					// lua脚本
					lock.releaseLock(activitySetId + codeId +codeTypeId);
				}catch (Exception e){
					logger.error("{锁释放失败:" +activitySetId + codeId +codeTypeId+ ",请检查}");
					e.printStackTrace();
				}
			}
		}
		logger.info("抽奖数据已保存到es");
		//判断realprize是否为0,0表示不中奖
		Byte realPrize=mPrizeTypeMO.getRealPrize();
		if (realPrize.equals((byte)0)) {
			restResult.setState(200);
			restResult.setMsg("‘啊呀没中，一定是打开方式不对’：没中奖");
			globalRamCache.deleteScanCodeInfoMO(wxstate);
		}else if (realPrize.equals((byte)1)) {
			Byte awardType=mPrizeTypeMO.getAwardType();
			Float amount =null;

			try {
				if (null==awardType || awardType.byteValue()==4) {
					//如果是微信红包
					amount = weixinpay(mobile, openId, organizationId, mPrizeTypeMO);
					addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, amount);
				}else {
					Integer remainingStock=null;
					switch (awardType) {
					case 1://实物
						remainingStock=mPrizeTypeMO.getRemainingStock();
						if (null!=remainingStock && remainingStock.intValue()<1) {
							restResult.setMsg("‘啊呀没中，一定是打开方式不对’：没中奖");
						}else {
							restResult.setMsg("恭喜您，获得"+mPrizeTypeMO.getPrizeTypeName());
							addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, amount);
						}
						
						break;
					case 2: //奖券
						restResult.setResults(mPrizeTypeMO.getCardLink());
						break;
					case 3: //积分
						 Integer awardIntegralNum=mPrizeTypeMO.getAwardIntegralNum();
						 marketingMembersInfo.setHaveIntegral((haveIntegral==null?0:haveIntegral)+awardIntegralNum);
						 restResult.setMsg("恭喜您，获得"+awardIntegralNum+"积分");
						 addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, amount);
						break;
					case 9://其它
						remainingStock=mPrizeTypeMO.getRemainingStock();
						if (null!=remainingStock && remainingStock.intValue()<1) {
							restResult.setState(200);
							restResult.setMsg("‘啊呀没中，一定是打开方式不对’：没中奖");
						}else {
							restResult.setMsg("恭喜您，获得"+mPrizeTypeMO.getPrizeTypeName());
							addWinRecord(scanCodeInfoMO.getCodeId(), mobile, openId, activitySetId, activity, organizationId, mPrizeTypeMO, amount);
						}
						break;
					default:
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//一切ok后清除缓存
				globalRamCache.deleteScanCodeInfoMO(wxstate);
			}
			if (null!=consumeIntegralNum) {
				marketingMembersInfo.setHaveIntegral(marketingMembersInfo.getHaveIntegral()-consumeIntegralNum);
			}
			marketingMembersMapper.update(marketingMembersInfo);
			restResult.setState(200);
			restResult.setMsg(amount+"");
		}
		return restResult;
	}

	private void addWinRecord(String outCodeId, String mobile, String openId, Long activitySetId,
			MarketingActivity activity, String organizationId, MarketingPrizeTypeMO mPrizeTypeMO, Float amount) {
		//插入中奖纪录
		MarketingMembersWinRecord redWinRecord=new MarketingMembersWinRecord();
		redWinRecord.setActivityId(activity.getId());
		redWinRecord.setActivityName(activity.getActivityName());
		redWinRecord.setActivitySetId(activitySetId);
		redWinRecord.setMobile(mobile);
		redWinRecord.setOpenid(openId);
		redWinRecord.setPrizeTypeId(mPrizeTypeMO.getId());
		redWinRecord.setWinningAmount((float)amount );
		redWinRecord.setWinningCode(outCodeId);
		redWinRecord.setPrizeName(mPrizeTypeMO.getPrizeTypeName());
		redWinRecord.setOrganizationId(organizationId);
		mWinRecordMapper.addWinRecord(redWinRecord);
	}

	private Float weixinpay(String mobile, String openId, String organizationId, MarketingPrizeTypeMO mPrizeTypeMO)
			throws SuperCodeException, Exception {
		Float amount=mPrizeTypeMO.getPrizeAmount();
		Byte randAmount=mPrizeTypeMO.getIsRrandomMoney();
		//如果是随机金额则生成随机金额
		if (randAmount.equals((byte)1)) {
			float min=mPrizeTypeMO.getLowRand();
			float max=mPrizeTypeMO.getHighRand();
			// [ )
//				amount=new Random().nextFloat() * (max - min)+min;
// 保留两位小数
			float init = new Random().nextFloat() *((max-min)) +min;
			DecimalFormat decimalFormat=new DecimalFormat(".00");
			String strAmount=decimalFormat.format(init);//format 返回的是字符串
			amount = Float.valueOf(strAmount);
		}
		Float finalAmount = amount * 100;//金额转化为分

		logger.error("{ 中奖记录保存：手机号=> + " + mobile +"==}");

		//生成订单号
		String partner_trade_no=wXPayTradeNoGenerator.tradeNo();
		//保存订单
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WXPayTradeOrder tradeOrder=new WXPayTradeOrder();
		tradeOrder.setAmount(finalAmount);
		tradeOrder.setOpenId(openId);
		tradeOrder.setTradeStatus((byte)0);
		tradeOrder.setPartnerTradeNo(partner_trade_no);
		tradeOrder.setTradeDate(format.format(new Date()));
		tradeOrder.setOrganizationId(organizationId);
		wXPayTradeOrderMapper.insert(tradeOrder);

		String remoteAddr = request.getRemoteAddr();
		if (StringUtils.isBlank(remoteAddr)) {
			remoteAddr=serverIp;
		}
		wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(),partner_trade_no, organizationId);
		return amount;
	}

	public void update(MarketingMembers members) {
		marketingMembersMapper.update(members);
	}

	public MarketingMembers selectById(Long id) {
		return marketingMembersMapper.getMemberById(id);
	}

	public void insert(MarketingMembers members) {
		marketingMembersMapper.insert(members);
	}

	public MarketingMembers selectByPhoneAndOrgId(String mobile, String organizationId) {
		return marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
	}

	public void deleteById(Long id) {
		marketingMembersMapper.deleteById(id);		
	}

	public MarketingMembers selectByPhoneAndOrgIdExcludeId(String mobile, String organizationId, Long id) {
		return marketingMembersMapper.selectByPhoneAndOrgIdExcludeId(mobile,organizationId,id);
	}

	/**
	 * 招募会员注册数目
	 * @param organizationId
	 * @param date
	 * @param date1
	 */
    public List<MarketingMembers> getRegisterNum(String organizationId, Date startDate, Date endDate) throws SuperCodeException {
   		if(StringUtils.isBlank(organizationId)){
   			throw new SuperCodeException("组织不存在...");
		}
		if(startDate == null || endDate == null){
			throw new SuperCodeException("日期未选择...");
		}
   		return marketingMembersMapper.getRegisterNum(organizationId,startDate,endDate);
    }

	public List<MarketingMembers> getOrganizationAllMemberWithDate(String organizationId, Date startDate, Date endDate) throws SuperCodeException {
		if(StringUtils.isBlank(organizationId)){
			throw new SuperCodeException("组织不存在...");
		}
		if(startDate == null || endDate == null){
			throw new SuperCodeException("日期未选择...");
		}
		return marketingMembersMapper.getOrganizationAllMemberWithDate(organizationId,startDate,endDate);
	}
}



