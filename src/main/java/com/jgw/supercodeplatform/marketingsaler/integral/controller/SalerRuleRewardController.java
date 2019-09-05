package com.jgw.supercodeplatform.marketingsaler.integral.controller;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketingsaler.base.controller.SalerCommonController;
import com.jgw.supercodeplatform.marketingsaler.base.exception.CommonException;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.BatchSalerRuleRewardDto;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.service.SalerRuleRewardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("marketing/salerRuleReward")
@Api(value = "", tags = "销售员积分领取")
public class SalerRuleRewardController extends SalerCommonController {

     @Autowired
    private SalerRuleRewardService service;

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ApiOperation(value = "已选的产品积分记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>> list(DaoSearch daoSearch) throws Exception {
        RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>> restResult=new RestResult<>();
        AbstractPageService.PageResults<List<SalerRuleReward>> pageResults=service.listSearchViewLike(daoSearch);
        restResult.setState(200);
        restResult.setResults(pageResults);
        return restResult;
    }

    @RequestMapping(value = "/unSelectPage",method = RequestMethod.GET)
    @ApiOperation(value = "未选的产品积分记录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<SalerRuleReward>>> unSelectPage(DaoSearch daoSearch) throws Exception {
        return service.unSelectPage(daoSearch);
    }

    @RequestMapping(value = "/emptyRule",method = RequestMethod.POST)
    @ApiOperation(value = "批量或单个清空规则设置产品", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
            @ApiImplicitParam(name = "productIds", paramType = "query", defaultValue = "1,2", value = "产品id集合", required = true)
    })
    public RestResult<String> emptyRule(@RequestParam List<String> productIds) throws Exception {
        service.deleteByProductIds(productIds);
        return success();
    }

    @RequestMapping(value = "/batchSetRule",method = RequestMethod.POST)
    @ApiOperation(value = "批量设置产品规则", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> batchSetRule(@Valid @RequestBody BatchSalerRuleRewardDto bProductRuleParam) throws Exception {
        service.batchSetSalerRule(bProductRuleParam);
        return success();
    }

    @RequestMapping(value = "/singleSetRule",method = RequestMethod.POST)
    @ApiOperation(value = "单个设置已设置过产品规则的产品:已经设置的重新设置需要携带id", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> singleSetRule(@Valid @RequestBody SalerRuleReward salerRuleReward) throws Exception {
        service.singleSetRuleProduct(salerRuleReward);
        return success();
    }


    @GetMapping(value = "/openIntegralStatus")
    @ApiOperation(value = "开启关闭页面领积分按钮 1开启2 关闭", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> openIntegralStatus(String status) throws Exception {
        Asserts.check(!StringUtils.isEmpty(status)
                && (OpenIntegralStatus.close.equals(status)  || OpenIntegralStatus.open.equals(status))  ,"状态不合法");
        redisUtil.set(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON+commonUtil.getOrganizationId(),status);
        return success();
    }




    @GetMapping(value = "/getIntegralStatus")
    @ApiOperation(value = "查看开启页面领积分按钮", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getIntegralStatus() throws Exception {
        String status = redisUtil.get(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON + commonUtil.getOrganizationId());
        if( StringUtils.isEmpty(status)){
            // 默认状态
            return success(OpenIntegralStatus.open);
        }else {
            return success(status);

        }

    }




}

