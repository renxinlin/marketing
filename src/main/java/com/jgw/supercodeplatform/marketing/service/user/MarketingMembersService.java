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
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.dao.admincode.AdminstrativeCodeMapper;
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
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
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
	private AdminstrativeCodeMapper adminstrativeCodeMapper;

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
	private CommonService commonService;

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
		String where=" where OrganizationId='"+organizationId+"'";
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
		H5LoginVO h5LoginVO =null;
		if (StringUtils.isNotBlank(wxstate)) {
			 h5LoginVO = loginWithWxstate(mobile, wxstate);
			restResult.setResults(h5LoginVO);
		}else {
			if (StringUtils.isBlank(organizationId)) {
				restResult.setState(500);
				restResult.setMsg("积分领取登录时组织id必传");
				return restResult;
			}
			
			h5LoginVO=new H5LoginVO();
			h5LoginVO.setMobile(mobile);			//积分登录openid不一定存在
			h5LoginVO.setRegistered(1);
			MarketingMembers marketingMembersByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);
			MarketingMembers marketingMembersByOpenId=null;
			
			//判断登录的手机号是否与openid是同一条记录
			if (StringUtils.isNotBlank(openid)) {
				if (null!=marketingMembersByPhone && StringUtils.isNotBlank(marketingMembersByPhone.getOpenid())) {
					if (!openid.equals(marketingMembersByPhone.getOpenid())) {
						restResult.setState(500);
						restResult.setMsg("当前手机号已绑定其它微信号");
						return restResult;
					}
				}
			}
			
			if (StringUtils.isNotBlank(openid)) {
			   marketingMembersByOpenId=marketingMembersMapper.selectByOpenIdAndOrgId(openid, organizationId);
			}
			if (null==marketingMembersByPhone) {
				if (null==marketingMembersByOpenId) {
					MarketingMembers member=new MarketingMembers();
					member.setOpenid(openid);
					member.setMobile(mobile);
					member.setState((byte)1);
					member.setHaveIntegral(0);
					member.setMemberType((byte)0);
					member.setOrganizationId(organizationId);
					marketingMembersMapper.insert(member);
					h5LoginVO.setMemberId(member.getId());
					h5LoginVO.setHaveIntegral(0);
				}else {
					marketingMembersByOpenId.setMobile(mobile);
					marketingMembersByOpenId.setState((byte)1);
					marketingMembersMapper.update(marketingMembersByOpenId);
					h5LoginVO.setWechatHeadImgUrl(marketingMembersByOpenId.getWechatHeadImgUrl());
					h5LoginVO.setMemberId(marketingMembersByOpenId.getId());
					h5LoginVO.setHaveIntegral(marketingMembersByOpenId.getHaveIntegral()==null?0:marketingMembersByOpenId.getHaveIntegral());
				}
			}else {
				Long userIdByPhone=marketingMembersByPhone.getId();
				h5LoginVO.setHaveIntegral(marketingMembersByPhone.getHaveIntegral()==null?0:marketingMembersByPhone.getHaveIntegral());
				h5LoginVO.setMemberId(userIdByPhone);
				h5LoginVO.setWechatHeadImgUrl(marketingMembersByPhone.getWechatHeadImgUrl());
				
				if (null!=marketingMembersByOpenId) {
					Long userIdByOpenId=marketingMembersByOpenId.getId();
					if (!userIdByOpenId.equals(userIdByPhone)) {
						String openIdByPhone=marketingMembersByPhone.getOpenid();
						//手机号这条记录的openid不为空，合并过openid就通过之前的openid更新中奖纪录里的openid和手机号
						if (StringUtils.isNotBlank(openIdByPhone)) {
							//如果之前该手机号绑定过openid则更新之前的中奖纪录，没有的话就不更新哦
							mWinRecordMapper.updateOpenIdAndMobileByOpenIdAndOrgId(openid,mobile,organizationId,openIdByPhone);
						}
						//合并两条记录
						//更新手机号对应的记录设置微信openid及昵称
						MarketingMembers members=new MarketingMembers();
						members.setId(userIdByPhone);
						members.setOpenid(openid);
						members.setWxName(marketingMembersByOpenId.getWxName());
						members.setWechatHeadImgUrl(marketingMembersByOpenId.getWechatHeadImgUrl());
						members.setState((byte)1);
						marketingMembersMapper.update(members);
						
						//删除openid查出的用户
						marketingMembersMapper.deleteById(userIdByOpenId);
					}
				}
			}
			String orgnazationName="";
			try {
				orgnazationName=commonService.getOrgNameByOrgId(organizationId);
				h5LoginVO.setOrganizationName(orgnazationName);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	
    /**
     * 扫描产品防伪码登录
     * @param mobile
     * @param wxstate
     * @return
     * @throws SuperCodeException
     */
	private H5LoginVO loginWithWxstate(String mobile, String wxstate) throws SuperCodeException {
		ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(wxstate);
		if (null==scanCodeInfoMO) {
			throw new SuperCodeException("参数wxstate对应的后台扫码缓存信息不存在，请重新扫码", 500);
		}
		// 登录时候保存手机信息用于后续接口获取
		scanCodeInfoMO.setMobile(mobile);
		globalRamCache.putScanCodeInfoMO(wxstate,scanCodeInfoMO);
		
		Long activitySetId=scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet maActivitySet=mSetMapper.selectById(activitySetId);
		if (null==maActivitySet) {
			throw new SuperCodeException("该活动设置id不存在", 500);
		}
		String openId=scanCodeInfoMO.getOpenId();
		String organizationId=maActivitySet.getOrganizationId();
		//1、首先保证授权时用户是保存成功的
		MarketingMembers marketingMembersByOpenId=marketingMembersMapper.selectByOpenIdAndOrgId(openId, organizationId);
		if (null==marketingMembersByOpenId) {
			logger.info("登录时无法根据openId及组织id查找到用户,openId="+openId+",组织id="+organizationId);
			throw new SuperCodeException("无法根据openId及组织id查找到用户。可能用户已被删除，请尝试重新扫码进入授权或联系商家", 500);
		}
		List<MarketingOrganizationPortraitListParam> mPortraits=organizationPortraitMapper.getSelectedPortrait(organizationId, PortraitTypeEnum.PORTRAIT.getTypeId());
		if (null==mPortraits || mPortraits.isEmpty()) {
			throw new SuperCodeException("登录时获取企业画像设置为空，无法进行后续逻辑", 500);
		}


		H5LoginVO h5LoginVO=new H5LoginVO();
		Long userIdByOpenId=marketingMembersByOpenId.getId();
		//2、根据输入的手机号和组织id查询该手机号是否存在记录
		MarketingMembers marketingMembersByPhone=marketingMembersMapper.selectByMobileAndOrgId(mobile, organizationId);

		//3、如果根据登录手机号无法查询到记录，则说明该手机号未进行过注册也为进行过绑定。可能情况：
		//3.1该openid对应的用户之前绑定过手机号但是想换手机号了、3.2该openid用户从未绑定过手机号
		if (null==marketingMembersByPhone) {
			MarketingMembers members=new MarketingMembers();
			members.setId(userIdByOpenId);
			members.setMobile(mobile);
			marketingMembersMapper.update(members);
			if (mPortraits.size()==1) {
				//如果企业画像只有一个那默认为手机号就不需要再去完善信息
				h5LoginVO.setRegistered(1);
			}else {
				h5LoginVO.setRegistered(0);
				h5LoginVO.setMemberId(userIdByOpenId);
			}
		}else {
			// zhuchuang老逻辑：如果根据登录手机号能找到用户则说明之前登陆过或者注册过就不需要注册完善信息，但需要比较跟openid查出的记录是否是一条记录，不是的话要合并
			// renxinlin 新逻辑：已经注册用户如果画像只有手机则无需完善，如果完善过一次则无需完善
			// 默认需要完善

			h5LoginVO.setRegistered(0);
			// 只有一个画像无需完善
			if (mPortraits.size()==1){
				h5LoginVO.setRegistered(1);
			}
			// 已经完善过不完善
			if(marketingMembersByPhone.getIsRegistered() != null && marketingMembersByPhone.getIsRegistered() == 1){
				h5LoginVO.setRegistered(1);
			}

			Long userIdByPhone=marketingMembersByPhone.getId();
			//4、如果分别根据openid和手机号查出两条记录且主键id不一致，则说明
			// 4.1、这两条信息没合并过
			// 4.2、手机号这条记录合并过openid，可用户此时想换一个微信号openid
			if (!userIdByOpenId.equals(userIdByPhone)) {
				String openIdByPhone=marketingMembersByPhone.getOpenid();
				String openIdByOpendId=openId;
				//手机号这条记录的openid不为空，合并过openid就通过之前的openid更新中奖纪录里的openid和手机号
				if (StringUtils.isNotBlank(openIdByPhone)) {
					//如果之前该手机号绑定过openid则更新之前的中奖纪录，没有的话就不更新哦
					mWinRecordMapper.updateOpenIdAndMobileByOpenIdAndOrgId(openIdByOpendId,mobile,organizationId,openIdByPhone);
				}
				//合并两条记录
				//更新手机号对应的记录设置微信openid及昵称
				MarketingMembers members=new MarketingMembers();
				members.setId(userIdByPhone);
				members.setOpenid(openIdByOpendId);
				members.setWxName(marketingMembersByOpenId.getWxName());
				members.setWechatHeadImgUrl(marketingMembersByOpenId.getWechatHeadImgUrl());
				members.setState((byte)1);
				marketingMembersMapper.update(members);
				//删除openid查出的用户
				marketingMembersMapper.deleteById(userIdByOpenId);
				
				h5LoginVO.setMemberId(userIdByPhone);

			}else{
				// 逻辑说明: 说明已经合并过但是没有完善：此时手机号的uid同openId的用户ID
				// 业务声明: 所有setRegistered(1)表示需要完善的都要回传用户id,用于完善接口标志身份
				h5LoginVO.setMemberId(userIdByPhone);
			}
		}
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
		if( marketingMembersInfo.getState() == 0){
			throw  new SuperCodeException("对不起,该会员已被加入黑名单",500);
		}

		//获取该活动设置下的参与码总数
		Long codeTotalNum=mActivitySet.getCodeTotalNum();
		if (null==codeTotalNum|| codeTotalNum.intValue()<=0) {
			restResult.setState(500);
			restResult.setMsg("该活动参与的码数量小于等于0");
			return restResult;
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
		long startTime = System.currentTimeMillis();

 		try {
			// 超时时间,重试次数，重试间隔
			acquireLock = lock.lock(activitySetId + ":" + codeId + ":" + codeTypeId,5000,5,200);
			if(acquireLock){
				List<MarketingPrizeType> mPrizeTypes=mMarketingPrizeTypeMapper.selectByActivitySetIdIncludeUnreal(activitySetId);
				if (null==mPrizeTypes || mPrizeTypes.isEmpty()) {
					restResult.setState(500);
					restResult.setMsg("该活动未设置中奖奖次");
					return restResult;
				}
				List<MarketingPrizeTypeMO> mTypeMOs=LotteryUtil.judge(mPrizeTypes, codeTotalNum);


				if (null!=mTypeMOs && !mTypeMOs.isEmpty()) {
					//执行中奖算法
					mPrizeTypeMO = LotteryUtil.lottery(mTypeMOs);
					logger.info("抽到中奖奖次为："+mPrizeTypeMO);
				}else {
					//到这里说明流程已经出现问题，因为在刚开始扫码哪部分就会判断当前扫码量有没有达到活动对应的码数量
					restResult.setState(500);
					restResult.setMsg("所有奖次对应的中奖码数量都已达到上限无法继续抽奖");
					return restResult;
				}


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

				codeEsService.addScanCodeRecord(opneIdNoSpecialChactar, scanCodeInfoMO.getProductId(), scanCodeInfoMO.getProductBatchId(), codeId, codeTypeId, activitySetId,nowTtimeStemp);
				logger.info("领取方法====：抽奖数据已保存到es");
			}else {
				logger.error("{锁获取失败:" +activitySetId + codeId +codeTypeId+ ",请检查}");
				// 统计失败
				redisUtil.hmSet("marketing:lock:fail",activitySetId + codeId +codeTypeId,new Date());
				restResult.setState(500);
				restResult.setMsg("扫码人数过多,请稍后再试");
				return restResult;
			}
		}  finally {
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
		long endTime = System.currentTimeMillis();
		logger.error("==> 悲观锁一共耗时:"+(endTime-startTime)+"=======================");
		logger.info("抽奖数据已保存到es");
		//判断realprize是否为0,0表示不中奖
		Byte realPrize=mPrizeTypeMO.getRealPrize();
		if (realPrize.equals((byte)0)) {
			restResult.setState(200);
			restResult.setMsg("‘啊呀没中，一定是打开方式不对’：没中奖");
			globalRamCache.deleteScanCodeInfoMO(wxstate);
		}else if (realPrize.equals((byte)1)) {
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
			//插入中奖纪录
			MarketingMembersWinRecord redWinRecord=new MarketingMembersWinRecord();
			redWinRecord.setActivityId(activity.getId());
			redWinRecord.setActivityName(activity.getActivityName());
			redWinRecord.setActivitySetId(activitySetId);
			redWinRecord.setMobile(mobile);
			redWinRecord.setOpenid(openId);
			redWinRecord.setPrizeTypeId(mPrizeTypeMO.getId());
			redWinRecord.setWinningAmount((float)amount );
			redWinRecord.setWinningCode(scanCodeInfoMO.getCodeId());
			redWinRecord.setOrganizationId(organizationId);
			mWinRecordMapper.addWinRecord(redWinRecord);
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
			try {
				wxpService.qiyePay(openId, remoteAddr, finalAmount.intValue(),partner_trade_no, organizationId);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//一切ok后清除缓存
				globalRamCache.deleteScanCodeInfoMO(wxstate);
			}
			restResult.setState(200);
			restResult.setMsg(amount+"");
		}
		return restResult;
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

}



