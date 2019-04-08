package com.jgw.supercodeplatform.marketing.controller.integral;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/integral/rule")
@Api(tags = "通用积分规则")
public class IntegralRuleController {


    // 获取产品价格，基础信息
    // 获取基础信息产品列表：调用基础信息接口


    /**
     *  获取积分规则
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ApiOperation(value = "积分规则： 获取积分规则信息", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token") })
    public RestResult<IntegralRule> getActivityPrizeInfoByeditPage(){
        RestResult<IntegralRule> result = new  RestResult<IntegralRule>();
        return  result;

    }

    @RequestMapping(value = "/setted-product/page",method = RequestMethod.POST)
    @ApiOperation(value = "获取已设置产品列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>> listUnsale(@RequestBody IntegralRuleProduct integralRuleProduct) throws Exception {
        RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>>();
        return restResult;
    }




    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑通用积分规则", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult edit(@RequestBody IntegralRule integralRule) throws Exception {
        RestResult  restResult=new RestResult();
        return restResult;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增通用积分规则产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult addProduct(@RequestBody IntegralRuleProduct integralRuleProduct) throws Exception {
        RestResult  restResult=new RestResult();
        return restResult;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新通用积分规则产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult updateProduct(@RequestBody IntegralRuleProduct integralRuleProduct) throws Exception {
        RestResult  restResult=new RestResult();
        return restResult;
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "清空规则设置产品", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult deleteProduct() throws Exception {
        // TODO 传入积分ID和积分产品ids
        RestResult  restResult=new RestResult();
        return restResult;
    }



}
