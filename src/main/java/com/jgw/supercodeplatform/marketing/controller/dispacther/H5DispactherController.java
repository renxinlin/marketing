package com.jgw.supercodeplatform.marketing.controller.dispacther;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller.H5SalerRuleExchangeController;
import com.jgw.supercodeplatform.marketingsaler.order.controller.H5SalerOrderFormController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */

@RestController
@RequestMapping("marketing/h5/button/dispacther")
public class H5DispactherController {
    @Autowired
    private H5SalerOrderFormController h5SalerOrderFormController;
    @Autowired
    private H5SalerRuleExchangeController h5SalerRuleExchangeController;

    @GetMapping(value = "/getIntegralStatusByH5")
    @ApiOperation(value = "H5查看开启页面按钮", notes = "")
    public RestResult<String> getIntegralStatusByH5(@RequestParam String organizationId) throws Exception {
        RestResult<String> result = null;
        result=    h5SalerRuleExchangeController.getIntegralStatusByH5(organizationId);
        result = h5SalerOrderFormController.getIntegralStatusByH5(organizationId);
        return result;
    }


}
