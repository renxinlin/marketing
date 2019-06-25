package com.jgw.supercodeplatform.marketing.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.enums.market.AccessProtocol;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.enums.market.SaleUserStatus;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
/**
 * 微信授权等
 * @author czm
 *
 */
@Controller
@RequestMapping("/marketing/front/auth")
@Api(tags = "微信授权回调地址")
public class WeixinAuthController {


	@Autowired
	private ModelMapper modelMapper;
	protected static Logger logger = LoggerFactory.getLogger(WeixinAuthController.class);

	@Autowired
	private MarketingMembersService marketingMembersService;


	@Autowired
	private MarketingSaleMemberService marketingSaleMemberService;


	@Autowired
	private CommonService commonService;

	@Autowired
	private GlobalRamCache globalRamCache;

    @Value("${marketing.activity.h5page.url}")
    private String h5pageUrl;

    @Value("${marketing.integral.h5page.urls}")
    private String integralH5Pages;

	@Value("${cookie.domain}")
	private String cookieDomain;
    /**
     * 微信授权回调方法
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code",method=RequestMethod.GET)
    public String getWXCode(String code ,String state,HttpServletResponse response) throws Exception {
    	logger.info("微信授权回调获取code="+code+",state="+state);
    	if (StringUtils.isBlank(state)) {
    		throw new SuperCodeException("state不能为空", 500);
		}
    	String redirectUrl=null;
    	String nickName=null;
    	String openid=null;
    	String organizationId=null;
    	JSONObject userInfo=null;
    	String statevalue="";
    	Integer statecode=null;
    	String[] statearr=null;
    	if (state.contains("_")) {
    		statevalue=state;
    		statearr=state.split("_");
    		statecode=Integer.valueOf(statearr[0]);
		}else {
			statevalue=state;
		}

    	ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(statevalue);
    	logger.info("根据code="+code+" 查询到的scanCodeInfoMO="+scanCodeInfoMO+",statecode="+statecode+",statevalue="+statevalue);
    	boolean needWriteJwtToken=false;



    	MarketingMembers members=null;
    	//表示不是从扫码产品防伪码入口进入
    	if (null==scanCodeInfoMO) {
    		// 2表示导购
    		if(AccessProtocol.ACTIVITY_SALER.getType() == statecode){
    			redirectUrl =doBizBySaler(statearr,state,code,userInfo,redirectUrl,response);
    			return redirectUrl;
    		}
    		organizationId=statearr[1];
    		userInfo=getUserInfo(code, organizationId);
    		openid=userInfo.getString("openid");
    		StringBuffer h5BUf=new StringBuffer();
    		h5BUf.append("redirect:");
    		h5BUf.append(integralH5Pages.split(",")[statecode]);
    		h5BUf.append("?openid="+openid);
    		if (null!=statecode && 0==statecode.intValue()) {
    			h5BUf.append("&uuid="+statearr[2]);
			}
    		h5BUf.append("&organizationId="+organizationId);

    	 	members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
    		Long memberParamId = loginMemberId(members);
            if (memberParamId.intValue()!=-1) {
            	needWriteJwtToken=true;
			}
    		h5BUf.append("&memberId="+memberParamId);
			nickName=userInfo.getString("nickname");
    		redirectUrl=h5BUf.toString();
		}else {
			//如果是活动扫码默认也写jwttoken
			needWriteJwtToken=true;
			userInfo=getUserInfo(code, scanCodeInfoMO.getOrganizationId());
			openid=userInfo.getString("openid");
			organizationId=scanCodeInfoMO.getOrganizationId();
			//表示是从扫码产品防伪码入口进入
			nickName=userInfo.getString("nickname");
			scanCodeInfoMO.setOpenId(userInfo.getString("openid"));
			//更新扫码信息
			globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);
    	 	members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
    		Long memberParamId = loginMemberId(members);
            if (memberParamId.intValue()!=-1) {
            	needWriteJwtToken=true;
			}
			redirectUrl="redirect:"+h5pageUrl+"?wxstate="+state+"&activitySetId="+scanCodeInfoMO.getActivitySetId()+"&organizationId="+organizationId+"&memberId="+memberParamId;
		}
		//判断是否需要保存用户
		if (null==members) {
			members=new MarketingMembers();
			members.setOpenid(openid);
			members.setWxName(nickName);
			members.setState((byte)2);
			members.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			members.setOrganizationId(organizationId);
			members.setIsRegistered((byte)0);
			marketingMembersService.insert(members);
		}else {
			if (null!=scanCodeInfoMO) {
				scanCodeInfoMO.setUserId(members.getId());
				globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);
			}
			members.setWxName(nickName);
			marketingMembersService.update(members);
		}

		//如果需要写jwttoken，在积分授权时只有不需要手机号登录时才写token否则手机号登录那里会写
		if (needWriteJwtToken) {
			writeJwtToken(response, members);
		}
//        String redirectUrl="redirect:http://192.168.10.78:7081/?wxstate="+state+"&activitySetId="+scInfoMO.getActivitySetId()+"&organizationId="+scInfoMO.getOrganizationId();
    	logger.info("最终跳转路径："+redirectUrl);
    	return  redirectUrl;
    }

	private Long loginMemberId(MarketingMembers members) {
		if (null!=members ) {
			Byte memberState=members.getState();
			// 1表示正常
			if (null!=memberState && memberState.intValue()==1) {
				return members.getId();
			}else {
				return -1L;
    		}
		}else {
			return -1L;
		}
	}

	private void writeJwtToken(HttpServletResponse response, MarketingMembers members) {
		String orgnazationName="";
		H5LoginVO h5LoginVO=new H5LoginVO();
		h5LoginVO.setMemberType(members.getMemberType());
		h5LoginVO.setCustomerId(members.getCustomerId());
		h5LoginVO.setCustomerName(members.getCustomerName());
		h5LoginVO.setHaveIntegral(members.getHaveIntegral());
		h5LoginVO.setMemberId(members.getId());
		h5LoginVO.setMobile(members.getMobile());
		h5LoginVO.setWechatHeadImgUrl(members.getWechatHeadImgUrl());
		h5LoginVO.setMemberName(members.getUserName()==null?members.getWxName():members.getUserName());
		h5LoginVO.setOrganizationId(members.getOrganizationId());
		h5LoginVO.setCustomerId(members.getCustomerId());
		h5LoginVO.setCustomerName(members.getCustomerName());
		try {
			orgnazationName=commonService.getOrgNameByOrgId(members.getOrganizationId());

			h5LoginVO.setOrganizationName(orgnazationName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String jwtToken=JWTUtil.createTokenWithClaim(h5LoginVO);
			Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
			// jwt有效期为2小时，保持一致
			jwtTokenCookie.setMaxAge(60*60*2);
			// 待补充： 其他参数基于传递状况
			jwtTokenCookie.setPath("/");
			jwtTokenCookie.setDomain(cookieDomain);
			response.addCookie(jwtTokenCookie);
			response.addHeader("Access-Control-Allow-Origin", "");
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type, Set-Cookie, *");
			logger.info("微信授权写jwt-token成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public JSONObject getUserInfo(String code,String organizationId) throws Exception {
		MarketingWxMerchants mWxMerchants=globalRamCache.getWXMerchants(organizationId);
		String appId=mWxMerchants.getMchAppid().trim();
		String secret=mWxMerchants.getMerchantSecret().trim();
		logger.info("微信授权回调根据组织id="+organizationId+"获取获取appid"+appId+",secret="+secret);
		String tokenParams="?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		HttpClientResult tokenhttpResult=HttpRequestUtil.doGet(WechatConstants.AUTH_ACCESS_TOKEN_URL+tokenParams);
		String tokenContent=tokenhttpResult.getContent();
		logger.info("调用获取授权access_token后返回内容："+tokenContent);
		if (tokenContent.contains("errcode")) {
			throw new SuperCodeException(tokenContent, 500);
		}

		JSONObject accessTokenObj=JSONObject.parseObject(tokenContent);
		String openid=accessTokenObj.getString("openid");


		logger.info("--------------------授权成功--------------------------------");
		HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(WechatConstants.ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+secret);
	    String body=reHttpClientResult.getContent();
	    logger.info("请求获取用户信息token返回;"+body);
	    if (body.contains("access_token")) {
			JSONObject tokenObj=JSONObject.parseObject(body);
			String token=tokenObj.getString("access_token");
			HttpClientResult userInfoResult=HttpRequestUtil.doGet(WechatConstants.WECHAT_USER_INFO+"?access_token="+token+"&openid="+openid+"&lang=zh_CN");
			String userInfoBody=userInfoResult.getContent();
			logger.info("判断是否关注过公众号方法获取用户基本信息`返回结果="+userInfoBody);
			if (userInfoBody.contains("subscribe")) {
				JSONObject userObj=JSONObject.parseObject(userInfoBody);
                return userObj;
			}
		}
//		String access_token=accessTokenObj.getString("access_token");
//
//		String userInfoParams="?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
//		HttpClientResult userinfohttpResult=HttpRequestUtil.doGet(WechatConstants.USER_INFO_URL+userInfoParams);
//		String userinfoContent=userinfohttpResult.getContent();
//		logger.info("调用获取基础用户信息接口后返回内容："+userinfoContent);
//		if (userinfoContent.contains("errcode")) {
//			throw new SuperCodeException(tokenContent, 500);
//		}
//		JSONObject userinfoObj=JSONObject.parseObject(userinfoContent);
		return null;
    }



    /**
     * 微信授权回调方法
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code2",method=RequestMethod.GET)
    public String getWXCode2(String code ,String state,HttpServletResponse response) throws Exception {
    	logger.info("微信授权回调获取code="+code+",state="+state);
    	if (StringUtils.isBlank(state)) {
    		throw new SuperCodeException("state不能为空", 500);
		}


    	String redirectUrl=null;
    	String nickName=null;
    	String openid=null;
    	String organizationId=null;
    	JSONObject userInfo=null;
    	String statevalue="";
    	Integer statecode=null;
    	Map<String, String> stateMap=null;
    	if (state.contains("&")) {
    		stateMap=stateToMap(state);
    		statevalue=state;
    		statecode=Integer.valueOf(stateMap.get("code"));
		}else {
			statevalue=state;
		}
    	if(stateMap.get("type") =="导购"){
    		// 导购
				//
				userInfo=getUserInfo(code, organizationId);
				openid=userInfo.getString("openid");
				MarketingUser marketingUser = marketingSaleMemberService.selectByOpenid(openid);
				if(marketingUser ==null || !organizationId.equals(marketingUser.getOrganizationId())){// 手机号只能注册一个组织
					if( !organizationId.equals(marketingUser.getOrganizationId())){
						throw new SuperCodeException("不要前往其他店贪小便宜...");
					}
//				h5BUf.append("&memberId=-1");
					// 失败
					// 跳转登录页
					// 携带openid跳转登录页
					// 手机号存在才会绑定openid 也就是说手机号不存在肯定无openid,手机号存在可能有OPENID
				}else{
					// 成功
					// 写登录数据 跳转销售中心
//					needWriteJwtToken=true;
					H5LoginVO jwtUser = new H5LoginVO();
					jwtUser.setMobile(marketingUser.getMobile());
					jwtUser.setMemberId(marketingUser.getId());
					jwtUser.setOrganizationId(marketingUser.getOrganizationId());
					jwtUser.setMemberType(MemberTypeEnums.SALER.getType());
					jwtUser.setCustomerId(marketingUser.getCustomerId());
					jwtUser.setCustomerName(marketingUser.getCustomerName());
					// TODO 可能存在其他登录信息需要设置

					String jwtToken = JWTUtil.createTokenWithClaim(jwtUser);
					Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
					// jwt有效期为2小时，保持一致
					jwtTokenCookie.setMaxAge(60*60*2);
					jwtTokenCookie.setPath("/");
					jwtTokenCookie.setDomain(cookieDomain);
					response.addCookie(jwtTokenCookie);

					redirectUrl="待写";


				}
				redirectUrl="redirect:"+h5pageUrl;
				return  redirectUrl;

		}else{
			ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(statevalue);
			logger.info("根据code="+code+" 查询到的scanCodeInfoMO="+scanCodeInfoMO+",statecode="+statecode+",statevalue="+statevalue);
			boolean needWriteJwtToken=false;
			//表示不是从扫码产品防伪码入口进入
			if (null==scanCodeInfoMO) {
				organizationId=stateMap.get("organizationId");
				userInfo=getUserInfo(code, organizationId);
				openid=userInfo.getString("openid");
				StringBuffer h5BUf=new StringBuffer();
				h5BUf.append("redirect:");
				h5BUf.append(integralH5Pages.split(",")[statecode]);
				h5BUf.append("?openid="+openid).append("&").append(state);
				MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
				if (null!=members ) {
					Byte memberState=members.getState();
					if (null!=memberState && memberState.intValue()==1) {
						h5BUf.append("&memberId="+members.getId());
						needWriteJwtToken=true;
					}else {
						h5BUf.append("&memberId=-1");
					}
				}else {
					h5BUf.append("&memberId=-1");
				}
				nickName=userInfo.getString("nickname");
				redirectUrl=h5BUf.toString();
				// TODO 预定义
			}else {
				//如果是活动扫码默认也写jwttoken
				needWriteJwtToken=true;

				userInfo=getUserInfo(code, scanCodeInfoMO.getOrganizationId());
				openid=userInfo.getString("openid");
				organizationId=scanCodeInfoMO.getOrganizationId();
				//表示是从扫码产品防伪码入口进入
				nickName=userInfo.getString("nickname");
				scanCodeInfoMO.setOpenId(userInfo.getString("openid"));
				//更新扫码信息
				globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);

				// success = 0失败

				redirectUrl="redirect:"+h5pageUrl+"?wxstate="+state+"&activitySetId="+scanCodeInfoMO.getActivitySetId()+"&organizationId="+organizationId;
			}

			//判断是否需要保存用户
			MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
			if (null==members) {
				members=new MarketingMembers();
				members.setOpenid(openid);
				members.setWxName(nickName);
				members.setState((byte)1);
				// TODO STATE=2
//				members.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
//				members.setOrganizationId(organizationId);
//				if (null!=statecode) {
//					members.setState((byte)2);
//				}
//				marketingMembersService.insert(members);
				//
			}else {
				members.setWxName(nickName);
				marketingMembersService.update(members);
			}
			//如果需要写jwttoken，在积分授权时只有不需要手机号登录时才写token否则手机号登录那里会写
			if (needWriteJwtToken) {
				writeJwtToken(response, members);
			}
//        String redirectUrl="redirect:http://192.168.10.78:7081/?wxstate="+state+"&activitySetId="+scInfoMO.getActivitySetId()+"&organizationId="+scInfoMO.getOrganizationId();
			logger.info("最终跳转路径："+redirectUrl);
			return  redirectUrl;
		}

    }

	private Map<String, String> stateToMap(String state) {
		String values[]=state.split("\\&");
		Map<String, String> stateMap=new HashMap<>();
        for (String va : values) {
			String[] key_va=va.split("=");
			stateMap.put(key_va[0], key_va[1]);
		}
		return stateMap;
	}



	private String doBizBySaler(String[] statearr, String state,String code,JSONObject userInfo,String redirectUrl,HttpServletResponse response) throws Exception {
		if(statearr == null || statearr.length<=1){
			if(logger.isErrorEnabled()){
				logger.error("[前端授权导购信息异常=>state:{}]",state);
				throw new SuperCodeException("系统授权信息异常");
			}
		}

// 导购step-1: 微信授权
		String organizationId=statearr[1];
		userInfo=getUserInfo(code, organizationId);
		String openid=userInfo.getString("openid");


// 导购step-2: 刷新头像
		// 需要返回前端组织Id和用户id[id或者-1]以及openid

		MarketingUser marketingUser = marketingSaleMemberService.selectByOpenidAndOrgId(openid,organizationId);


// 导购step-3: 业务处理
		if(marketingUser != null){
			// TODO 异步
			// 始终刷新微信用户头像,用于同步微信信息
			MarketingUser marketingUserDo = new MarketingUser();
			marketingUserDo.setId(marketingUser.getId());
			marketingUserDo.setOpenid(userInfo.getString("openid"));
			marketingUserDo.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			marketingUserDo.setWxName(userInfo.getString("nickname"));
			marketingSaleMemberService.updateWxInfo(marketingUser);
			// 说明用户存在,需要自动登录
			logger.error("user =>{} define =>{}", marketingUser.getState().intValue(),SaleUserStatus.ENABLE.getStatus().intValue());
			if(marketingUser.getState().intValue() != SaleUserStatus.ENABLE.getStatus().intValue()){
				// 非启用状态
				StringBuffer urlParams = new StringBuffer("?");
				urlParams.append("memberId=-1").append("&openid=").append(openid)
						.append("&organizationId=").append(organizationId);
				redirectUrl ="redirect:" + h5pageUrl + WechatConstants.SALER_LOGIN_URL+urlParams.toString();
			}else{
				StringBuffer urlParams = new StringBuffer("?");
				urlParams.append("memberId=").append(marketingUser.getId()).append("&openid=").append(openid)
						.append("&organizationId=").append(organizationId);
				redirectUrl ="redirect:" + h5pageUrl + WechatConstants.SALER_LOGIN_URL+urlParams.toString();
				MarketingMembers user = new MarketingMembers();
				MarketingMembers userVo = modelMapper.map(marketingUser, MarketingMembers.class);
				user.setId(user.getId());
				writeJwtToken(response,userVo);
			}
		}else{
			// 前端需要的信息
			// 推荐前端缓存该信息:组织Id等关键信息不适合url上携带
			StringBuffer sb = new StringBuffer("?");
			sb.append("memberId=-1").append("&openid=").append(openid)
					.append("&organizationId=").append(organizationId);
			redirectUrl ="redirect:" +  h5pageUrl + WechatConstants.SALER_LOGIN_URL+sb.toString();
		}
		logger.info("导购扫码最终返回url:"+redirectUrl);
		return  redirectUrl;
	}
}
