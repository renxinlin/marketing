package com.jgw.supercodeplatform.marketing.controller.activity;


import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateNewParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusBatchUpdateParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySalerSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingSalerActivitySetMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.MarketingSalerActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/saler/activity/set")
@Api(tags = "导购活动设置管理")
public class MarketingSalerActivitySetController extends CommonUtil {
    private static Logger logger = LoggerFactory.getLogger(MarketingSalerActivitySetController.class);

    @Autowired
    private MarketingActivitySetService service;

    @Autowired
    private MarketingActivitySalerSetService marketingActivitySalerSetService;

    /**
     * 获取销售活动列表
     * @param organizationIdParam
     * @return
     * @throws Exception
     */
    @GetMapping("/page")
    @ApiOperation("获取销售活动列表")
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
    @PostMapping("/enableOrDisable")
    @ApiOperation(value="启用或禁用活动", notes = "启用或禁用活动")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> enableOrDisable(@RequestBody MarketingActivitySetStatusBatchUpdateParam batchUpdateParam) throws SuperCodeException {
        RestResult<String> restResult = new RestResult<>();
        if (CollectionUtils.isEmpty(batchUpdateParam.getActivitySetIds())) {
            restResult.setState(500);
            restResult.setMsg("请至少选择一个活动");
            return restResult;
        }
        return service.updateSalerActivitySetStatus(batchUpdateParam);
    }




    /**
     * 活动创建:
     * 注意:线程使用时,一定要注意事务的回滚,提交,死锁释放问题
     * 本期已经测试完:回滚正常,提交正常
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ApiOperation("导购活动创建")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerAdd(@RequestBody MarketingSalerActivityCreateNewParam activitySetParam) throws SuperCodeException, BrokenBarrierException, InterruptedException {
        // TODO 产品覆盖
        logger.error("导购活动add活动产品参数{}",JSONObject.toJSONString(activitySetParam));
        logger.error("导购活动add产品参数{}",JSONObject.toJSONString(activitySetParam));
        long startTime = System.currentTimeMillis();
        RestResult<String> stringRestResult = marketingActivitySalerSetService.salerAdd(activitySetParam);
        logger.error("响应时间{}",(System.currentTimeMillis()-startTime));
        return stringRestResult;

    }

    /**
     * 导购更新
     * 复制与编辑的区别在与,编辑是修改主表,复制是新增主表活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @ApiOperation("导购活动更新,需要携带产品productId,删除原来的信息")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerUpdate(@RequestBody MarketingSalerActivityUpdateParam activitySetParam) throws SuperCodeException, BrokenBarrierException, InterruptedException {
        logger.error("导购活动更新产品参数{}",JSONObject.toJSONString(activitySetParam));
        long startTime = System.currentTimeMillis();
        RestResult<String> stringRestResult = marketingActivitySalerSetService.salerUpdate(activitySetParam);
        logger.error("响应时间{}",(System.currentTimeMillis()-startTime));
        return stringRestResult;
    }

    /**
     * 导购复制
     * 复制与编辑的区别在与,编辑是修改主表,复制是新增主表活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @PostMapping("/copy")
    @ApiOperation("导购活动复制")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> salerCopy(@RequestBody MarketingSalerActivityUpdateParam activitySetParam) throws SuperCodeException, BrokenBarrierException, InterruptedException {
        logger.error("导购活动copy产品参数{}",JSONObject.toJSONString(activitySetParam));
        return marketingActivitySalerSetService.salerCopy(activitySetParam);
    }

    /**
     * 活动编辑
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @GetMapping("/activityInfo")
    @ApiOperation("获取活动数据")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true),
    @ApiImplicitParam(name="activitySetId",paramType="query",value="活动设置ID",required=true)})
    public RestResult<MarketingSalerActivityCreateParam> activityInfo(@RequestParam Long activitySetId) throws Exception {
    	MarketingSalerActivityCreateParam marketingSalerActivityCreateParam = service.activityInfo(activitySetId);
    	return new RestResult<>(200, "查询成功", marketingSalerActivityCreateParam);
    }


}
