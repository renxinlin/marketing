package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

import com.jgw.supercodeplatform.burypoint.prizewheels.service.get.BuryPointDataGetService;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangshiping
 * @date 2019/10/17 11:32
 */
@RestController
@RequestMapping("marketing/buryPoint/getPrizeWheelsData")
@Api(tags = "获取大转盘各类埋点数据")
@Slf4j
public class BuryPointDataGetController extends SalerCommonController {

    @Autowired
    private BuryPointDataGetService buryPointDataGetService;

    @GetMapping(value = "/getPvAll")
    @ApiOperation(value = "获取总Pv")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getPvAll(){
        return success(buryPointDataGetService.getPvAll());
    }

    @GetMapping(value = "/getPvDay")
    @ApiOperation(value = "获取每天Pv")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getPvDay(){
        return success(buryPointDataGetService.getPvDay());
    }

    @GetMapping(value = "/getUvAll")
    @ApiOperation(value = "获取总Uv")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getUvAll(){
        return success(buryPointDataGetService.getUvAll());
    }

    @GetMapping(value = "/getUvDay")
    @ApiOperation(value = "获取每天Uv")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getUvDay(){
        return success(buryPointDataGetService.getUvDay());
    }


    @GetMapping(value = "/getOutChainConfigAll")
    @ApiOperation(value = "获取B端配置外链总数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getOutChainConfigAll(){
        return success(buryPointDataGetService.getOuterChainConfigAll());
    }

    @GetMapping(value = "/getOutChainClickAll")
    @ApiOperation(value = "获取C端用户点击外链总数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getOutChainClickAll(){
        return success(buryPointDataGetService.getOuterChainClickAll());
    }

    @GetMapping(value = "/getOutChainClickDay")
    @ApiOperation(value = "获取C端用户点击外链每天总数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getOutChainClickDay(){
        return success(buryPointDataGetService.getOuterChainClickDay());
    }

    @GetMapping(value = "/getRewardAll")
    @ApiOperation(value = "获取中奖总人数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getRewardAll(){
        return success(buryPointDataGetService.getRewardOutAll());
    }

    @GetMapping(value = "/getRewardOutDay")
    @ApiOperation(value = "获取每天各个奖项中奖总人数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getRewardOutDay(){
        return success(buryPointDataGetService.getRewardOutDay());
    }

    @GetMapping(value = "/getRewardOutRewardIdDay")
    @ApiOperation(value = "获取各个奖项中奖总人数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getRewardOutRewardIdDay(){
        return success(buryPointDataGetService.getRewardOutRewardIdDay());
    }

    @GetMapping(value = "/getTemplateScanAll")
    @ApiOperation(value = "C端用户扫模板总数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getTemplateScanAll(){
        return success(buryPointDataGetService.getTemplateScanAll());
    }

    @GetMapping(value = "/getTemplateScanDay")
    @ApiOperation(value = "C端用户扫模板总数每天")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getTemplateScanDay(){
        return success(buryPointDataGetService.getTemplateScanDay());
    }

    @GetMapping(value = "/getTemplateScanHour")
    @ApiOperation(value = "C端用户扫模板总数每小时")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getTemplateScanHour(){
        return success(buryPointDataGetService.getTemplateScanHour());
    }

    @GetMapping(value = "/getWheelsClickAll")
    @ApiOperation(value = "C端用户点击大转盘总次数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getWheelsClickAll(){
        return success(buryPointDataGetService.getWheelsClickAll());
    }

    @GetMapping(value = "/getWheelsClickDay")
    @ApiOperation(value = "C端用户点击大转盘总次数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getWheelsClickDay(){
        return success(buryPointDataGetService.getWheelsClickDay());
    }

    @GetMapping(value = "/getWxConfigAll")
    @ApiOperation(value = "B端配置微信总次数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getWxConfigAll(){
        return success(buryPointDataGetService.getWxConfigAll());
    }

    @GetMapping(value = "/getWxFollowAll")
    @ApiOperation(value = "C端微信关注注总次数")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> getWxFollowAll(){
        return success(buryPointDataGetService.getWxFollowAll());
    }

    @GetMapping(value = "/getWxFollowDay")
    @ApiOperation(value = "C端微信关注每天")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<Map> getWxFollowDay(){
        return success(buryPointDataGetService.getWxFollowDay());
    }
}
