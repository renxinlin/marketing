package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.mutIntegral.application.service.IntegralRuleApplication;
import com.jgw.supercodeplatform.mutIntegral.application.service.IntegralRuleCodeFacadeApplication;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralRewardSettingAggDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;



import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/marketing/mutiIntegral/integralRule")
@Api(value = "积分通用规则", tags = "积分通用规则")
public class MutiIntegralRuleController  extends SalerCommonController{

    @Autowired
    private IntegralRuleApplication application;


    @Autowired
    private IntegralRuleCodeFacadeApplication ruleCodeFacadeApplication;

    @PostMapping("/save")
    @ApiOperation(value = "积分通用规则配置", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@Valid  @RequestBody IntegralRuleDto integralRuleDto)   {
        application.commonIntegralRewardSetting(integralRuleDto);
        return success();
    }


    @GetMapping("/detail")
    @ApiOperation(value = "积分通用规则配置查看", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<IntegralRuleDto> detail()   {
        return success(application.commonIntegralRewardDetail());
    }


    @GetMapping("/commonList")
    @ApiOperation(value = "积分通用规则通用列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<List<IntegralRuleRewardCommonVo>> commonList()   {
        return success(application.commonIntegralRewardList());
    }



    @GetMapping("/deleteById")
    @ApiOperation(value = "积分通用规则列表项删除", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleteById(@RequestParam Integer id)   {
        application.deleteById(id);
        return success();
    }

    @GetMapping("/getSettingInfo")
    @ApiOperation(value = "获取积分产品,,,单码配置信息", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<IntegralRewardSettingAggDto> getSettingInfo()   {
        IntegralRewardSettingAggDto integralRewardSettingAgg = ruleCodeFacadeApplication.getIntegralRewardSettingAgg();
        return success(integralRewardSettingAgg);
    }


    @PostMapping("/setSettingInfo")
    @ApiOperation(value = "获取积分产品,,,单码配置信息", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult setSettingInfo(@Valid  @RequestBody  IntegralRewardSettingAggDto integralRewardSettingAggDto)   {
        ruleCodeFacadeApplication.setSettingInfo(integralRewardSettingAggDto);
        return success();
    }









}

