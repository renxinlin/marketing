package com.jgw.supercodeplatform.marketing.controller.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService.PageResults;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.ExcelUtils;
import com.jgw.supercodeplatform.marketing.common.util.JsonToMapUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingMembersWinRecordListReturn;
import com.jgw.supercodeplatform.marketing.pojo.pay.RedPackageParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingMembersWinRecordService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingWxTradeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marketing/winRecord")
@Api(tags = "中奖管理")
@Slf4j
public class MarketingMembersWinRecordController extends CommonUtil {
 
	@Autowired
	private MarketingMembersWinRecordService service;

	@Autowired
	private MarketingUserMapper marketingUserMapper;

	@Autowired
	private MarketingWxTradeOrderService marketingWxTradeOrderService;


	// 	@Value("${marketing.winRecord.sheetHead}")
	@Value("{\"activityName\":\"奖品类型\",\"userName\":\"会员姓名\",\"wxName\":\"会员微信昵称\", \"openId\":\"会员微信ID\",\"mobile\":\"会员手机\",\"prizeTypeName\":\"中奖奖次\", \"winningAmount\":\"中奖金额\",\"winningCode\":\"中奖码\",\"productName\":\"中奖产品\",\"customerName\":\"活动门店\"}")
	private String MARKET_WIN_RECORD_EXCEL_FIELD_MAP;



	////////////////////////////////////////////////
	@RequestMapping(value = "/page/v1",method = RequestMethod.POST)
	@ApiOperation(value = "中奖纪录列表V1.1", notes = "")
	@ApiImplicitParams(value= { @ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token") 	})
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
	@ApiOperation(value = "根据活动Id导出中奖纪录V1.1,没有活动ID可查询用户的所有会员中奖", notes = "")
	@ApiImplicitParams(value= {
			@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
			,@ApiImplicitParam(paramType="long",value = "活动主键id",name="activitySetId")
  	})
	public void littleWinRecordOutExcelWithOrganization( Long activitySetId) throws SuperCodeException, UnsupportedEncodingException, Exception {

		// step-1: 参数设置
		MarketingMembersWinRecordListParam winRecordListParam = new MarketingMembersWinRecordListParam();
		winRecordListParam.setStartNumber(1);
		winRecordListParam.setPageSize(Integer.MAX_VALUE);
		winRecordListParam.setOrganizationId(getOrganizationId());
		winRecordListParam.setActivitySetId(activitySetId);

		// step-2: 获取结果
		// 数据表中数据很大会导致内存问题;请求慢，excel打开慢等问题
		PageResults<List<MarketingMembersWinRecordListReturn>> pageResults = service.listWithOrganization(winRecordListParam);
		List<MarketingMembersWinRecordListReturn> list = pageResults.getList();

		// step-3:处理excel字段映射 转换excel {filedMap:[ {key:英文} ,  {value:中文} ]} 有序
		Map<String, String> filedMap = null;
		try {
			filedMap = JsonToMapUtil.toMap(MARKET_WIN_RECORD_EXCEL_FIELD_MAP);
		} catch (Exception e){
			log.error("{desc:营销中奖记录表头解析异常" + e.getMessage() + "}");
			throw new SuperCodeException("营销中奖记录表头解析异常",500);
		}
		// step-4: 导出前端
		ExcelUtils.listToExcel(list, filedMap, "中奖记录", response);

	}

	@ApiOperation("发送中奖红包")
	@PostMapping("/sendRedPackage")
	@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token")
	public RestResult<?> sendWxTrade(@RequestBody @Valid RedPackageParam redPackageParam) throws Exception {
		String res = marketingWxTradeOrderService.sendPayTradeOrder(redPackageParam.getTradeNo(), redPackageParam.getWinningCode());
		if (res == null) {
			return RestResult.success();
		}
		return RestResult.fail(res, null);
	}

}
