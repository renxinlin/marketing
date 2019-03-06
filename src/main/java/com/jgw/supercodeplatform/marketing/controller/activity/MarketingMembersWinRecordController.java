package com.jgw.supercodeplatform.marketing.controller.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingMembersWinRecordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/marketing/winRecord")
@Api(tags = "中奖管理")
public class MarketingMembersWinRecordController extends CommonUtil {
    
	@Autowired
	private MarketingMembersWinRecordService service;
	
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation(value = "中奖纪录列表", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>> list(@RequestBody MarketingMembersWinRecordListParam winRecordListParam) throws Exception {
    	RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>> restResult=new RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>>();
    	PageResults<List<MarketingMembersWinRecordListReturn>> pageResults=service.listSearchViewLike(winRecordListParam);
    	restResult.setState(200);
    	restResult.setResults(pageResults);
    	restResult.setMsg("成功");
    	return restResult;
    }

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ApiOperation(value = "添加中奖记录", notes = "")
	@ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
	public RestResult<String> create(@RequestBody MarketingMembersWinRecord winRecord) throws Exception {
		service.add(winRecord);
		return new RestResult<String>(200, "success",null );
	}


	@GetMapping("/little-record")
	public void littleWinRecordOutExcel(@RequestParam LinkedHashMap filedMap, HttpServletResponse response) throws SuperCodeException, UnsupportedEncodingException {
		validateRequestParamAndValueNotNull(filedMap,"list");
		List<String> ids = Arrays.asList(filedMap.get("list").toString().split(","));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String fileName = simpleDateFormat.format(new Date()) + ".xls";
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.addHeader("Content-disposition", "attachment;filename=" + fileName);
		response.setContentType("txt/plain");
		service.littleGeneralWinRecordExcelOutToResponse(ids,response,filedMap);
	}


    
}
