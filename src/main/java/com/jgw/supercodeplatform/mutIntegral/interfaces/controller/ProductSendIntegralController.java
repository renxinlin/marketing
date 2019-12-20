package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.application.service.ProductSendIntegralApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.ProductSendIntegral;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.ProductSendIntegralDto;
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
@RequestMapping("/marketing/mutiIntegral/productSendIntegral")
@Api(value = "积分派送", tags = "积分派送")
public class ProductSendIntegralController  extends SalerCommonController{

    @Autowired
    private ProductSendIntegralApplication application;

    @PostMapping("/sendIntegral")
    @ApiOperation(value = "派送", notes = "派送")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult productSendIntegral(@Valid @RequestBody ProductSendIntegralDto sendIntegralDto)   {
        application.productSendIntegral(sendIntegralDto);
        return success();
    }




    @GetMapping("/list")
    @ApiOperation(value = "派送列表", notes = "派送列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<ProductSendIntegral>>> sendRecordList(DaoSearch daoSearch)   {
        return success(application.sendRecordList(daoSearch));
    }




}

