package com.jgw.supercodeplatform.marketing.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.weixin.MarketingWxMerchantsMapper;
import com.jgw.supercodeplatform.marketing.enums.market.AccessProtocol;
import com.jgw.supercodeplatform.marketing.enums.market.SaleUserStatus;
import com.jgw.supercodeplatform.marketing.pojo.*;
import com.jgw.supercodeplatform.marketing.pojo.platform.MarketingPlatformOrganization;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingPlatformOrganizationService;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 微信授权等
 * @author czm
 *
 */
@Controller
@RequestMapping("/marketing/front/auth")
@Api(tags = "微信授权回调地址")
@Slf4j
public class WeixinAuthController {

	@Autowired
	private ModelMapper modelMapper;
 
	@Autowired
	private MarketingMembersService marketingMembersService;

	@Autowired
	private MarketingPlatformOrganizationService marketingPlatformOrganizationService;

	@Autowired
	private MarketingSaleMemberService marketingSaleMemberService;

	@Autowired
	private MarketingWxMerchantsMapper mWxMerchantsMapper;

	@Autowired
	private CommonService commonService;

	@Autowired
	private GlobalRamCache globalRamCache;

	@Autowired
	private CommonUtil commonUtil;

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
    public String getWXCode(String code ,String state, String redirctUrl, HttpServletResponse response) throws Exception {
    	log.info("微信授权回调获取code="+code+",state="+state);
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
    	log.info("根据code="+code+" 查询到的scanCodeInfoMO="+scanCodeInfoMO+",statecode="+statecode+",statevalue="+statevalue);
    	boolean needWriteJwtToken=false;
		Byte jgwType = null;
		String appId = null;
		String organizationName = null;
    	MemberWithWechat memberWithWechat=null;
		MarketingWxMember marketingWxMember = null;
    	//表示不是从扫码产品防伪码入口进入
    	if (null==scanCodeInfoMO) {
    		// 2表示导购
    		if(AccessProtocol.ACTIVITY_SALER.getType() == statecode){
    			redirectUrl =doBizBySaler(statearr[1],state,code,userInfo,redirectUrl,response);
    			return redirectUrl;
    		}
    		//5表示全网运营红包
    		if (AccessProtocol.ACTIVITY_PLATFORM.getType() == statecode) {
				redirectUrl = doBizPlatform(redirctUrl, statearr[1], code, response);
				return redirectUrl;
			}
    		organizationId=statearr[1];
    		userInfo=getUserInfo(code, organizationId,null);
			appId = userInfo.getString("appId");
			jgwType = userInfo.getByte("jgwType");
			organizationName = userInfo.getString("organizationName");
    		openid=userInfo.getString("openid");
    		StringBuffer h5BUf=new StringBuffer();
    		h5BUf.append("redirect:");
    		if(statecode != null && statecode.intValue() == AccessProtocol.ACTIVITY_COUPON.getType()) {
                h5BUf.append(integralH5Pages.split(",")[0]);
            } else {
                h5BUf.append(integralH5Pages.split(",")[statecode]);
            }
    		h5BUf.append("?openid="+openid);
    		if (null!=statecode && 0==statecode.intValue()) {
    			h5BUf.append("&uuid="+statearr[2]);
			}
    		h5BUf.append("&organizationId="+organizationId);
    		if(statecode != null && statecode.intValue() == AccessProtocol.ACTIVITY_COUPON.getType()) {
                h5BUf.append("&uuid=").append(statearr[2]).append("&type=").append(statecode);
            }
			memberWithWechat = marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
			marketingWxMember = marketingMembersService.getWxMemberByOpenidAndOrgid(openid, organizationId);
    		Long memberParamId = loginMemberId(memberWithWechat);
            if (memberParamId.intValue()!=-1) {
            	needWriteJwtToken=true;
			}
    		h5BUf.append("&memberId="+memberParamId);
			nickName=userInfo.getString("nickname");
    		redirectUrl=h5BUf.toString();
		}else {
			//如果是活动扫码默认也写jwttoken
			userInfo=getUserInfo(code, scanCodeInfoMO.getOrganizationId(),scanCodeInfoMO.getActivitySetId());
			openid=userInfo.getString("openid");
			jgwType = userInfo.getByte("jgwType");
			organizationName = userInfo.getString("organizationName");
			organizationId=scanCodeInfoMO.getOrganizationId();
			appId = userInfo.getString("appId");
			//表示是从扫码产品防伪码入口进入
			nickName=userInfo.getString("nickname");
			scanCodeInfoMO.setOpenId(userInfo.getString("openid"));
			//更新扫码信息
			globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);
			memberWithWechat = marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
			marketingWxMember = marketingMembersService.getWxMemberByOpenidAndOrgid(openid, organizationId);
			Long memberParamId = loginMemberId(memberWithWechat);
			if (memberParamId.intValue()!=-1) {
				needWriteJwtToken=true;
			}
			redirectUrl="redirect:"+h5pageUrl+"?wxstate="+state+"&activitySetId="+scanCodeInfoMO.getActivitySetId()+"&organizationId="+organizationId+"&memberId="+memberParamId;
		}
		//判断是否需要保存用户
		if (null == marketingWxMember) {
			memberWithWechat = new MemberWithWechat();
			memberWithWechat.setOpenid(openid);
			memberWithWechat.setWxName(nickName);
			memberWithWechat.setState((byte)2);
			memberWithWechat.setAppid(appId);
			memberWithWechat.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			memberWithWechat.setOrganizationId(organizationId);
			memberWithWechat.setOrganizationFullName(organizationName);
			memberWithWechat.setMemberType((byte)0);
			memberWithWechat.setJgwType(jgwType);
			marketingMembersService.insert(memberWithWechat);
		} else {
			if (null != memberWithWechat && scanCodeInfoMO != null) {
				scanCodeInfoMO.setUserId(memberWithWechat.getMemberId());
				globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);
			}
			MarketingWxMember wxMember = new MarketingWxMember();
			wxMember.setCurrentUse((byte)1);
			wxMember.setWxName(nickName);
			wxMember.setOpenid(marketingWxMember.getOpenid());
			wxMember.setOrganizationId(marketingWxMember.getOrganizationId());
			marketingMembersService.updateWxMemberByOpenid(marketingWxMember);
		}

		//如果需要写jwttoken，在积分授权时只有不需要手机号登录时才写token否则手机号登录那里会写
		if (needWriteJwtToken) {
			MarketingMembers members = new MarketingMembers();
			writeJwtToken(response, memberWithWechat);
		}
//        String redirectUrl="redirect:http://192.168.10.78:7081/?wxstate="+state+"&activitySetId="+scInfoMO.getActivitySetId()+"&organizationId="+scInfoMO.getOrganizationId();
    	log.info("最终跳转路径："+redirectUrl);
    	return  redirectUrl;
    }

	private Long loginMemberId(MemberWithWechat memberWithWechat) {
		if (null!=memberWithWechat ) {
			Byte memberState=memberWithWechat.getState();
			// 1表示正常
			if (null!=memberState && memberState.intValue()==1) {
				return memberWithWechat.getMemberId();
			}else {
				return -1L;
    		}
		}else {
			return -1L;
		}
	}

	private void writeJwtToken(HttpServletResponse response, MemberWithWechat memberWithWechat) {
		H5LoginVO h5LoginVO=new H5LoginVO();
		h5LoginVO.setMemberType(memberWithWechat.getMemberType());
		h5LoginVO.setCustomerId(memberWithWechat.getCustomerId());
		h5LoginVO.setCustomerName(memberWithWechat.getCustomerName());
		h5LoginVO.setHaveIntegral(memberWithWechat.getHaveIntegral());
		h5LoginVO.setMemberId(memberWithWechat.getMemberId());
		h5LoginVO.setMobile(memberWithWechat.getMobile());
		h5LoginVO.setWechatHeadImgUrl(memberWithWechat.getWechatHeadImgUrl());
		h5LoginVO.setMemberName(StringUtils.isEmpty(memberWithWechat.getUserName())?memberWithWechat.getWxName():memberWithWechat.getUserName());
		h5LoginVO.setOrganizationId(memberWithWechat.getOrganizationId());
		h5LoginVO.setCustomerId(memberWithWechat.getCustomerId());
		h5LoginVO.setCustomerName(memberWithWechat.getCustomerName());
		h5LoginVO.setOpenid(memberWithWechat.getOpenid());
		h5LoginVO.setOrganizationName(memberWithWechat.getOrganizationFullName());
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
			response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");
			log.info("微信授权写jwt-token成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public JSONObject getUserInfo(String code,String organizationId, Long activitySetId) throws Exception {
		String appId = null, secret = null;
//    	if (activitySetId != null) {
//			MarketingActivitySet marketingActivitySet = marketingActivitySetMapper.selectById(activitySetId);
//			String merchantsInfo = marketingActivitySet.getMerchantsInfo();
//			if (StringUtils.isNotBlank(merchantsInfo)) {
//				JSONObject merchantJson = JSON.parseObject(merchantsInfo);
//				appId = merchantJson.getString("mchAppid");
//				secret = merchantJson.getString("merchantSecret");
//			}
//		}
		Byte jgwType = null;
		String organizationName = null;
    	if (appId == null || secret == null){
			MarketingWxMerchants mWxMerchants=globalRamCache.getWXMerchants(organizationId);
			appId = mWxMerchants.getMchAppid().trim();
			secret = mWxMerchants.getMerchantSecret().trim();
			organizationName = mWxMerchants.getOrganizatioIdlName();
			jgwType = mWxMerchants.getBelongToJgw();
		}

		log.info("微信授权回调根据组织id="+organizationId+"获取获取appid"+appId+",secret="+secret);
		String tokenParams="?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		HttpClientResult tokenhttpResult=HttpRequestUtil.doGet(WechatConstants.AUTH_ACCESS_TOKEN_URL+tokenParams);
		String tokenContent=tokenhttpResult.getContent();
		log.info("调用获取授权access_token后返回内容："+tokenContent);
		if (tokenContent.contains("errcode")) {
			throw new SuperCodeException(tokenContent, 500);
		}

		JSONObject accessTokenObj=JSONObject.parseObject(tokenContent);
		String openid=accessTokenObj.getString("openid");


		log.info("--------------------授权成功--------------------------------");
		HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(WechatConstants.ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+secret);
	    String body=reHttpClientResult.getContent();
	    log.info("请求获取用户信息token返回;"+body);
	    if (body.contains("access_token")) {
			JSONObject tokenObj=JSONObject.parseObject(body);
			String token=tokenObj.getString("access_token");
			HttpClientResult userInfoResult=HttpRequestUtil.doGet(WechatConstants.WECHAT_USER_INFO+"?access_token="+token+"&openid="+openid+"&lang=zh_CN");
			String userInfoBody=userInfoResult.getContent();
			log.info("判断是否关注过公众号方法获取用户基本信息`返回结果="+userInfoBody);
			if (userInfoBody.contains("subscribe")) {
				JSONObject userObj=JSONObject.parseObject(userInfoBody);
				userObj.put("organizationName", organizationName);
				userObj.put("appId", appId);
				userObj.put("jgwType", jgwType);
                return userObj;
			}
		}
//		String access_token=accessTokenObj.getString("access_token");
//
//		String userInfoParams="?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
//		HttpClientResult userinfohttpResult=HttpRequestUtil.doGet(WechatConstants.USER_INFO_URL+userInfoParams);
//		String userinfoContent=userinfohttpResult.getContent();
//		log.info("调用获取基础用户信息接口后返回内容："+userinfoContent);
//		if (userinfoContent.contains("errcode")) {
//			throw new SuperCodeException(tokenContent, 500);
//		}
//		JSONObject userinfoObj=JSONObject.parseObject(userinfoContent);
		return null;
    }



//    /**
//     * 微信授权回调方法
//     * @param code
//     * @param state
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/code2",method=RequestMethod.GET)
//    public String getWXCode2(String code ,String state,HttpServletResponse response) throws Exception {
//    	log.info("微信授权回调获取code="+code+",state="+state);
//    	if (StringUtils.isBlank(state)) {
//    		throw new SuperCodeException("state不能为空", 500);
//		}
//
//
//    	String redirectUrl=null;
//    	String nickName=null;
//    	String openid=null;
//    	String organizationId=null;
//    	JSONObject userInfo=null;
//    	String statevalue="";
//    	Integer statecode=null;
//    	Map<String, String> stateMap=null;
//    	if (state.contains("&")) {
//    		stateMap=stateToMap(state);
//    		statevalue=state;
//    		statecode=Integer.valueOf(stateMap.get("code"));
//		}else {
//			statevalue=state;
//		}
//    	if(stateMap.get("type") =="导购"){
//    		// 导购
//				//
//				userInfo=getUserInfo(code, organizationId,null);
//				openid=userInfo.getString("openid");
//				MarketingUser marketingUser = marketingSaleMemberService.selectByOpenid(openid);
//				if(marketingUser ==null || !organizationId.equals(marketingUser.getOrganizationId())){// 手机号只能注册一个组织
//					if( !organizationId.equals(marketingUser.getOrganizationId())){
//						throw new SuperCodeException("不要前往其他店贪小便宜...");
//					}
////				h5BUf.append("&memberId=-1");
//					// 失败
//					// 跳转登录页
//					// 携带openid跳转登录页
//					// 手机号存在才会绑定openid 也就是说手机号不存在肯定无openid,手机号存在可能有OPENID
//				}else{
//					// 成功
//					// 写登录数据 跳转销售中心
////					needWriteJwtToken=true;
//					H5LoginVO jwtUser = new H5LoginVO();
//					jwtUser.setMobile(marketingUser.getMobile());
//					jwtUser.setMemberId(marketingUser.getId());
//					jwtUser.setOrganizationId(marketingUser.getOrganizationId());
//					jwtUser.setMemberType(MemberTypeEnums.SALER.getType());
//					jwtUser.setCustomerId(marketingUser.getCustomerId());
//					jwtUser.setCustomerName(marketingUser.getCustomerName());
//					// TODO 可能存在其他登录信息需要设置
//
//					String jwtToken = JWTUtil.createTokenWithClaim(jwtUser);
//					Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
//					// jwt有效期为2小时，保持一致
//					jwtTokenCookie.setMaxAge(60*60*2);
//					jwtTokenCookie.setPath("/");
//					jwtTokenCookie.setDomain(cookieDomain);
//					response.addCookie(jwtTokenCookie);
//
//					redirectUrl="待写";
//
//
//				}
//				redirectUrl="redirect:"+h5pageUrl;
//				return  redirectUrl;
//
//		}else{
//			ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(statevalue);
//			log.info("根据code="+code+" 查询到的scanCodeInfoMO="+scanCodeInfoMO+",statecode="+statecode+",statevalue="+statevalue);
//			boolean needWriteJwtToken=false;
//			//表示不是从扫码产品防伪码入口进入
//			if (null==scanCodeInfoMO) {
//				organizationId=stateMap.get("organizationId");
//				userInfo=getUserInfo(code, organizationId,null);
//				openid=userInfo.getString("openid");
//				StringBuffer h5BUf=new StringBuffer();
//				h5BUf.append("redirect:");
//				h5BUf.append(integralH5Pages.split(",")[statecode]);
//				h5BUf.append("?openid="+openid).append("&").append(state);
//				MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
//				if (null!=members ) {
//					Byte memberState=members.getState();
//					if (null!=memberState && memberState.intValue()==1) {
//						h5BUf.append("&memberId="+members.getId());
//						needWriteJwtToken=true;
//					}else {
//						h5BUf.append("&memberId=-1");
//					}
//				}else {
//					h5BUf.append("&memberId=-1");
//				}
//				nickName=userInfo.getString("nickname");
//				redirectUrl=h5BUf.toString();
//				// TODO 预定义
//			}else {
//				//如果是活动扫码默认也写jwttoken
//				needWriteJwtToken=true;
//
//				userInfo=getUserInfo(code, scanCodeInfoMO.getOrganizationId(),scanCodeInfoMO.getActivitySetId());
//				openid=userInfo.getString("openid");
//				organizationId=scanCodeInfoMO.getOrganizationId();
//				//表示是从扫码产品防伪码入口进入
//				nickName=userInfo.getString("nickname");
//				scanCodeInfoMO.setOpenId(userInfo.getString("openid"));
//				//更新扫码信息
//				globalRamCache.putScanCodeInfoMO(state, scanCodeInfoMO);
//
//				// success = 0失败
//
//				redirectUrl="redirect:"+h5pageUrl+"?wxstate="+state+"&activitySetId="+scanCodeInfoMO.getActivitySetId()+"&organizationId="+organizationId;
//			}
//
//			//判断是否需要保存用户
//			MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgIdWithTemp(openid, organizationId);
//			if (null==members) {
//				members=new MarketingMembers();
//				members.setOpenid(openid);
//				members.setWxName(nickName);
//				members.setState((byte)1);
//				// TODO STATE=2
////				members.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
////				members.setOrganizationId(organizationId);
////				if (null!=statecode) {
////					members.setState((byte)2);
////				}
////				marketingMembersService.insert(members);
//				//
//			}else {
//				members.setWxName(nickName);
//				marketingMembersService.update(members);
//			}
//			//如果需要写jwttoken，在积分授权时只有不需要手机号登录时才写token否则手机号登录那里会写
//			if (needWriteJwtToken) {
//				writeJwtToken(response, members);
//			}
////        String redirectUrl="redirect:http://192.168.10.78:7081/?wxstate="+state+"&activitySetId="+scInfoMO.getActivitySetId()+"&organizationId="+scInfoMO.getOrganizationId();
//			log.info("最终跳转路径："+redirectUrl);
//			return  redirectUrl;
//		}
//
//    }

	private Map<String, String> stateToMap(String state) {
		String values[]=state.split("\\&");
		Map<String, String> stateMap=new HashMap<>();
        for (String va : values) {
			String[] key_va=va.split("=");
			stateMap.put(key_va[0], key_va[1]);
		}
		return stateMap;
	}



	private String doBizBySaler(String organizationId, String state,String code,JSONObject userInfo,String redirectUrl,HttpServletResponse response) throws Exception {
		if(StringUtils.isBlank(organizationId)){
			if(log.isErrorEnabled()){
				log.error("[前端授权导购信息异常=>state:{}]",state);
				throw new SuperCodeException("系统授权信息异常");
			}
		}
		MarketingWxMerchants marketingWxMerchants = mWxMerchantsMapper.get(organizationId);
// 导购step-1: 微信授权
		userInfo=getUserInfo(code, organizationId,null);
		String openid=userInfo.getString("openid");

// 导购step-2: 刷新头像
		// 需要返回前端组织Id和用户id[id或者-1]以及openid

		UserWithWechat userWithWechat = marketingSaleMemberService.selectByOpenidAndOrgId(openid, organizationId);


// 导购step-3: 业务处理
		if(userWithWechat != null){
			// TODO 异步
			// 始终刷新微信用户头像,用于同步微信信息
			MarketingWxMember marketingWxMember = new MarketingWxMember();
			marketingWxMember.setOpenid(userInfo.getString("openid"));
			marketingWxMember.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			marketingWxMember.setWxName(userInfo.getString("nickname"));
			marketingWxMember.setOrganizationId(organizationId);
			marketingSaleMemberService.updateWxInfo(marketingWxMember);
			// 说明用户存在,需要自动登录
			log.error("user =>{} define =>{}", userWithWechat.getState().intValue(),SaleUserStatus.ENABLE.getStatus().intValue());
			if(userWithWechat.getState().intValue() != SaleUserStatus.ENABLE.getStatus().intValue()){
				// 非启用状态
				StringBuffer urlParams = new StringBuffer("?");
				urlParams.append("memberId=-1").append("&openid=").append(openid)
						.append("&organizationId=").append(organizationId);
				redirectUrl ="redirect:" + h5pageUrl + WechatConstants.SALER_LOGIN_URL+urlParams.toString();
			}else{
				StringBuffer urlParams = new StringBuffer("?");
				urlParams.append("memberId=").append(userWithWechat.getMemberId()).append("&openid=").append(openid)
						.append("&organizationId=").append(organizationId);
				redirectUrl ="redirect:" + h5pageUrl + WechatConstants.SALER_LOGIN_URL+urlParams.toString();
				MarketingMembers user = new MarketingMembers();
				MemberWithWechat memberWithWechat = modelMapper.map(userWithWechat, MemberWithWechat.class);
				user.setId(user.getId());
				writeJwtToken(response,memberWithWechat);
			}
		}else{
			// 前端需要的信息
			// 推荐前端缓存该信息:组织Id等关键信息不适合url上携带
			StringBuffer sb = new StringBuffer("?");
			sb.append("memberId=-1").append("&openid=").append(openid)
					.append("&organizationId=").append(organizationId);
			redirectUrl ="redirect:" +  h5pageUrl + WechatConstants.SALER_LOGIN_URL+sb.toString();
		}
		log.info("导购扫码最终返回url:"+redirectUrl);
		return  redirectUrl;
	}


	private String doBizPlatform(String redirectUri, String organizationId, String code, HttpServletResponse response) throws Exception {
		MarketingWxMerchants mWxMerchants = mWxMerchantsMapper.getDefaultJgw();
		String appId = mWxMerchants.getMchAppid().trim();
		String secret = mWxMerchants.getMerchantSecret().trim();
		String tokenParams="?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		HttpClientResult tokenhttpResult=HttpRequestUtil.doGet(WechatConstants.AUTH_ACCESS_TOKEN_URL+tokenParams);
		String tokenContent=tokenhttpResult.getContent();
		log.info("调用获取授权access_token后返回内容："+tokenContent);
		if (tokenContent.contains("errcode")) {
			throw new SuperCodeExtException(tokenContent, 500);
		}
		JSONObject accessTokenObj = JSONObject.parseObject(tokenContent);
		String openid = accessTokenObj.getString("openid");
		HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(WechatConstants.ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+secret);
		String body = reHttpClientResult.getContent();
		log.info("请求获取用户信息token返回;"+body);
		JSONObject userInfo = null;
		if (body.contains("access_token")) {
			JSONObject tokenObj=JSONObject.parseObject(body);
			String token=tokenObj.getString("access_token");
			HttpClientResult userInfoResult=HttpRequestUtil.doGet(WechatConstants.WECHAT_USER_INFO+"?access_token="+token+"&openid="+openid+"&lang=zh_CN");
			String userInfoBody=userInfoResult.getContent();
			log.info("判断是否关注过公众号方法获取用户基本信息`返回结果="+userInfoBody);
			if (StringUtils.isBlank(userInfoBody)) {
				throw new SuperCodeExtException(userInfoBody, 500);
			}
			userInfo = JSONObject.parseObject(userInfoBody);
		}
		List<MarketingPlatformOrganization> marketingPlatformOrganizationList = marketingPlatformOrganizationService.selectByOrgId(organizationId);
		String organizationFullName = null;
		if (!CollectionUtils.isEmpty(marketingPlatformOrganizationList)) {
			organizationFullName = marketingPlatformOrganizationList.get(0).getOrganizationFullName();
		}
		// 微信授权
		openid = userInfo.getString("openid");
// 导购step-2: 刷新头像
		// 需要返回前端组织Id和用户id[id或者-1]以及openid
		MemberWithWechat memberWithWechat = new MemberWithWechat();
		MarketingWxMember marketingWxMember = marketingMembersService.getWxMemberByOpenidAndOrgid(openid, organizationId);
		if (marketingWxMember == null) {
			memberWithWechat.setOpenid(openid);
			memberWithWechat.setSex(userInfo.getByte("sex"));
			memberWithWechat.setWxName(userInfo.getString("nickname"));
			memberWithWechat.setProvinceName(userInfo.getString("province"));
			memberWithWechat.setCityName(userInfo.getString("city"));
			memberWithWechat.setState((byte) 2);
			memberWithWechat.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			memberWithWechat.setOrganizationId(organizationId);
			memberWithWechat.setIsRegistered((byte) 0);
			memberWithWechat.setDeviceType((byte)1);
			memberWithWechat.setMemberType((byte)0);
			memberWithWechat.setAppid(appId);
			memberWithWechat.setJgwType((byte)1);
			memberWithWechat.setOrganizationFullName(organizationFullName);
			marketingMembersService.insert(memberWithWechat);
		} else {
			BeanUtils.copyProperties(marketingWxMember, memberWithWechat);
		}
		String wxstate=commonUtil.getUUID();
		ScanCodeInfoMO scanCodeInfoMO = new ScanCodeInfoMO();
		scanCodeInfoMO.setOpenId(openid);
		globalRamCache.putScanCodeInfoMO(wxstate, scanCodeInfoMO);
		String uri = null;
		redirectUri = URLDecoder.decode(redirectUri, "UTF-8");
		redirectUri = StringUtils.replace(redirectUri, ",", "&");
		String[] uris = redirectUri.split("#");
		if (uris.length > 1) {
			String startUrl = uris[0].contains("?")? uris[0]+"&" : uris[0]+"?";
			String firUri = startUrl + "wxstate="+wxstate+"&organizationId="+organizationId;
			uri = firUri;
			for (int i= 1;i<uris.length;i++) {
				uri = uri + "#" + uris[i];
			}
		} else {
			String startUrl = redirectUri.contains("?")? redirectUri+"&" : redirectUri+"?";
			uri = startUrl + "wxstate="+wxstate+"&organizationId="+organizationId;
		}
		return "redirect:" + uri;
	}

}
