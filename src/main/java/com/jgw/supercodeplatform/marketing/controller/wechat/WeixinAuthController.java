package com.jgw.supercodeplatform.marketing.controller.wechat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

import io.swagger.annotations.Api;
/**
 * 微信授权等
 * @author czm
 *
 */
@Controller
@RequestMapping("/marketing/front/auth")
@Api(tags = "微信授权回调地址")
public class WeixinAuthController {
	protected static Logger logger = LoggerFactory.getLogger(WeixinAuthController.class);

	@Autowired
	private MarketingMembersService marketingMembersService;
	
	@Autowired
	private GlobalRamCache globalRamCache;
	
    @Value("${marketing.activity.h5page.url}")
    private String h5pageUrl;
    
    @Value("${marketing.integral.h5page.urls}")
    private String integralH5Pages;
    
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
    	//表示不是从扫码产品防伪码入口进入
    	if (null==scanCodeInfoMO) {
    		organizationId=statearr[1];
    		userInfo=getUserInfo(code, organizationId);
    		openid=userInfo.getString("openid");
    		StringBuffer h5BUf=new StringBuffer();
    		h5BUf.append("redirect:");
    		h5BUf.append(integralH5Pages.split(",")[statecode]);
    		h5BUf.append("?openid="+openid);
    		if (null!=statecode && 0==statecode) {
    			h5BUf.append("&uuid="+statearr[2]);
			}
    		h5BUf.append("&organizationId="+organizationId);
    		MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgId(openid, organizationId);
    		if (null!=members) {
    			h5BUf.append("&memberId="+members.getId());
    		}else {
    			needWriteJwtToken=true;
    		}
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
			redirectUrl="redirect:"+h5pageUrl+"?wxstate="+state+"&activitySetId="+scanCodeInfoMO.getActivitySetId()+"&organizationId="+organizationId;
		}
    	
		//判断是否需要保存用户
		MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgId(openid, organizationId);
		if (null==members) {
			members=new MarketingMembers();
			members.setOpenid(openid);
			members.setWxName(nickName);
			members.setWechatHeadImgUrl(userInfo.getString("headimgurl"));
			members.setOrganizationId(organizationId);
			if (null!=statecode) {
				members.setState((byte)0);
			}
			marketingMembersService.addMember(members);
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

	private void writeJwtToken(HttpServletResponse response, MarketingMembers members) {
		try {
			H5LoginVO h5LoginVO=new H5LoginVO();
			h5LoginVO.setHaveIntegral(members.getHaveIntegral());
			h5LoginVO.setMemberId(members.getId());
			h5LoginVO.setMobile(members.getMobile());
			h5LoginVO.setWechatHeadImgUrl(members.getWechatHeadImgUrl());
			h5LoginVO.setMemberName(members.getUserName()==null?members.getWxName():members.getUserName());
			String jwtToken=JWTUtil.createTokenWithClaim(h5LoginVO);
			Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
			// jwt有效期为2小时，保持一致
			jwtTokenCookie.setMaxAge(60*60*2);
			// 待补充： 其他参数基于传递状况
			// jwtTokenCookie.setPath();
			response.addCookie(jwtTokenCookie);
//			response.addHeader("jwt-token", jwtToken);
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
}
