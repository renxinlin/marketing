package com.jgw.supercodeplatform.marketing.controller.h5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.cache.GlobalRamCache;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
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
	@ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "扫码唯一id",name="wxstate",required=false),@ApiImplicitParam(paramType="query",value = "获取设置主键id",name="activitySetId",required=false)})
	public RestResult<MarketingWinningPage> getByAsId(String wxstate,Long activitySetId ) throws SuperCodeException{
        if (null==activitySetId) {
        	ScanCodeInfoMO scInfoMO=GlobalRamCache.scanCodeInfoMap.get(wxstate);
        	if (null==scInfoMO) {
        		throw new SuperCodeException("授权回调方法无法根据state="+wxstate+"获取到用户扫码缓存信息请重试", 500);
        	}
        	activitySetId=scInfoMO.getActivitySetId();
		}
		MarketingWinningPage mWinningPage=service.selectByActivitySetId(activitySetId);
		if (null==mWinningPage) {
			throw new SuperCodeException("h5扫码时获取中奖页信息失败根据activitySetId="+activitySetId+"无法获取中奖页信息", 500);
		}
		RestResult<MarketingWinningPage> restResult=new RestResult<MarketingWinningPage>();
		restResult.setState(200);
		restResult.setResults(mWinningPage);
		restResult.setMsg("成功");
		return restResult;
	}
}
