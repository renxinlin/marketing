package com.jgw.supercodeplatform.marketing.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivityProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/marketing/front/outerReq")
@Api(tags = "营销h5与外部系统交互公共接口类")
public class MarketingOuterReqFrontController extends CommonUtil {

    @Autowired
    private MarketingActivityProductService maProductService;


    @RequestMapping(value = "/relationActProds",method = RequestMethod.GET)
    @ApiOperation(value = "获取活动做过码关联的产品及产品批次数据", notes = "")
    @ApiImplicitParams(value= {@ApiImplicitParam(paramType="query",value = "组织id",name="organizationId")
    		})
    public JSONObject relationActProds(@RequestParam(required=false) String organizationId) throws Exception {
        return maProductService.relationActProds(organizationId);
    }

}
