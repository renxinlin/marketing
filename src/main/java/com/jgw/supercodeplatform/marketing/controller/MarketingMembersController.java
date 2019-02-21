package com.jgw.supercodeplatform.marketing.controller;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonObject;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonProperty;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/members")
public class MarketingMembersController {


    @RequestMapping(value = "",method = RequestMethod.POST)
    @ApiOperation(value = "招募会员（注册）", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult addMember(
            @ApiJsonObject(name = "addMembers", value = {
                    @ApiJsonProperty(key = "Wxid", example = "wxid_asds4ad564sa56d", description = "微信号,必需"),
                    @ApiJsonProperty(key = "mobileId", example = "18268322268", description = "手机号,必需"),
                    @ApiJsonProperty(key = "UserName",example = "zhangsan",description="用户姓名,必需"),
                    @ApiJsonProperty(key = "Sex", example = "男", description = "性别Name,必需"),
                    @ApiJsonProperty(key = "name", example = "liujianqiang", description = "姓名,必需"),
                    @ApiJsonProperty(key = "positionId", example = "f4c1054fe0b84bd0a448070d98110b22", description = "职位id,非必需"),
                    @ApiJsonProperty(key = "positionIdName", example = "普通员工", description = "职位名字,非必需"),
                    @ApiJsonProperty(key = "employeeNumber", example = "101", description = "工号,非必需"),
                    @ApiJsonProperty(key = "employeeDate", example = "2018-09-11", description = "入职时间,非必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", null);
    }
}
