package com.jgw.supercodeplatform.marketing.controller.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityCreateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetStatusUpdateParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.vo.activity.ReceivingAndWinningPageVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/activity/set")
@Api(tags = "活动设置管理")
public class MarketingActivitySetController {

	@Autowired
	private MarketingActivitySetService service;

    @Autowired
    private MarketingActivityProductService maProductService;
    /**
     * 活动编辑;本期不做
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/edit",method = RequestMethod.POST)
//    @ApiOperation(value = "活动编辑", notes = "")
//    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
//    public RestResult<String> edit(@RequestBody MarketingActivityCreateParam activitySetParam) throws Exception {
//        return service.edit(activitySetParam);
//    }



    /**
     * 活动创建
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "活动创建", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> create(@RequestBody MarketingActivityCreateParam activitySetParam) throws Exception {
    	return service.memberActivityAdd(activitySetParam);
    }
    
    /**
     * 活动编辑
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "活动更新", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> update(@RequestBody MarketingActivityCreateParam activitySetParam) throws Exception {
    	return service.update(activitySetParam);
    }
    
    /**
     * 停用或启用活动
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/disOrEnable",method = RequestMethod.POST)
    @ApiOperation(value = "停用或启用", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    })
    public RestResult<String> disOrEnable(@RequestBody MarketingActivitySetStatusUpdateParam mUpdateStatus) throws Exception {
    	return service.updateActivitySetStatus(mUpdateStatus);
    }
    
    /**
     * 
     * @param marketingActivityParam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getPageInfo",method = RequestMethod.GET)
    @ApiOperation(value = "根据活动id获取领取页和中奖页数据", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),@ApiImplicitParam(paramType="query",value = "活动设置主键id",name="activitySetId")})
    public RestResult<ReceivingAndWinningPageVO> getPageInfo(@RequestParam(required=true) Long activitySetId) throws Exception {
    	return service.getPageInfo(activitySetId);
    }
    

    @RequestMapping(value = "/updatePage",method = RequestMethod.POST)
    @ApiOperation(value = "更新领取页中奖页", notes = "")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<String> updatePage(@RequestBody MarketingReceivingPageParam mUpdateParam) throws Exception {
    	return service.updatePage(mUpdateParam);
    }

    /**
     * 获取活动基础信息
     * @param activitySetId
     * @return
     */
    @RequestMapping(value = "/getBaseInfo",method = RequestMethod.GET)
    @ApiOperation(value = "编辑活动： 获取活动基础信息", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),@ApiImplicitParam(paramType="query",value = "活动设置主键id",name="activitySetId")})
    public RestResult<MarketingActivitySet> getActivityBaseInfoByeditPage(@RequestParam(required=true) Long activitySetId){
        return service.getActivityBaseInfoByeditPage(activitySetId);

    }


    @RequestMapping(value = "/relationActProds",method = RequestMethod.GET)
    @ApiOperation(value = "获取活动做过码关联的产品及产品批次数据", notes = "")
    @ApiImplicitParams(value= {
    		 @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    		})
    public JSONObject relationActProds() throws Exception {
    	
        return maProductService.relationActProds();
    }



}
