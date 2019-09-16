package com.jgw.supercodeplatform.marketing.controller.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.jgw.supercodeplatform.marketing.dto.WxSignPram;
import com.jgw.supercodeplatform.marketing.vo.activity.WxSignVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.HttpClientResult;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.HttpRequestUtil;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;
import com.jgw.supercodeplatform.marketing.weixinpay.WXPayUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/common")
@Api(tags = "公共接口")
public class CommonController extends CommonUtil {
	protected static Logger logger = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private CommonService service;

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
    	logger.info("签名信息,organizationId="+organizationId+",url="+url);
    	MarketingWxMerchants mWxMerchants=marketingWxMerchantsService.selectByOrganizationId(organizationId);
    	if (null==mWxMerchants) {
           throw new SuperCodeException("无法获取商户公众号信息", 500);
		}
    	String accessToken=service.getAccessTokenByOrgId(mWxMerchants.getMchAppid(), mWxMerchants.getMerchantSecret(), organizationId);
        // TODO 测试
    	HttpClientResult result=HttpRequestUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
    	String tickContent=result.getContent();
    	logger.info("获取到tick的数据："+tickContent);
        String ticket = JSONObject.parseObject(tickContent).getString("ticket");
        logger.info("获取到tick的数据："+ticket);
        // todo tickContent返回错误的处理，access_token错误的处理，之前日志好像抛出一次access_token错误
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
        logger.error("==================start log=====================");
    	logger.error(sha1String1);
        logger.error(signature);
    	
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
        logger.info("签名信息,appid:{}，appsecret:{}", wxSignPram.getAppId(), wxSignPram.getAppSecret());
        String access_token_url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
        HttpClientResult reHttpClientResult=HttpRequestUtil.doGet(access_token_url);
        String body=reHttpClientResult.getContent();
        logger.info("请求获取用户信息token返回;"+body);
        if (!body.contains("access_token")) {
            return RestResult.fail("获取签名失败", null);
        }
        JSONObject tokenObj=JSONObject.parseObject(body);
        String accessToken=tokenObj.getString("access_token");
        HttpClientResult result=HttpRequestUtil.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi");
        String tickContent=result.getContent();
        logger.info("获取到tick的数据："+tickContent);
        String ticket = JSONObject.parseObject(tickContent).getString("ticket");
        logger.info("获取到tick的数据："+ticket);
        // todo tickContent返回错误的处理，access_token错误的处理，之前日志好像抛出一次access_token错误
        String noncestr=WXPayUtil.generateNonceStr().toLowerCase();
        long timestamp=WXPayUtil.getCurrentTimestamp();
        String sha1String1 = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
        String signature=CommonUtil.sha1Encrypt(sha1String1);
        logger.error("==================start log=====================");
        logger.error(sha1String1);
        logger.error(signature);

        WxSignVo wxSignVo = new WxSignVo();
        wxSignVo.setAppId(appId);
        wxSignVo.setAppSecret(appSecret);
        wxSignVo.setNoncestr(noncestr);
        wxSignVo.setSignature(signature);
        wxSignVo.setTimestamp(timestamp + "");
        return RestResult.successWithData(wxSignVo);
    }

}
