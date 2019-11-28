package com.jgw.supercodeplatform.two.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.two.constants.JudgeBindConstants;
import com.jgw.supercodeplatform.two.dto.MarketingSaleUserBindMobileParam;
import com.jgw.supercodeplatform.two.service.UserLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author fangshiping
 * @date 2019/11/14 15:23
 */
@RestController
@RequestMapping("marketing/two/user")
@Api(tags = "2.0登录迁移接口")
public class UserLoginController {
    @Autowired
    private MarketingUserMapper marketingUserMapper;

    @Autowired
    private UserLoginService service;

    @Autowired
    private CommonService commonService;

    @Value("${cookie.domain}")
    private String cookieDomain;

    @GetMapping("/login")
    @ApiOperation(value = "导购员账号密码登录", notes = "2.0登陆")
    public RestResult<?> login(@RequestParam String loginName, @RequestParam String password, HttpServletResponse response) throws SuperCodeException {
        String md5Password= DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("LoginName",loginName);
        queryWrapper.eq("Password",md5Password);
        queryWrapper.eq("Version",1); // 1 数据来自2.0

        MarketingUser marketingUser=marketingUserMapper.selectOne(queryWrapper);
        if (marketingUser!=null){
            if (JudgeBindConstants.HAVEBIND.equals(marketingUser.getBinding())){
                throw new SuperCodeException("该用户已经绑定！");
            }else{
                //返回会员主键Id
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("id",marketingUser.getId());
                objectObjectHashMap.put("organizationId",marketingUser.getOrganizationId());
                return RestResult.success(200,"success",objectObjectHashMap);
            }
            /*String jwtToken= JWTUtil.createTokenWithClaim(h5LoginVO);
            Cookie jwtTokenCookie = new Cookie(CommonConstants.JWT_TOKEN,jwtToken);
            // jwt有效期为2小时，保持一致
            jwtTokenCookie.setMaxAge(60*60*2);
            // 待补充： 其他参数基于传递状况
            jwtTokenCookie.setPath("/");
            jwtTokenCookie.setDomain(cookieDomain);
            response.addCookie(jwtTokenCookie);
            response.addHeader("Access-Control-Allow-Origin", "");
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, ActivitySet-Cookie, *");*/
        }
        return RestResult.success(500,"不存在该用户",null);
    }

    @GetMapping("/sendPhoneCode")
    @ApiOperation(value = "发送手机验证码", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "13925121452", value = "手机号", required = true)
    public RestResult<String> sendPhoneCode(@RequestParam String mobile) throws Exception {
        return commonService.sendPhoneCode(mobile);
    }

    @PostMapping("/bind")
    @ApiOperation(value = "导购员绑定手机号", notes = "2.0绑定手机号")
    public RestResult bind(@Valid @RequestBody MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam) throws SuperCodeException {
        return service.bindMobile(marketingSaleUserBindMobileParam);
    }
}
