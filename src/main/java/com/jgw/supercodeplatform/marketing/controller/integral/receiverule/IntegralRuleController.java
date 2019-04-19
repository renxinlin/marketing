package com.jgw.supercodeplatform.marketing.controller.integral.receiverule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.service.integral.IntegralRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 积分记录controller
 *
 */
@RestController
@RequestMapping("/marketing/integral/rule")
@Api(tags = "通用积分规则设置")
public class IntegralRuleController {

  @Autowired
  private IntegralRuleService service;
  

  /**
   *  获取积分规则
   * @param
   * @return
   * @throws SuperCodeException 
   * @throws Exception
   */
  @RequestMapping(value = "/get",method = RequestMethod.GET)
  @ApiOperation(value = "积分规则： 获取积分规则信息", notes = "")
  @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token") })
  public RestResult<IntegralRule> get() throws SuperCodeException{
      RestResult<IntegralRule> result = new  RestResult<IntegralRule>();
      IntegralRule rule=service.getCurrOrgRule();
      result.setResults(rule);
      result.setState(200);
      return  result;
  }



    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑通用积分规则", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> edit(@RequestBody IntegralRule integralRule) throws Exception {
    	RestResult<String>  restResult=new RestResult<String>();
    	service.edit(integralRule);
    	restResult.setState(200);
        return restResult;
    }


}
