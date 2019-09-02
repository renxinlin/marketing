package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRuleExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/salerRuleExchange")
@Api(value = "", tags = "销售员积分兑换")
public class SalerRuleExchangeController extends SalerCommonController {

    @Autowired
    private SalerRuleExchangeService service;

    @PostMapping("/save")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@RequestBody SalerRuleExchange obj) throws CommonException {
        return success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@RequestBody SalerRuleExchange obj) throws CommonException {
        return success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public RestResult getById(@PathVariable("id") String id) throws CommonException {
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult list(SalerRuleExchange obj) throws CommonException {
        return null;
    }


}

