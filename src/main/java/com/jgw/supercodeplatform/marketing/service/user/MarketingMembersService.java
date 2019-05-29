package com.jgw.supercodeplatform.marketing.service.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.constants.PcccodeConstants;
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
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRecordMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.user.MarketingMembersMapper;
import com.jgw.supercodeplatform.marketing.dao.user.OrganizationPortraitMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersAddParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersListParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingMembersUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.enums.portrait.PortraitTypeEnum;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

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
	private IntegralRuleMapperExt integralRuleDao;
	
	@Autowired
	private RedisUtil redisUtil;

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
		String organizationId=getOrganizationId();
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
		//

		MarketingMembers members=modelMapper.map(marketingMembersAddParam,MarketingMembers.class);
		List<JSONObject> objects = JSONObject.parseArray(marketingMembersAddParam.getpCCcode(),JSONObject.class);
		JSONObject province = objects.get(0);
		JSONObject city = objects.get(1);
		JSONObject country = objects.get(2);
		members.setProvinceCode(province.getString(PcccodeConstants.areaCode));
		members.setCityCode(city.getString(PcccodeConstants.areaCode));
		members.setCountyCode(country.getString(PcccodeConstants.areaCode));
		members.setProvinceName(province.getString(PcccodeConstants.areaName));
		members.setCityName(city.getString(PcccodeConstants.areaName));
		members.setCountyName(country.getString(PcccodeConstants.areaName));
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
	 * 发送短信 	todo,转移到公共类
	 * @param mobile 手机号
	 * @param msg 短信内容
	 */
	public void sendRegisterMessage(String mobile, String msg)  {

		try {
			Map msgData = new HashMap();
			msgData.put("mobileId",mobile);
			msgData.put("sendContent",msg);
			// 发送短信
			restTemplate.postForEntity(userServiceUrl + WechatConstants.SMS_SEND_PHONE_MESSGAE, msgData, RestResult.class);

		} catch (Exception e) {
			// 公共方法提示交给调用方
			if(logger.isInfoEnabled()){
				logger.info("短信发送失败"+e.getMessage());
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
	public Integer getAllMarketingMembersCountWithOutToday(Map<String,Object> map){
		return marketingMembersMapper.getAllMarketingMembersCountWithOutToday(map);
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

	public MarketingMembers selectByOpenIdAndOrgIdWithTemp(String openid, String organizationId) {
		return marketingMembersMapper.selectByOpenIdAndOrgIdWithTemp(openid,organizationId);
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
			List<MarketingOrganizationPortraitListParam> mPortraits = organizationPortraitMapper
					.getSelectedPortrait(organizationId, PortraitTypeEnum.PORTRAIT.getTypeId());
			if (null == mPortraits || mPortraits.isEmpty()) {
				throw new SuperCodeException("登录时获取企业画像设置为空，无法进行后续逻辑", 500);
			}
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
			}
			if (memberByPhone.getState().intValue()==0) {
				throw new SuperCodeException("您已被禁用，请联系管理员", 500);
			}
			trueMember=memberByPhone;
		}else {
			MarketingMembers memberByOpenId=marketingMembersMapper.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
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
				//
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
						if (StringUtils.isNotBlank(memberByOpenId.getWechatHeadImgUrl())) {
							memberByPhone.setWechatHeadImgUrl(memberByOpenId.getWechatHeadImgUrl());
						}
						marketingMembersMapper.update(memberByPhone);
						
						marketingMembersMapper.deleteById(memberByOpenId.getId());
						
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
	private H5LoginVO loginWithWxstate(String mobile, String wxstate) throws SuperCodeException {
		ScanCodeInfoMO scanCodeInfoMO = globalRamCache.getScanCodeInfoMO(wxstate);
		if (null == scanCodeInfoMO) {
			throw new SuperCodeException("参数wxstate对应的后台扫码缓存信息不存在，请重新扫码", 500);
		}
		// 登录时候保存手机信息用于后续接口获取
		scanCodeInfoMO.setMobile(mobile);
		Long activitySetId = scanCodeInfoMO.getActivitySetId();
		MarketingActivitySet maActivitySet = mSetMapper.selectById(activitySetId);
		if (null == maActivitySet) {
			throw new SuperCodeException("该活动设置id不存在", 500);
		}
		String organizationId = maActivitySet.getOrganizationId();
		List<MarketingOrganizationPortraitListParam> mPortraits = organizationPortraitMapper
				.getSelectedPortrait(organizationId, PortraitTypeEnum.PORTRAIT.getTypeId());
		if (null == mPortraits || mPortraits.isEmpty()) {
			throw new SuperCodeException("登录时获取企业画像设置为空，无法进行后续逻辑", 500);
		}
		H5LoginVO h5LoginVO = commonLogin(mobile, scanCodeInfoMO.getOpenId(), organizationId,mPortraits.size());
		scanCodeInfoMO.setUserId(h5LoginVO.getMemberId());
		globalRamCache.putScanCodeInfoMO(wxstate, scanCodeInfoMO);
		return h5LoginVO;
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

	public RestResult<String> guideLottery(String wxstate) {
		return null;
	}
}



