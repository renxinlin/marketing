package com.jgw.supercodeplatform.marketing.controller.dispacther;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller.SalerRuleRewardController;
import com.jgw.supercodeplatform.marketingsaler.order.controller.SalerOrderFormController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RestController
@RequestMapping("marketing/button/dispacther")
public class DispactherController {
    @Autowired
    private SalerRuleRewardController salerRuleRewardController;

    @Autowired
    private SalerOrderFormController orderFormController;

    @GetMapping(value = "/openIntegralStatus")
    @ApiOperation(value = "开启关闭页面领积分按钮 1开启2 关闭", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> openIntegralStatus(String status) throws Exception {
        salerRuleRewardController.openIntegralStatus(status);
        orderFormController.openIntegralStatus(status);
        return RestResult.success();
    }




    @GetMapping(value = "/getIntegralStatus")
    @ApiOperation(value = "查看开启页面领积分按钮", notes = "")
    @ApiImplicitParams(value= {
            @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true),
    })
    public RestResult<String> getIntegralStatus() throws Exception {
        RestResult<String> result = null;
        result =  salerRuleRewardController.getIntegralStatus();
        result= orderFormController.getIntegralStatus();
        return result;
    }


}
