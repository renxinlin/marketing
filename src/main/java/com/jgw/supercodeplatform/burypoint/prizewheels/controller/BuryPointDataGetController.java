package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

import com.jgw.supercodeplatform.burypoint.prizewheels.service.get.BuryPointDataGetService;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author fangshiping
 * @date 2019/10/17 11:32
 */
@RestController
@RequestMapping("marketing/buryPoint/getPrizeWheelsData")
@Api(tags = "获取大转盘各类埋点数据")
public class BuryPointDataGetController extends SalerCommonController {
    private Logger logger = LoggerFactory.getLogger(BuryPointDataGetController.class);

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
    public RestResult<HashMap> getPvDay(){
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
    public RestResult<HashMap> getUvDay(){
        return success(buryPointDataGetService.getUvDay());
    }
}
