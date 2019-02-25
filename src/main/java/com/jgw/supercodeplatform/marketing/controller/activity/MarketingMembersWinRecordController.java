package com.jgw.supercodeplatform.marketing.controller.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingMembersWinRecordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/winRecord")
public class MarketingMembersWinRecordController {
    
	@Autowired
	private MarketingMembersWinRecordService service;
	
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "中奖纪录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<MarketingMembersWinRecord>>> list(@RequestBody MarketingMembersWinRecordListParam winRecordListParam) throws Exception {
    	RestResult<PageResults<List<MarketingMembersWinRecord>>> restResult=new RestResult<PageResults<List<MarketingMembersWinRecord>>>();
    	PageResults<List<MarketingMembersWinRecord>> pageResults=service.listSearchViewLike(winRecordListParam);
    	restResult.setState(200);
    	restResult.setResults(pageResults);
    	restResult.setMsg("成功");
    	return restResult;
    }
    
}
