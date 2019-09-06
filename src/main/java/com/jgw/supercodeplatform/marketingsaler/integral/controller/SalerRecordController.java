package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("marketing/salerRecord")
@Api(value = "", tags = "销售员积分记录")
public class SalerRecordController extends SalerCommonController {

    @Autowired
    private SalerRecordService service;


    @GetMapping("/list")
    @ApiOperation(value = "后台积分记录分页", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult< AbstractPageService.PageResults<List<SalerRecord>>> list(DaoSearch daoSearch) throws CommonException {
        return success(service.selectPage(daoSearch));
    }


}

