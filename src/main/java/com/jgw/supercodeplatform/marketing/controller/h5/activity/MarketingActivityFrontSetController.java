package com.jgw.supercodeplatform.marketing.controller.h5.activity;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingReceivingPageParam;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/activity")
@Api(tags = "h5活动/获取活动预览信息")
public class MarketingActivityFrontSetController {

	@Autowired
	private MarketingActivitySetService service;


    /**
     * 活动预览
     * @param activitySetId
     * @return
     * @throws IOException 
     * @throws WriterException 
     */
    @GetMapping(value = "/getPreviewData")
    @ApiOperation("获取活动预览数据")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",value = "唯一id",name="uuid")})
    public RestResult<MarketingReceivingPageParam> getPreviewData(String uuid) throws WriterException, IOException{
        return service.getPreviewParam(uuid);
    }

}
