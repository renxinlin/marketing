package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.application.service.ProductReturnIntegralApplication;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.ProductReturnIntegralDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;


import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.ProductReturnIntegralService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductReturnIntegral;

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
@RequestMapping("/marketing/mutiIntegral/productReturnIntegral")
@Api(value = "会员退货", tags = "会员退货")
public class ProductReturnIntegralController  extends SalerCommonController{

    @Autowired
    private ProductReturnIntegralApplication application;

    @PostMapping("/save")
    @ApiOperation(value = "积分减扣", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@Valid  @RequestBody ProductReturnIntegralDto productReturnIntegralDto)   {
        application.returnBack(productReturnIntegralDto);
        return success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "退货列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult< AbstractPageService.PageResults<List<ProductReturnIntegral>>> list(DaoSearch daoSearch){
        return success(application.returnBackList(daoSearch));
    }





}

