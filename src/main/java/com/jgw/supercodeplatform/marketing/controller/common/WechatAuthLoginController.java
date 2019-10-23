package com.jgw.supercodeplatform.marketing.controller.common;


import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.common.WechatPreStore;
import com.jgw.supercodeplatform.marketing.service.common.WechatAuthLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@Api(tags = "微信登录相关")
@RequestMapping("/marketing/wechat")
public class WechatAuthLoginController {

    @Autowired
    private WechatAuthLoginService wechatAuthLoginService;

    @ApiOperation("跳转微信之前，先预存的信息，如微信登录后跳转前端地址等信息")
    @PostMapping("/preStore")
    @ResponseBody
    public RestResult<String> preStore(@RequestBody @Valid WechatPreStore wechatPreStore){
        String uuid = wechatAuthLoginService.wechatPreStore(wechatPreStore);
        return RestResult.successWithData(uuid);
    }

    @ApiOperation("前端要进行时微信登录跳转地址")
    @GetMapping("/redirect")
    public String wechatRedirect(@RequestParam String uuid){
        String redirectUrl = wechatAuthLoginService.wechatRedirect(uuid);
        return "redirect:" + redirectUrl;
    }

    @ApiOperation("微信登录后跳转前端地址，链接上会携带organizationId和&openid")
    @GetMapping("/auth")
    public String auth(String code ,String state) throws Exception {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(state)) {
            log.error("微信授权登录跳转code为：{}，state为：{}");
            throw new SuperCodeExtException("code或者 state不能为空");
        }
        String redirectUrl = wechatAuthLoginService.auth(code, state);
        return "redirect:" + redirectUrl;
    }

}
