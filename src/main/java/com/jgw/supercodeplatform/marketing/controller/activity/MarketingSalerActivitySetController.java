package com.jgw.supercodeplatform.marketing.controller.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingSalerActivitySetMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/saler/activity/set")
@Api(tags = "导购活动设置管理")
public class MarketingSalerActivitySetController extends CommonUtil {

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
     * 获取销售活动列表
     * @param organizationIdParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value = "获取销售活动列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<MarketingSalerActivitySetMO>>> list(DaoSearchWithOrganizationIdParam organizationIdParam) throws Exception {
        RestResult<PageResults<List<MarketingSalerActivitySetMO>>> restResult = new RestResult<>();
        organizationIdParam.setOrganizationId(getOrganizationId());
        PageResults<List<MarketingSalerActivitySetMO>> pageResults = service.listSearchViewLike(organizationIdParam);
        restResult.setState(200);
        restResult.setResults(pageResults);
        restResult.setMsg("成功");
        return restResult;
    }

    /**
     * 启用或禁用活动
     * @return
     */

    @RequestMapping(value = "/enableOrDisable", method = RequestMethod.PUT)
    @ApiOperation(value = "启用或禁用活动", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> enable(@RequestBody MarketingActivitySetStatusUpdateParam setStatusUpdateParam) {
        return service.updateSalerActivitySetStatus(setStatusUpdateParam);
    }





    /**
     * 导购更新
     * 复制与编辑的区别在与,编辑是修改主表,复制是新增主表活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动更新,需要携带产品productId,删除原来的信息", notes = "")
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
    @RequestMapping(value = "/copy",method = RequestMethod.POST)
    @ApiOperation(value = "导购活动复制", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerCopy(@RequestBody MarketingSalerActivityCreateParam activitySetParam) throws Exception {
        //TODO 待码平台处理完毕
        return service.salerCopy(activitySetParam);
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

    
    
    /**
     * 活动编辑;本期不做
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @GetMapping("/activityInfo")
    @ApiOperation("获取活动数据")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true),
    @ApiImplicitParam(name="activitySetId",paramType="query",value="活动设置ID",required=true)})
    public RestResult<MarketingActivityCreateParam> activityInfo(@RequestParam Long activitySetId) throws Exception {
    	MarketingActivityCreateParam marketingActivityCreateParam = service.activityInfo(activitySetId);
    	return new RestResult<>(200, "查询成功", marketingActivityCreateParam);
    }


}
