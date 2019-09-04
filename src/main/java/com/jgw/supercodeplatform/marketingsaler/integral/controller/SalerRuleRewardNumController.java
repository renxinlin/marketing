package com.jgw.supercodeplatform.marketingsaler.integral.controller;




import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRuleRewardNumService;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleRewardNum;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/b2b//salerRuleRewardNum")
    @Api(value = "", tags = "")
    public class SalerRuleRewardNumController  extends SalerCommonController{
// 可在模版中添加相应的controller通用方法，编辑模版在resources/templates/controller.java.vm文件中

        @Autowired
        private SalerRuleRewardNumService service;

        @PostMapping("/save")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult save(@RequestBody SalerRuleRewardNum obj) throws CommonException {
                return RestResult.success();
        }

        @PostMapping("/update")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult update(@RequestBody SalerRuleRewardNum obj) throws CommonException {
            return RestResult.success();
        }

        @GetMapping("/{id}")
        @ApiOperation(value = "", notes = "")
        public RestResult getById(@PathVariable("id") String id) throws CommonException {
            return null;
        }

        @GetMapping("/list")
        @ApiOperation(value = "", notes = "")
        @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
        public RestResult list(SalerRuleRewardNum obj) throws CommonException {
            return null;
        }





}

