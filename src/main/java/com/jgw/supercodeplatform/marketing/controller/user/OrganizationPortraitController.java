package com.jgw.supercodeplatform.marketing.controller.user;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonObject;
import com.jgw.supercodeplatform.marketing.config.swagger.ApiJsonProperty;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.service.user.OrganizationPortraitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/portrait")
@Api(tags = "会员画像管理")
public class OrganizationPortraitController  extends CommonUtil {

    @Autowired
    private OrganizationPortraitService organizationPortraitService;

    @RequestMapping(value = "/getSelectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织已选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<List<MarketingOrganizationPortraitListParam>> getSelectedPortrait() throws Exception {
        return new RestResult<List<MarketingOrganizationPortraitListParam>>(200, "success", organizationPortraitService.getSelectedPortrait(null));
    }

    @RequestMapping(value = "/getUnselectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织未选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getUnselectedPortrait() throws Exception {
        return new RestResult(200, "success", organizationPortraitService.getUnselectedPortrait(null));
    }


    @RequestMapping(value = "/addOrgPor",method = RequestMethod.POST)
    @ApiOperation(value = "保存组织画像关系", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> addOrgPortrait(
            @ApiJsonObject(name = "addOrgPortrait", value = {
                    @ApiJsonProperty(key = "portraitCodeList", example = "\"[Mobile,UserName]\"", description = "画像编码list,必需"),
                    //@ApiJsonProperty(key = "portraitName", example = "手机", description = "画像名称,必需")
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "portraitCodeList");
        return organizationPortraitService.addOrgPortrait(params);
    }

/*    @RequestMapping(value = "/deleOrgPor",method = RequestMethod.POST)
    @ApiOperation(value = "删除组织画像关系", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> deleOrgPortrait(
            @ApiJsonObject(name = "deleOrgPortrait", value = {
                    @ApiJsonProperty(key = "organizationId", example = "dsadsad165156163a1sddasd", description = "组织Id,必需"),
                    @ApiJsonProperty(key = "portraitCode", example = "Mobile", description = "画像编码,必需"),
            })
            @RequestBody Map<String, Object> params) throws Exception {
        validateRequestParamAndValueNotNull(params, "organizationId","portraitCode");
        int record = organizationPortraitService.deleOrgPortrait(params);
        if (record!=0){
            return new RestResult(200, "success", null);
        }else{
            return new RestResult(500, "删除组织画像关系失败", null);
        }

    }*/

}
