package com.jgw.supercodeplatform.prizewheels.interfaces;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.prizewheels.application.service.WheelsPublishAppication;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
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
    @ApiOperation(value = "添加", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult add(@Valid  @RequestBody WheelsDto wheelsDto)   {
        appication.publish(wheelsDto);
        return success();
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult update(@Valid  @RequestBody WheelsDto wheelsDto)   {
        // appication.publish(wheelsDto);
        return success();
    }



    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<WheelsDto> detail(@RequestParam("id") Integer id)   {
        return success();
    }


    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult delete(@RequestParam("id") Integer id)   {
        return success();
    }

    @GetMapping("/add")
    @ApiOperation(value = "分页", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<WheelsDto>>> list(DaoSearch daoSearch)   {
        return success();
    }

}
