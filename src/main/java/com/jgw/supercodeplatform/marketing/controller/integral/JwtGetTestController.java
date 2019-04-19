package com.jgw.supercodeplatform.marketing.controller.integral;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试时获取jwt-token
 */
@RestController
@RequestMapping("/marketing/jwt")
@Api(tags = "jwt-token生成接口|前端和网页调试使用H5页面使用")
public class JwtGetTestController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
     public RestResult jwt(@ApiIgnore HttpServletResponse response, @ApiIgnore HttpServletRequest request, @RequestBody H5LoginVO user) throws SuperCodeException {
        String tokenWithClaim = JWTUtil.createTokenWithClaim(user);
        response.setHeader("jwt-token",tokenWithClaim);
        // 方便调试
        Cookie ck =new Cookie("jwt-token",tokenWithClaim);
        response.addCookie(ck);
        return RestResult.success("success",tokenWithClaim);
    }
}
