package com.jgw.supercodeplatform.marketing.controller.h5.integral;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 商户公众号绑定
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/front/snBind")
@Api(tags = "获取授权微信商户信息及授权地址")
public class WeixinSNBindFrontController extends CommonUtil {
    
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    
    @Value("${marketing.domain.url}")
    private String wxauthRedirectUri;
    
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取授权微信商户信息及授权地址", notes = "")
    @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "组织id", required = true)
    public RestResult<Map<String, String>> get(String organizationId) throws Exception {
    	MarketingWxMerchants mWxMerchants=marketingWxMerchantsService.selectByOrganizationId(organizationId);
    	RestResult<Map<String, String>> restResult=new RestResult<Map<String, String>>();
    	restResult.setState(200);
    	Map<String, String> data=new HashMap<String, String>();
    	if (null!=mWxMerchants) {
    		data.put("appId", mWxMerchants.getMchAppid());
        	//微信授权需要对redirect_uri进行urlencode
            String wholeUrl=wxauthRedirectUri+"/marketing/front/auth/code";
        	String encoderedirectUri=URLEncoder.encode(wholeUrl, "utf-8");
    		data.put("redriectUrl", encoderedirectUri);
    		restResult.setResults(data);
		}
        return restResult;
    }
    
}
