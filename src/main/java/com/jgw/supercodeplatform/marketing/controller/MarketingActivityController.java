package com.jgw.supercodeplatform.marketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.base.MarketingActivityParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/activity")
public class MarketingActivityController {

	@Autowired
	private MarketingActivityService service;
    /**
     * 活动创建
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ApiOperation(value = "活动创建", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> create(@RequestBody MarketingActivityParam marketingActivityParam) throws Exception {
    	RestResult<String> restResult=new RestResult<String>(200, "成功", null);
    	return restResult;
    }
    
    /**
     * 活动列表
     * @param marketingActivityListParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "活动列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> list(@RequestBody MarketingActivityListParam marketingActivityListParam) throws Exception {
    	RestResult<String> restResult=new RestResult<String>(200, "成功", null);
    	return restResult;
    }
    
    /**
     * 活动创建
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/disOrEnable",method = RequestMethod.GET)
    @ApiOperation(value = "停用或启用", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),@ApiImplicitParam(paramType="query",value = "活动状态1启用，0停用",name="activityStatus")})
    public RestResult<String> disOrEnable(@RequestParam(required=true) Integer activityStatus) throws Exception {
    	RestResult<String> restResult=new RestResult<String>(200, "成功", null);
    	return restResult;
    }
    
    /**
     * 活动创建
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPageInfo",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id获取领取页和中奖页数据", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),@ApiImplicitParam(paramType="query",value = "活动主键id",name="activityId")})
    public RestResult<String> getPageInfo(@RequestParam(required=true) Integer activityId) throws Exception {
    	RestResult<String> restResult=new RestResult<String>(200, "成功", null);
    	return restResult;
    }
}
