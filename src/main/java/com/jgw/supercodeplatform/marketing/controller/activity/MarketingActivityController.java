package com.jgw.supercodeplatform.marketing.controller.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingActivityListMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/activity")
@Api(tags = "活动列表管理")
public class MarketingActivityController extends CommonUtil {
	@Autowired
	private MarketingActivityService service;
	
    /**
     *      活动列表
     * @param marketingActivityListParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "活动列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<MarketingActivityListMO>>> list(@RequestBody MarketingActivityListParam marketingActivityListParam) throws Exception {
    	RestResult<PageResults<List<MarketingActivityListMO>>> restResult=new RestResult<PageResults<List<MarketingActivityListMO>>>();
    	marketingActivityListParam.setOrganizationId(getOrganizationId());
    	PageResults<List<MarketingActivityListMO>> pageResults=service.listSearchViewLike(marketingActivityListParam);
    	restResult.setState(200);
    	restResult.setResults(pageResults);
    	restResult.setMsg("成功");
    	return restResult;
    }
    
    /**
     *  获取所有活动
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectAll",method = RequestMethod.GET)
    @ApiOperation(value = "获取所有活动", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<List<MarketingActivity>> selectAll() throws Exception {
    	return service.selectAll();
    } 
    
}
