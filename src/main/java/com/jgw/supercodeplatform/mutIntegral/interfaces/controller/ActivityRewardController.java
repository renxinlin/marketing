package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;


import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.ActivityRewardService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ActivityReward;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/marketing/mutiIntegral/activityReward")
@Api(value = "活动关联奖励设置", tags = "活动关联奖励设置")
public class ActivityRewardController  extends SalerCommonController{

    @Autowired
    private ActivityRewardService service;

    @PostMapping("/save")
    @ApiOperation(value = "活动关联积分设置", notes = "活动关联积分设置")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@RequestBody ActivityReward obj)   {
        return success();
    }






}

