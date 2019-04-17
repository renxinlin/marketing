package com.jgw.supercodeplatform.marketing.controller.wechat;

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
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.user.MarketingMembersService;

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
    public String getWXCode(String code ,String state) throws Exception {
    	logger.info("微信授权回调获取code="+code+",state="+state);
    	if (StringUtils.isBlank(state)) {
    		throw new SuperCodeException("state不能为空", 500);
		}
    	String redirectUrl=null;
    	String nickName=null;
    	String openid=null;
    	String organizationId=null;
    	JSONObject userInfo=null;
    	String statevalue=null;
    	Integer statecode=null;
    	if (state.contains("_")) {
    		String[] statearr=state.split("_");
    		statecode=Integer.valueOf(statearr[0]);
    		statevalue=statearr[1];
		}else {
			statevalue=state;
		}
    	
    	ScanCodeInfoMO scanCodeInfoMO=globalRamCache.getScanCodeInfoMO(statevalue);
    	logger.info("根据code="+code+" 查询到的scanCodeInfoMO="+scanCodeInfoMO+",statecode="+statecode+",statevalue="+statevalue);
    	
    	//表示不是从扫码产品防伪码入口进入
    	if (null==scanCodeInfoMO) {
    		userInfo=getUserInfo(code, state);
    		openid=userInfo.getString("openid");
    		StringBuffer h5BUf=new StringBuffer();
    		h5BUf.append(integralH5Pages.split(",")[statecode]).append("&openId="+openid);
    		MarketingMembers members=marketingMembersService.selectByOpenIdAndOrgId(openid, organizationId);
    		if (null!=members) {
    			h5BUf.append("&memberId="+members.getId());
    		}
			nickName=userInfo.getString("nickname");
    		redirectUrl=h5BUf.toString();
    		organizationId=statevalue.split("_")[0];
		}else {
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
    	
    	synchronized (this) {
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
    	}
//        String redirectUrl="redirect:http://192.168.10.78:7081/?wxstate="+state+"&activitySetId="+scInfoMO.getActivitySetId()+"&organizationId="+scInfoMO.getOrganizationId();
    	logger.info("最终跳转路径："+redirectUrl);
    	return  redirectUrl;
    }

    public static void main(String[] args) {
		String dd="ddff";
		System.out.println(dd.split("_")[0]);
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
		String access_token=accessTokenObj.getString("access_token");
		Long expires_in=accessTokenObj.getLong("expires_in");
		String refresh_token=accessTokenObj.getString("refresh_token");
		String scope=accessTokenObj.getString("scope");
		
		String userInfoParams="?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		HttpClientResult userinfohttpResult=HttpRequestUtil.doGet(WechatConstants.USER_INFO_URL+userInfoParams);
		String userinfoContent=userinfohttpResult.getContent();
		logger.info("调用获取基础用户信息接口后返回内容："+userinfoContent);
		if (userinfoContent.contains("errcode")) {
			throw new SuperCodeException(tokenContent, 500);
		}
		logger.info("--------------------授权成功--------------------------------");
		JSONObject userinfoObj=JSONObject.parseObject(userinfoContent);
		return userinfoObj;
    }
}
