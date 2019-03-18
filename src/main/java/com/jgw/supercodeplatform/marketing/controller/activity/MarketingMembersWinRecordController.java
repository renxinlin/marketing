package com.jgw.supercodeplatform.marketing.controller.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingMembersWinRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/winRecord")
@Api(tags = "中奖管理")
public class MarketingMembersWinRecordController extends CommonUtil {
	protected static Logger logger = LoggerFactory.getLogger(MarketingMembersWinRecordController.class);

	@Autowired
	private MarketingMembersWinRecordService service;



	// 	@Value("${marketing.winRecord.sheetHead}")
	@Value("{\"activityName\":\"奖品类型\",\"userName\":\"会员姓名\",\"wxName\":\"会员微信昵称\", \"openId\":\"会员微信ID\",\"mobile\":\"会员手机\",\"prizeTypeId\":\"中奖奖次\", \"winningAmount\":\"中奖金额\",\"winningCode\":\"中奖码\",\"productName\":\"中奖产品\"}")
	private String MARKET_WIN_RECORD_EXCEL_FIELD_MAP;

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


	@RequestMapping(value ="/export",method = RequestMethod.GET)
	@ApiOperation(value = "导出中奖纪录", notes = "")
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



	////////////////////////////////////////////////
	@RequestMapping(value = "/page/v1",method = RequestMethod.POST)
	@ApiOperation(value = "中奖纪录列表V1.1", notes = "")
	@ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
	public RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>> listWithOrganization(@RequestBody MarketingMembersWinRecordListParam winRecordListParam) throws Exception {
		// 查看当前用户所在组织的所有会员的中奖信息
		RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>> restResult=new RestResult<PageResults<List<MarketingMembersWinRecordListReturn>>>();
		PageResults<List<MarketingMembersWinRecordListReturn>> pageResults = service.listWithOrganization(winRecordListParam);

		restResult.setState(200);
		restResult.setResults(pageResults);
		restResult.setMsg("成功");
		return restResult;
	}


	@RequestMapping(value ="/export/v1",method = RequestMethod.GET)
	@ApiOperation(value = "导出中奖纪录V1.1", notes = "")
	public void littleWinRecordOutExcelWithOrganization( HttpServletResponse response) throws SuperCodeException, UnsupportedEncodingException, Exception {

		// step-1: 参数设置
		MarketingMembersWinRecordListParam winRecordListParam = new MarketingMembersWinRecordListParam();
		winRecordListParam.setStartNumber(1);
		winRecordListParam.setPageSize(Integer.MAX_VALUE);
		winRecordListParam.setOrganizationId(getOrganizationId());

		// step-2: 获取结果
		// 数据表中数据很大会导致内存问题;请求慢，excel打开慢等问题
		PageResults<List<MarketingMembersWinRecordListReturn>> pageResults = service.listWithOrganization(winRecordListParam);
		List<MarketingMembersWinRecordListReturn> list = pageResults.getList();

		// step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
		Map filedMap = null;
		try {
			filedMap = JsonToMapUtil.toMap(MARKET_WIN_RECORD_EXCEL_FIELD_MAP);
		} catch (Exception e){
			logger.error("{desc:营销中奖记录表头解析异常" + e.getMessage() + "}");
			throw new SuperCodeException("营销中奖记录表头解析异常",500);
		}
		// step-4: 导出前端
		ExcelUtils.listToExcel(list, filedMap, "中奖记录", response);

	}

}
