package com.jgw.supercodeplatform.marketing.controller.h5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingWinningPageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 中奖页controller
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/front/winningPag")
@Api(tags = "h5获取中奖页信息")
public class MarketingWinningPageFrontController {

	@Autowired
	private MarketingWinningPageService service;
	
	@RequestMapping(value = "/getByAsId",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动设置id获取中奖页记录", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "活动设置id",name="activitySetId")})
	public RestResult<MarketingWinningPage> getByAsId(@RequestParam(required=true)Long activitySetId){
		MarketingWinningPage mWinningPage=service.selectByActivitySetId(activitySetId);
		RestResult<MarketingWinningPage> restResult=new RestResult<MarketingWinningPage>();
		restResult.setState(200);
		restResult.setResults(mWinningPage);
		restResult.setMsg("成功");
		return restResult;
	}
}
