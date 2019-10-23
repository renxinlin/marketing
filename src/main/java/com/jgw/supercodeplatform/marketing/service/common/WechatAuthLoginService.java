package com.jgw.supercodeplatform.marketing.service.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dto.common.WechatPreStore;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WechatAuthLoginService {
    /** 微信登录预存信息key */
    private static final String WECHAT_PRE_STORE = "wechat:pre:store:";
    /** 设置微信预存过期时间一个小时 */
    private static final long WECHAT_PRE_EXPIRETIME = 3600;

    @Value("${marketing.domain.url}")
    private String marketDomainUrl;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;

    /**
     * 微信跳转前预存信息
     * @param wechatPreStore
     * @return 返回uuid
     */
    public String wechatPreStore(WechatPreStore wechatPreStore) {
        String uuid = commonUtil.getUUID();
        String key = WECHAT_PRE_STORE + uuid;
        String wechatPreStoreJosn = JSON.toJSONString(wechatPreStore);
        redisUtil.set(key, wechatPreStoreJosn, WECHAT_PRE_EXPIRETIME);
        return uuid;
    }

    /**
     * 根据uuid找到用户信息并且进行微信登录跳转
     * @param uuid
     * @return 微信跳转后端地址
     */
    public String wechatRedirect(String uuid){
        WechatPreStore wechatPreStore = getWechatByUuid(uuid);
        MarketingWxMerchants marketingWxMerchants = getMerchantsByOrgId(wechatPreStore.getOrganizationId());
        String appid = marketingWxMerchants.getMchAppid();
        String backDomainUrl = marketDomainUrl + "/marketing/wechat/auth";
        String redirectUrl = WechatConstants.WECHAT_AUTH_URL.replace("{appid}", appid).replace("{redirect_uri}", backDomainUrl).replace("{state}", uuid);
        return redirectUrl;
    }

    /**
     * 微信回调之后授权登录，获取该公众号下该用户的openid
     * @param code
     * @param uuid
     * @return
     * @throws Exception
     */
    public String auth(String code, String uuid) throws Exception {
        //通过uuid预存信息的key值
        String key = WECHAT_PRE_STORE + uuid;
        WechatPreStore wechatPreStore = getWechatByUuid(uuid);
        redisUtil.remove(key);
        String organizationId = wechatPreStore.getOrganizationId();
        MarketingWxMerchants marketingWxMerchants = getMerchantsByOrgId(organizationId);
        String organizationName = marketingWxMerchants.getOrganizatioIdlName();
        String appId = marketingWxMerchants.getMchAppid();
        String secret = marketingWxMerchants.getMerchantSecret();
        //获取access_token
        String tokenParams = "?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        HttpClientResult tokenhttpResult= HttpRequestUtil.doGet(WechatConstants.AUTH_ACCESS_TOKEN_URL+tokenParams);
        String tokenContent=tokenhttpResult.getContent();
        log.info("调用获取授权access_token后返回内容："+tokenContent);
        if (tokenContent.contains("errcode")) {
            throw new SuperCodeException(tokenContent, 500);
        }
        JSONObject accessTokenObj=JSONObject.parseObject(tokenContent);
        String openid=accessTokenObj.getString("openid");
        HttpClientResult reHttpClientResult = HttpRequestUtil.doGet(WechatConstants.ACCESS_TOKEN_URL+"&appid="+appId+"&secret="+secret);
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
                userObj.put("organizationId", organizationId);
                String openidKey = RedisKey.WECHAT_OPENID_INFO + openid;
                redisUtil.set(openidKey, userObj.toJSONString(), WECHAT_PRE_EXPIRETIME);
            }
        }
        String redirectUri = wechatPreStore.getFrontRedirectUrl();
        //拼接前端返回数据跳转地址
        String uri = null;
        String[] uris = redirectUri.split("#");
        if (uris.length > 1) {
            String startUrl = uris[0].contains("?")? uris[0]+"&" : uris[0]+"?";
            String firUri = startUrl + "organizationId=" + organizationId + "&openid=" + openid;
            uri = firUri;
            for (int i= 1;i<uris.length;i++) {
                uri = uri + "#" + uris[i];
            }
        } else {
            String startUrl = redirectUri.contains("?")? redirectUri+"&" : redirectUri+"?";
            uri = startUrl + "organizationId=" + organizationId + "&openid=" + openid;
        }
        return uri;
    }


    /**
     * 通过 uuid 获取 WechatPreStore
     * @param uuid
     * @return
     */
    private WechatPreStore getWechatByUuid(String uuid){
        String key = WECHAT_PRE_STORE + uuid;
        String wechatPreStoreJosn = redisUtil.get(key);
        if (StringUtils.isBlank(wechatPreStoreJosn)) {
            throw new SuperCodeExtException("uuid不正确或已过期");
        }
        return JSON.parseObject(wechatPreStoreJosn, WechatPreStore.class);
    }

    /**
     * 通过 organizationId 获取 MarketingWxMerchants
     * @param organizationId
     * @return
     */
    private MarketingWxMerchants getMerchantsByOrgId(String organizationId){
        MarketingWxMerchants marketingWxMerchants = marketingWxMerchantsService.selectByOrganizationId(organizationId);
        if (marketingWxMerchants == null) {
            log.error("找不到组织id为" + organizationId + "对应的微信信息");
            throw new SuperCodeExtException("找不到该组织对应的微信信息");
        }
        String organizationName = marketingWxMerchants.getOrganizatioIdlName();
        if (marketingWxMerchants.getMerchantType() == 1) {
            marketingWxMerchants = marketingWxMerchantsService.getJgw();
        }
        marketingWxMerchants.setOrganizatioIdlName(organizationName);
        return marketingWxMerchants;
    }

}
