package com.jgw.supercodeplatform.marketing.controller.h5;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.members.MarketingOrganizationPortraitListParam;
import com.jgw.supercodeplatform.marketing.service.user.OrganizationPortraitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/portrait")
@Api(tags = "企业画像获取")
public class OrganizationPortraitFrontController  extends CommonUtil {

    @Autowired
    private OrganizationPortraitService organizationPortraitService;

    @RequestMapping(value = "/getSelectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织已选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "组织id", required = true)
    })
    public RestResult<List<MarketingOrganizationPortraitListParam>> getSelectedPortrait(String organizationId) throws Exception {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("组织id不存在", 500);
		}
		
        return new RestResult<List<MarketingOrganizationPortraitListParam>>(200, "success", organizationPortraitService.getSelectedPortrait(organizationId));
    }

    
    @RequestMapping(value = "/getUnselectedPor", method = RequestMethod.GET)
    @ApiOperation(value = "获取组织未选的画像编码", notes = "返回编码信息列表")
    @ApiImplicitParams({
    	   @ApiImplicitParam(name = "organizationId", paramType = "query", defaultValue = "64b379cd47c843458378f479a115c322", value = "扫码唯一id", required = true)
    })
    public RestResult<String> getUnselectedPortrait(String organizationId) throws Exception {
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("组织id不存在", 500);
		}
        return new RestResult(200, "success", organizationPortraitService.getUnselectedPortrait(organizationId));
    }


}
