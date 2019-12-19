package com.jgw.supercodeplatform.mutIntegral.interfaces.controller;



import com.jgw.supercodeplatform.marketing.common.model.RestResult;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.mutIntegral.application.service.MutiIntegralRecordApplication;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.MutiIntegralRecordService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;


import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;

import java.util.List;

/**
 * <p>
 * 积分领取记录表 前端控制器
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@RestController
@RequestMapping("/marketing/mutiIntegral/integralRecord")
@Api(value = "积分记录", tags = "积分记录")
public class MutiIntegralRecordController  extends SalerCommonController{

    @Autowired
    private MutiIntegralRecordApplication application;

    @GetMapping("/list")
    @ApiOperation(value = "积分记录列表", notes = "积分记录列表")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralRecord>>> list(DaoSearch daoSearch)   {
        return success(application.getMemberMutiIntegralRecordPage(daoSearch));
    }





}

