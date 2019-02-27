package com.jgw.supercodeplatform.marketing.controller.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
/**
 * 微信授权等
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/auth")
public class WeixinAuthController {
	protected static Logger logger = LoggerFactory.getLogger(WeixinAuthController.class);
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;

    
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public String getWXCode(String code ,String state) throws Exception {
    	logger.info("微信授权回调获取code="+code+",state="+state);
    	String tokenParams="?appid="+WechatConstants.APPID+"&secret="+WechatConstants.secret+"&code="+code+"&grant_type=authorization_code";
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
        JSONObject userinfoObj=JSONObject.parseObject(userinfoContent);
        
        
        //1、判断该用户状态是否合法
        //用户不存在保存用户
        //2、判断该用户是否超过每天的扫码量
       //3.判断是否需要手机登录及注册及绑定对应的openid或更新手机号
      
        String nickname=userinfoObj.getString("nickname");
        return "success";
    }
    
}
