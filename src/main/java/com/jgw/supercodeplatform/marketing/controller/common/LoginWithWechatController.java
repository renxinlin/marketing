package com.jgw.supercodeplatform.marketing.controller.common;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dto.common.LoginWithWechat;
import com.jgw.supercodeplatform.marketing.service.common.LoginWithWechatService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "微信授权后前端发起登录")
@RestController
@RequestMapping("/marketing/wx")
public class LoginWithWechatController {

    @Value("${cookie.domain}")
    private String cookieDomain;
    @Autowired
    private LoginWithWechatService loginWithWechatService;

    @ApiOperation("会员登录")
    @PostMapping("/memberLogin")
    public RestResult<?> memberLogin(@RequestBody @Valid LoginWithWechat loginWithWechat, HttpServletResponse response) throws SuperCodeException {
        H5LoginVO h5LoginVO = loginWithWechatService.memberLogin(loginWithWechat);
        if (h5LoginVO == null) {
            return RestResult.fail("根据openid无法登录");
        }
        String jwtToken= JWTUtil.createTokenWithClaim(h5LoginVO);
        Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
        // jwt有效期为2小时，保持一致
        jwtTokenCookie.setMaxAge(60*60*2);
        // 待补充： 其他参数基于传递状况
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain(cookieDomain);
        response.addCookie(jwtTokenCookie);
        response.addHeader("Access-Control-Allow-Origin", "");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");
        return RestResult.success();
    }

    @ApiOperation("导购员登录")
    @PostMapping("/userLogin")
    public RestResult<?> userLogin(@RequestBody @Valid LoginWithWechat loginWithWechat, HttpServletResponse response) throws SuperCodeException {
        H5LoginVO h5LoginVO = loginWithWechatService.userLogin(loginWithWechat);
        if (h5LoginVO == null) {
            return RestResult.fail("根据openid无法登录");
        }
        String jwtToken= JWTUtil.createTokenWithClaim(h5LoginVO);
        Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
        // jwt有效期为2小时，保持一致
        jwtTokenCookie.setMaxAge(60*60*2);
        // 待补充： 其他参数基于传递状况
        jwtTokenCookie.setPath("/");
        jwtTokenCookie.setDomain(cookieDomain);
        response.addCookie(jwtTokenCookie);
        response.addHeader("Access-Control-Allow-Origin", "");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");
        return RestResult.success();
    }

}
