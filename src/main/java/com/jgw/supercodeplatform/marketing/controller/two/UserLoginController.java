package com.jgw.supercodeplatform.marketing.controller.two;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingSaleUserBindMobileParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUser;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.user.MarketingSaleMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author fangshiping
 * @date 2019/11/12 10:34
 */
@RestController
@RequestMapping("/marketing/two/user")
@Api(tags = "2.0登录迁移接口")
public class UserLoginController {

    @Autowired
    private MarketingUserMapper marketingUserMapper;

    @Autowired
    private MarketingSaleMemberService service;

    @Autowired
    private CommonService commonService;

    @GetMapping("login")
    @ApiOperation(value = "导购员账号密码登录", notes = "2.0登陆")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<?> login(@RequestParam String loginName, @RequestParam String password){
        String md5Password= DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("LoginName",loginName);
        queryWrapper.eq("Password",md5Password);
        MarketingUser marketingUser=marketingUserMapper.selectOne(queryWrapper);
        if (marketingUser!=null){
            return RestResult.success(200,"success",marketingUser.getId());
        }
        return RestResult.success(500,"fail",null);
    }

    @GetMapping("/sendPhoneCode")
    @ApiOperation(value = "发送手机验证码", notes = "")
    @ApiImplicitParam(name = "mobile", paramType = "query", defaultValue = "13925121452", value = "手机号", required = true)
    public RestResult<String> sendPhoneCode(@RequestParam String mobile) throws Exception {
        return commonService.sendPhoneCode(mobile);
    }

    @GetMapping("/bind")
    @ApiOperation(value = "导购员绑定手机号", notes = "2.0绑定手机号")
    @ApiImplicitParam(name = "jwt-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult bind(@RequestBody MarketingSaleUserBindMobileParam marketingSaleUserBindMobileParam) throws SuperCodeException {
        return service.bindMobile(marketingSaleUserBindMobileParam);
    }
}
