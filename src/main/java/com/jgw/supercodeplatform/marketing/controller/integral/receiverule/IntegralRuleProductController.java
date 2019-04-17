package com.jgw.supercodeplatform.marketing.controller.integral.receiverule;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/integral/ruleProduct")
@Api(tags = "产品积分领取设置")
public class IntegralRuleProductController {

  @Autowired
  private IntegralRuleProductService service;
  
  @RequestMapping(value = "/page",method = RequestMethod.GET)
  @ApiOperation(value = "已选的产品积分记录列表", notes = "")
  @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
  public RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>> list(DaoSearch daoSearch) throws Exception {
      RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>>();
      PageResults<List<IntegralRuleProduct>> pageResults=service.listSearchViewLike(daoSearch);
      restResult.setState(200);
      if(!CollectionUtils.isEmpty(pageResults.getList())){
          restResult.setResults(pageResults);
      }
      return restResult;
  }
  
  @RequestMapping(value = "/unSelectPage",method = RequestMethod.GET)
  @ApiOperation(value = "未选的产品积分记录列表", notes = "")
  @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
  public JSONObject unSelectPage(DaoSearch daoSearch) throws Exception {
      JSONObject jsonObject=service.unSelectPage(daoSearch);
      return jsonObject;
  }

  @RequestMapping(value = "/emptyRule",method = RequestMethod.POST)
  @ApiOperation(value = "批量或单个清空规则设置产品", notes = "")
  @ApiImplicitParams(value= {
          @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
          @ApiImplicitParam(name = "productIds", paramType = "query", defaultValue = "1,2", value = "产品id集合", required = true)
  })
  public RestResult<String> emptyRule(@RequestParam List<String> productIds) throws Exception {
      RestResult<String>  restResult=new RestResult<String>();
      service.deleteByProductIds(productIds);
      restResult.setState(200);
      return restResult;
  }

  @RequestMapping(value = "/batchSetRule",method = RequestMethod.POST)
  @ApiOperation(value = "批量设置产品规则", notes = "")
  @ApiImplicitParams(value= {
          @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
  })
  public RestResult<String> batchSetRule(@Valid @RequestBody BatchSetProductRuleParam bProductRuleParam) throws Exception {
      RestResult<String>  restResult=new RestResult<String>();
      service.batchSetRule(bProductRuleParam);
      restResult.setState(200);
      return restResult;
  }
  
  @RequestMapping(value = "/singleSetRule",method = RequestMethod.POST)
  @ApiOperation(value = "单个设置产品规则", notes = "")
  @ApiImplicitParams(value= {
          @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
  })
  public RestResult<String> singleSetRule(@Valid @RequestBody IntegralRuleProduct inRuleProduct) throws Exception {
      RestResult<String>  restResult=new RestResult<String>();
      service.singleSetRuleProduct(inRuleProduct);
      restResult.setState(200);
      return restResult;
  }
  
  
  
  
}
