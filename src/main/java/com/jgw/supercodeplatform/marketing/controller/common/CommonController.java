package com.jgw.supercodeplatform.marketing.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dto.WxSignPram;
import com.jgw.supercodeplatform.marketing.dto.common.CustomPreStore;
import com.jgw.supercodeplatform.marketing.dto.coupon.UrlParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.vo.activity.WxSignVo;
import com.jgw.supercodeplatform.marketing.vo.common.WxMerchants;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/marketing/common")
@Api(tags = "公共接口")
@Slf4j
public class CommonController extends CommonUtil {
     @Autowired
    private CommonService service;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    
    @RequestMapping(value = "/sendPhoneCode",method = RequestMethod.GET)
    @ApiOperation(value = "发送手机验证码", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "13925121452", value = "手机号", required = true)
    public RestResult<String> sendPhoneCode(@RequestParam String mobile) throws Exception {
        return service.sendPhoneCode(mobile);
    }


    /**
     * 创建二维码
     * @param content
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    @ApiOperation(value = "生成二维码接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", paramType = "query", defaultValue = "http://www.baidu.com", value = "", required = true),
    })
    public  boolean createQrCode(String content,HttpServletResponse response) throws WriterException, IOException,Exception{
        return service.generateQR(content, response);
    }
    
    @RequestMapping(value = "/getJssdkInfo",method = RequestMethod.GET)
    @ApiOperation(value = "获取jssdk权限认证信息", notes = "")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "组织id", required = true),
        @ApiImplicitParam(name = "url", paramType = "query", defaultValue = "http://www.baidu.com", value = "签名页面url", required = true)
   })
    public RestResult<Map<String, String>> get(@RequestParam("organizationId")String organizationId,@RequestParam("url")String url) throws Exception {
    	log.info("签名信息,organizationId="+organizationId+",url="+url);
    	MarketingWxMerchants mWxMerchants=marketingWxMerchantsService.selectByOrganizationId(organizationId);
    	if (mWxMerchants == null) {
    	    mWxMerchants = marketingWxMerchantsService.getDefaultJgw();
        }
    	if (null!=mWxMerchants && mWxMerchants.getMerchantType().intValue() == 1) {
            if (mWxMerchants.getJgwId() != null) {
                mWxMerchants = marketingWxMerchantsService.getJgw(mWxMerchants.getJgwId());
            } else {
                mWxMerchants = marketingWxMerchantsService.getDefaultJgw();
            }
		}

    	String ticket = service.getTicketByAccessToken(mWxMerchants.getMchAppid(), mWxMerchants.getMerchantSecret(), organizationId);
        String noncestr=WXPayUtil.generateNonceStr().toLowerCase();
    	long timestamp=WXPayUtil.getCurrentTimestamp();
    	Map<String, String>sinMap=new HashMap<String, String>();
    	sinMap.put("noncestr", noncestr);
    	sinMap.put("jsapi_ticket", ticket);

        sinMap.put("timestamp", timestamp+"");
    	sinMap.put("url", url);
    	//
        String sha1String1 = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
    	String signature=CommonUtil.sha1Encrypt(sha1String1);
        log.info("==================start log=====================");
    	log.info(sha1String1);
        log.info(signature);
    	
    	RestResult<Map<String, String>> restResult=new RestResult<Map<String, String>>();
    	restResult.setState(200);
    	Map<String, String> data=new HashMap<String, String>();
    	data.put("noncestr", noncestr);
    	data.put("timestamp", timestamp+"");
    	data.put("appId", mWxMerchants.getMchAppid());
    	data.put("signature", signature);
    	restResult.setResults(data);
    	restResult.setState(200);
    	
        return restResult;
    }

    @PostMapping("/jgwJssdkinfo")
    @ApiOperation("获取甲骨文微信授权信息")
    public RestResult<Map<String, String>> getJgwJssdkinfo(@RequestBody @Valid UrlParam urlParam) throws Exception {
        String url = StringUtils.split(urlParam.getUrl(), "#")[0];
        String paramAppid = urlParam.getAppid();
        MarketingWxMerchants mWxMerchants = null;
        if (StringUtils.isNotBlank(paramAppid)) {
            mWxMerchants = marketingWxMerchantsService.getByAppid(paramAppid);
        } else {
            mWxMerchants = marketingWxMerchantsService.getDefaultJgw();
        }
        if (mWxMerchants == null) {
            throw new SuperCodeExtException("未找到对应的微信配置信息");
        }
        String ticket = service.getTicketByAccessToken(mWxMerchants.getMchAppid(), mWxMerchants.getMerchantSecret(), mWxMerchants.getOrganizationId());
        String noncestr=WXPayUtil.generateNonceStr().toLowerCase();
        long timestamp=WXPayUtil.getCurrentTimestamp();
        Map<String, String>sinMap=new HashMap<String, String>();
        sinMap.put("noncestr", noncestr);
        sinMap.put("jsapi_ticket", ticket);
        sinMap.put("timestamp", timestamp+"");
        sinMap.put("url", url);
        //
        String sha1String1 = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature=DigestUtils.sha1Hex(sha1String1);
        //String signature=CommonUtil.sha1Encrypt(sha1String1);
        log.info("签名前======>{}", sha1String1);
        log.info("签名后======>", signature);
        RestResult<Map<String, String>> restResult=new RestResult<Map<String, String>>();
        restResult.setState(200);
        Map<String, String> data=new HashMap<String, String>();
        data.put("noncestr", noncestr);
        data.put("timestamp", timestamp+"");
        data.put("appId", mWxMerchants.getMchAppid());
        data.put("signature", signature);
        restResult.setResults(data);
        restResult.setState(200);
        return restResult;
    }


    @PostMapping("/getSign")
    @ApiOperation("根据appid和secret获取签名")
    public RestResult<WxSignVo> get(@RequestBody @Valid WxSignPram wxSignPram) throws Exception {
        String appId = wxSignPram.getAppId();
        String appSecret = wxSignPram.getAppSecret();
        String url = wxSignPram.getUrl();
        log.info("签名信息,appid:{}，appsecret:{}", wxSignPram.getAppId(), wxSignPram.getAppSecret());
        String access_token_url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(access_token_url);
        String body=reHttpClientResult.getContent();
        log.info("请求获取用户信息token返回;"+body);
        if (!body.contains("access_token")) {
            return RestResult.fail("获取签名失败", null);
        }
        JSONObject tokenObj=JSONObject.parseObject(body);
        String accessToken=tokenObj.getString("access_token");
        HttpClientResult result=HttpRequestUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
        String tickContent=result.getContent();
        log.info("获取到tick的数据："+tickContent);
        String ticket = JSONObject.parseObject(tickContent).getString("ticket");
        log.info("获取到tick的数据："+ticket);
        // todo tickContent返回错误的处理，access_token错误的处理，之前日志好像抛出一次access_token错误
        String noncestr=WXPayUtil.generateNonceStr().toLowerCase();
        long timestamp=WXPayUtil.getCurrentTimestamp();
        String sha1String1 = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature=CommonUtil.sha1Encrypt(sha1String1);
        log.info("==================start log=====================");
        log.info(sha1String1);
        log.info(signature);

        WxSignVo wxSignVo = new WxSignVo();
        wxSignVo.setAppId(appId);
        wxSignVo.setAppSecret(appSecret);
        wxSignVo.setNoncestr(noncestr);
        wxSignVo.setSignature(signature);
        wxSignVo.setTimestamp(timestamp + "");
        return RestResult.successWithData(wxSignVo);
    }

    @GetMapping("/wxMerchants")
    @ApiOperation("根据organizationId获取微信公众号信息")
    public RestResult<WxMerchants> getWxMerchants(@RequestParam String organizationId) {
        MarketingWxMerchants mWxMerchants = marketingWxMerchantsService.selectByOrganizationId(organizationId);
        if (mWxMerchants == null ) {
            throw new SuperCodeExtException("该组织对应的微信信息不存在");
        }
        if (mWxMerchants.getMerchantType() == 1) {
            if (mWxMerchants.getJgwId() != null) {
                mWxMerchants = marketingWxMerchantsService.getJgw(mWxMerchants.getJgwId());
            } else {
                mWxMerchants = marketingWxMerchantsService.getDefaultJgw();
            }
        }
        WxMerchants wxMerchants = new WxMerchants();
        wxMerchants.setAppid(mWxMerchants.getMchAppid());
        return RestResult.successWithData(wxMerchants);
    }

    @ApiOperation("用户自定义根据码和码制预存数据")
    @PostMapping("/custom/prestore")
    public RestResult<Void> customPrestore(@RequestBody @Valid CustomPreStore customPreStore) {
        String key = RedisKey.CUSTOM_PRESTORE + customPreStore.getCodeType() + ":" + customPreStore.getCode() + ":" + customPreStore.getStoreType();
        redisUtil.set(key, customPreStore.getStoreData(), 3600L);
        return RestResult.success();
    }

    @ApiOperation("用户获取预存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", paramType = "query",  value = "码制", required = true),
            @ApiImplicitParam(name = "code", paramType = "query",  value = "码", required = true),
            @ApiImplicitParam(name = "storeType", paramType = "query",  value = "存储类型", required = true),
    })
    @GetMapping("/custom/getstore")
    public RestResult<String> getCustomStore(@RequestParam String codeType, @RequestParam String code, @RequestParam Integer storeType){
        String key = RedisKey.CUSTOM_PRESTORE + codeType + ":" + code + ":" + storeType;
        String data = redisUtil.get(key);
        return RestResult.successDefault(data);
    }

}
