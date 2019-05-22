package com.jgw.supercodeplatform.marketing.controller.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityCreateParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketing/saler/activity/set")
@Api(tags = "导购活动设置管理")
public class MarketingSalerActivitySetController {

    // todo  复制  更新
    // todo 扫码领红包
    //
    // 停用 列表
    @Autowired
    private MarketingActivitySetService service;

    @Autowired
    private MarketingActivityProductService maProductService;
    /**
     * 活动创建
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动创建", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerAdd(@RequestBody MarketingSalerActivityCreateParam activitySetParam) throws Exception {
        return service.salerAdd(activitySetParam);
    }




    /**
     * 导购更新
     * 复制与编辑的区别在与,编辑是修改主表,复制是新增主表活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动更新", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerUpdate(@RequestBody MarketingSalerActivityCreateParam activitySetParam) throws Exception {
        //TODO 待码平台处理完毕
        return service.salerUpdate(activitySetParam);
    }



    /**
     * 导购复制
     * 复制与编辑的区别在与,编辑是修改主表,复制是新增主表活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动更新", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerCopy(@RequestBody MarketingSalerActivityCreateParam activitySetParam) throws Exception {
        //TODO 待码平台处理完毕
        return service.salerUpdate(activitySetParam);
    }





    /**
     * 导购更新
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动详情", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<MarketingSalerActivityCreateParam> detail(Long id) throws Exception {
        // todo
        return service.detail(id);
    }



}
