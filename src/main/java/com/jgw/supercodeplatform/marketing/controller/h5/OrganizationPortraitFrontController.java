package com.jgw.supercodeplatform.marketing.controller.h5;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.service.user.OrganizationPortraitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/portrait")
@Api(tags = "会员画像管理")
public class OrganizationPortraitFrontController  extends CommonUtil {

    @Autowired
    private OrganizationPortraitService organizationPortraitService;

    @RequestMapping(value = "/getSelectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织已选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<List<MarketingOrganizationPortraitListParam>> getSelectedPortrait(String organizationId) throws Exception {
        return new RestResult<List<MarketingOrganizationPortraitListParam>>(200, "success", organizationPortraitService.getSelectedPortrait(organizationId));
    }

    @RequestMapping(value = "/getUnselectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织未选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getUnselectedPortrait(String organizationId) throws Exception {
        return new RestResult(200, "success", organizationPortraitService.getUnselectedPortrait(organizationId));
    }


}
