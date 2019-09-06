package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRuleExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@RestController
@RequestMapping("marketing/salerRuleExchange")
@Api(value = "", tags = "销售员积分兑换")
public class SalerRuleExchangeController extends SalerCommonController {

    @Autowired
    private SalerRuleExchangeService service;

    @PostMapping("/save")
    @ApiOperation(value = "新增兑换", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult save(@Valid @RequestBody SalerRuleExchangeDto salerRuleExchangeDto) throws CommonException {
        service.addExchange(salerRuleExchangeDto);
        return success();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新兑换", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@Valid @RequestBody SalerRuleExchangeDto salerRuleExchangeDto) throws CommonException {
        service.updateExchange(salerRuleExchangeDto);
        return success();
    }

    @GetMapping("/getById")
    @ApiOperation(value = "兑换详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult getById(@RequestParam("id") Long id) throws CommonException {
        SalerRuleExchange byId = service.getByIdWithOrg(id);
        return success(byId);
    }


    @GetMapping("/updateStatus")
    @ApiOperation(value = "兑换上下架:0上架1手动下架2自动下架", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult updateStatus(@RequestParam("status") Byte status,@RequestParam("id") Long id) throws CommonException {
        service.updateStatus(id,status);
        return success();
    }



    @GetMapping("/delete")
    @ApiOperation(value = "删除兑换", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleteById(@RequestParam("id") Long id) throws CommonException {
        service.deleteById(id);
        return success();
    }



    @GetMapping("/list")
    @ApiOperation(value = "兑换分页", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<SalerRuleExchange>>> list(DaoSearch search) throws CommonException {
        AbstractPageService.PageResults<List<SalerRuleExchange>> page = service.searchPage(search);
        return success(page);
    }


}

