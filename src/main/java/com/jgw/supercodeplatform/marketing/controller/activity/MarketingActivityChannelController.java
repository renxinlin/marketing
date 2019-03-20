package com.jgw.supercodeplatform.marketing.controller.activity;


import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityChannelService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * 活动产品批次信息
 */
@RestController
@RequestMapping("/marketing/activity/channel")
@Api(tags = "活动渠道管理")
public class MarketingActivityChannelController {


    @Autowired
    private MarketingActivityChannelService service;
    /**
     * 获取活动基础信息
     * @param activitySetId
     * @return
     */
    @RequestMapping(value = "/getchannelInfo",method = RequestMethod.GET)
    @ApiOperation(value = "编辑活动： 获取活动渠道信息", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="header",value = "新平台token--开发联调使用",name="super-token"),@ApiImplicitParam(paramType="query",value = "活动设置主键id",name="activitySetId")})
    public RestResult<HashSet<MarketingActivityProductParam>> getActivityProductInfoByeditPage(@RequestParam(required=true) Long activitySetId){
        return service.getActivityChannelInfoByeditPage(activitySetId);

    }
}
