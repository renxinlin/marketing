package com.jgw.supercodeplatform.marketing.controller.activity;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonObject;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonProperty;
import com.jgw.supercodeplatform.marketing.service.user.OrganizationPortraitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@RestController
@RequestMapping("/marketing/portrait")
@Api(tags = "会员画像管理")
public class OrganizationPortraitController  extends CommonUtil {

    @Autowired
    private OrganizationPortraitService organizationPortraitService;

    @RequestMapping(value = "/getSelectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "根据组织id获取组织已选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "dsadsad165156163a1sddasd", value = "组织id,必需")
    })
    public RestResult getSelectedPortrait(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", organizationPortraitService.getSelectedPortrait(params));
    }

    @RequestMapping(value = "/getUnselectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "根据组织id获取组织未选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "dsadsad165156163a1sddasd", value = "组织id,必需")
    })
    public RestResult getUnselectedPortrait(@ApiIgnore @RequestParam Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", organizationPortraitService.getUnselectedPortrait(params));
    }


    @RequestMapping(value = "/addOrgPor",method = RequestMethod.POST)
    @ApiOperation(value = "添加组织画像关系", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult addOrgPortrait(
            @ApiJsonObject(name = "addOrgPortrait", value = {
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需"),
                    @ApiJsonProperty(key = "organizationFullName",example = "中化通讯公司",description="组织全称,必需"),
                    @ApiJsonProperty(key = "portraitCode", example = "14001", description = "画像编码,必需"),
                    @ApiJsonProperty(key = "portraitName", example = "手机", description = "画像名称,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", null);
    }

    @RequestMapping(value = "/deleOrgPor",method = RequestMethod.POST)
    @ApiOperation(value = "删除组织画像关系", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleOrgPortrait(
            @ApiJsonObject(name = "deleOrgPortrait", value = {
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需"),
                    @ApiJsonProperty(key = "organizationFullName",example = "中化通讯公司",description="组织全称,必需"),
                    @ApiJsonProperty(key = "portraitCode", example = "14001", description = "画像编码,必需"),
                    @ApiJsonProperty(key = "portraitName", example = "手机", description = "画像名称,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        return new RestResult(200, "success", null);
    }

}
