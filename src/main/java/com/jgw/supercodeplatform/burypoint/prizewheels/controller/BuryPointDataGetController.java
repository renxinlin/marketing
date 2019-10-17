package com.jgw.supercodeplatform.burypoint.prizewheels.controller;

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

/**
 * @author fangshiping
 * @date 2019/10/17 11:32
 */
@RestController
@RequestMapping("marketing/prizeWheels/buryPointData")
@Api(tags = "获取大转盘各类埋点数据")
public class BuryPointDataGetController extends SalerCommonController {
    private Logger logger = LoggerFactory.getLogger(BuryPointDataGetController.class);
    @Autowired
    private RedisUtil redisUtil;

    //外链埋点统一前缀
    private String buryPointOuterChain= "strom:marketing:outUrl:";

    @GetMapping(value = "/getOuterChainNum")
    @ApiOperation(value = "获取B端配置外链埋点数据")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult buryPointOuterChainTc(@RequestParam(value = "key") String key){
        String result=redisUtil.get(buryPointOuterChain+key);
        logger.info("查询B端配置外链埋点数据----"+result);
        return success(result);
    }
}
