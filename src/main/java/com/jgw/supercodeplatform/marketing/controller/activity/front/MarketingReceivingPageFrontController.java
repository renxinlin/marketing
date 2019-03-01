package com.jgw.supercodeplatform.marketing.controller.activity.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingReceivingPageService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 领取页controller
 * @author czm
 *
 */
@RestController
@RequestMapping("/marketing/front/receivingPag")
public class MarketingReceivingPageFrontController {

	@Autowired
	private MarketingReceivingPageService service;
	
	@RequestMapping(value = "/getByAsId",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动设置id获取领取页记录，扫码时可以通过该接口获取是否需要领取页", notes = "")
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "活动设置id",name="activitySetId")})
	public RestResult<MarketingReceivingPage> getByAsId(@RequestParam(required=true)Long activitySetId){
		MarketingReceivingPage mReceivingPage=service.selectByActivitySetId(activitySetId);
		RestResult<MarketingReceivingPage> restResult=new RestResult<MarketingReceivingPage>();
		restResult.setState(200);
		restResult.setResults(mReceivingPage);
		restResult.setMsg("成功");
		return restResult;
	}
}
