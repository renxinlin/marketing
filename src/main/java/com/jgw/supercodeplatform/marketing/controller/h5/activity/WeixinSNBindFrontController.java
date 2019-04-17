package com.jgw.supercodeplatform.marketing.controller.h5.activity;

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
@Api(tags = "获取企业公众号信息")
public class WeixinSNBindFrontController extends CommonUtil {
    
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "获取微信商户信息", notes = "")
    @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "组织id", required = true)
    public RestResult<MarketingWxMerchants> get(String organizationId) throws Exception {
    	MarketingWxMerchants mWxMerchants=marketingWxMerchantsService.selectByOrganizationId(organizationId);
    	RestResult<MarketingWxMerchants> restResult=new RestResult<MarketingWxMerchants>();
    	restResult.setState(200);
    	if (null!=mWxMerchants) {
    		mWxMerchants.setMerchantSecret(null);
    		mWxMerchants.setMerchantKey(null);
    		mWxMerchants.setCertificatePassword(null);
    		restResult.setResults(mWxMerchants);
		}
        return restResult;
    }
    
}
