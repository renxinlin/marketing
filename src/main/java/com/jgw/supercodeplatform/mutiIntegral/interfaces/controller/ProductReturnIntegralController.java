package com.jgw.supercodeplatform.mutiIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;


import com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.batchdao.ProductReturnIntegralService;
import com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/marketing/mutiIntegral/productReturnIntegral")
@Api(value = "", tags = "")
public class ProductReturnIntegralController  extends SalerCommonController{
        // 可在模版中添加相应的controller通用方法，编辑模版在resources/templates/controller.java.vm文件中

    @Autowired
    private ProductReturnIntegralService service;

    @PostMapping("/save")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@RequestBody ProductReturnIntegral obj)   {
        return success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@RequestBody ProductReturnIntegral obj)   {
        return success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "", notes = "")
    public RestResult getById(@PathVariable("id") String id)   {
        service.getById(id);
        return null;
    }

    @GetMapping("/list")
    @ApiOperation(value = "", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult list(ProductReturnIntegral obj)   {
        return null;
    }





}

