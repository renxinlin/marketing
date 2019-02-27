package com.jgw.supercodeplatform.marketing.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/marketing/scan")
public class ScanCodeController {
    
    @Autowired
    private MarketingActivitySetService mActivitySetService;
    
    /**
     *    该接口负责获取到扫码信息判断该码是否参与活动及对活动的校验
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "码平台调用跳转路径", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public String bind() throws Exception {
    	boolean flag=mActivitySetService.judgement();
        return "redirect:http://www.baidu.com";
    }

}
