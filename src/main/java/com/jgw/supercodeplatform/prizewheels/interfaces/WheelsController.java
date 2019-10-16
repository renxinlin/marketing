package com.jgw.supercodeplatform.prizewheels.interfaces;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.prizewheels.application.service.WheelsPublishAppication;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsRewardDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("marketing/prizeWheels")
@Api(value = "", tags = "大转盘")
public class WheelsController extends SalerCommonController {


    @Autowired
    private WheelsPublishAppication appication;


    @PostMapping("/add")
    @ApiOperation(value = "添加", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@Valid  @RequestBody WheelsDto wheelsDto)   {
        appication.publish(wheelsDto);
        return success();
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@Valid  @RequestBody WheelsUpdateDto wheelsDto)   {
        appication.update(wheelsDto);
        return success();
    }


    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<WheelsDetailsVo> detail(@RequestParam("id") Long id)   {
        WheelsDetailsVo wheelsDetailsVo = appication.getWheelsDetails(id);
        return success(wheelsDetailsVo);
    }


    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "分页参见以前接口")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delete(@RequestParam("id") Long id)   {
        appication.deletePrizeWheelsById(id);
        return success();
    }



    @PostMapping("/changeStatus")
    @ApiOperation(value = "活动停用启用", notes = "活动状态(1、表示上架进展，0 表示下架)")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult status(@RequestParam("id") String activityStatus)   {

        appication.upadteStatus(activityStatus);
        return success();
    }







}
