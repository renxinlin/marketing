package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.JWTUtil;
import com.jgw.supercodeplatform.marketing.dto.integral.JwtUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/marketing/jwt")
@Api(tags = "jwt-token生成接口")
public class JwtGetTestController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    @ApiImplicitParam(name = "memberId", paramType = "header", defaultValue = "1", value = "1", required = true)
    public RestResult jwt(@ApiIgnore HttpServletResponse response, @RequestBody JwtUser user) throws SuperCodeException {
        String tokenWithClaim = JWTUtil.createTokenWithClaim(user);
        response.setHeader("jwt-token",tokenWithClaim);
        return RestResult.success("success",tokenWithClaim);

    }
}
