package com.jgw.supercodeplatform.marketing.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;
import com.jgw.supercodeplatform.marketing.service.weixin.MarketingWxMerchantsService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
/**
 * 商户公众号绑定
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/snBind")
public class WeixinSNBindController {
    
    @Autowired
    private MarketingWxMerchantsService marketingWxMerchantsService;
    
    @RequestMapping(value = "/bind",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息绑定", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> bind(@RequestBody MarketingWxMerchantsParam wxMerchantsParam) throws Exception {
        return new RestResult(200, "success", null);
    }
    
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息修改", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> update(@RequestBody MarketingWxMerchantsParam wxMerchantsParam) throws Exception {
        return new RestResult(200, "success", null);
    }
    
    @RequestMapping(value = "/get",method = RequestMethod.POST)
    @ApiOperation(value = "微信商户信息修改", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<MarketingWxMerchants> get() throws Exception {
    	
        return marketingWxMerchantsService.get();
    }
    
}
