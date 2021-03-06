package com.jgw.supercodeplatform.marketing.controller.user;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingOrganizationPortraitParam;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingUnitcode;
import com.jgw.supercodeplatform.marketing.service.user.OrganizationPortraitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marketing/portrait")
@Api(tags = "会员画像管理")
public class OrganizationPortraitController  extends CommonUtil {

    @Autowired
    private OrganizationPortraitService organizationPortraitService;

    @RequestMapping(value = "/getSelectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织已选的画像编码和标签", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<List<MarketingOrganizationPortraitListParam>> getSelectedPortrait() throws Exception {
        // 前端格式修改，合并画像和标签接口
        List<MarketingOrganizationPortraitListParam> selectedPortrait = organizationPortraitService.getSelectedPortrait(null);
        List<MarketingOrganizationPortraitListParam> selectedLabel = organizationPortraitService.getSelectedLabel(getOrganizationId());
        selectedPortrait.addAll(selectedLabel);

        return new RestResult<List<MarketingOrganizationPortraitListParam>>(200, "success",selectedPortrait);
    }

    @RequestMapping(value = "/getUnselectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织未选的画像编码和标签", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getUnselectedPortrait() throws Exception {
        // 前端格式修改，合并画像和标签接口
        List<MarketingUnitcode> unselectedPortrait = organizationPortraitService.getUnselectedPortrait(null);
        List<MarketingUnitcode> unSelectedLabel = organizationPortraitService.getUnSelectedLabel(getOrganizationId());
        unselectedPortrait.addAll(unSelectedLabel);
        return new RestResult(200, "success", unselectedPortrait);
    }


    @RequestMapping(value = "/addOrgPor",method = RequestMethod.POST)
    @ApiOperation(value = "保存组织画像关系|标签关系", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> addOrgPortrait(@RequestBody List<MarketingOrganizationPortraitParam> params) throws Exception {
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



    @RequestMapping(value = "/getSelectedLabel", method = RequestMethod.GET)
    @ApiOperation(value = "获取已经选择的标签", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<List<MarketingOrganizationPortraitListParam>> getSelectedLabel() throws Exception {
        return new RestResult<List<MarketingOrganizationPortraitListParam>>(200, "success", organizationPortraitService.getSelectedLabel(getOrganizationId()));
    }


    @RequestMapping(value = "/getUnSelectedLabel", method = RequestMethod.GET)
    @ApiOperation(value = "获取未选择的标签", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<List<MarketingUnitcode>> getUnSelectedLabel() throws Exception {
        return new RestResult<List<MarketingUnitcode>>(200, "success", organizationPortraitService.getUnSelectedLabel(getOrganizationId()));
    }


}
