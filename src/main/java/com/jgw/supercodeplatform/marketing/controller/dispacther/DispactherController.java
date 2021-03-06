package com.jgw.supercodeplatform.marketing.controller.dispacther;

import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller.SalerRuleRewardController;
import com.jgw.supercodeplatform.marketingsaler.order.controller.SalerOrderFormController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */

@Api(tags = "后台系统管理按钮")
@RestController
@RequestMapping("marketing/button/dispacther")
public class DispactherController {
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    protected RedisUtil redisUtil;
    @Autowired
    private SalerRuleRewardController salerRuleRewardController;
    @Autowired
    private SalerOrderFormController orderFormController;

    @GetMapping("/openCloseStatus")
    @ApiOperation("开启关闭页面按钮 1开启2 关闭")
    @ApiImplicitParams({@ApiImplicitParam(name = "super-token", paramType = "header", value = "token信息", required = true),
    @ApiImplicitParam(name = "buttonType", paramType = "query", value = "按钮类型<红包:redBag,积分:salerIntegral,订货:salerOrder>", required = true),
    @ApiImplicitParam(name = "status", paramType = "query", value = "按钮值<1:启用，2：关闭>", required = true, dataType = "Integer")})
    public RestResult<String> openIntegralStatus(@RequestParam String buttonType, @RequestParam Integer status) throws Exception {
        if (status != ButtonStatus.openInt && status != ButtonStatus.closeInt){
            throw new SuperCodeExtException("状态输入不合法，必须为1或者2");
        }
        String organizationId = commonUtil.getOrganizationId();
        if (buttonType.equals(ButtonType.RED_BAG)) {
            redisUtil.set(UserConstants.MARKETING_RED_BAG + organizationId,status.toString());
        }
        if (buttonType.equals(ButtonType.SALER_INTEGRAL)) {
            redisUtil.set(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON + organizationId,status.toString());
        }
        if (buttonType.equals(ButtonType.SALER_ORDER)) {
            redisUtil.set(UserConstants.MARKETING_ORDER_BUTTON + organizationId,status.toString());
        }
        return RestResult.success();
    }




    @GetMapping("/getButtonStatus")
    @ApiOperation("查看按钮状态")
    @ApiImplicitParam(name = "super-token", paramType = "header", defaultValue = "64b379cd47c843458378f479a115c322", value = "token信息", required = true)
    public RestResult<List<ButtonStatusVo>> getIntegralStatus() throws Exception {
        String organizationId = commonUtil.getOrganizationId();
        String redBag = redisUtil.get(UserConstants.MARKETING_RED_BAG + organizationId);
        ButtonStatusVo redBagButtonStatus = new ButtonStatusVo("redBag", StringUtils.isBlank(redBag)?ButtonStatus.open:redBag);
        String salerIntegral = redisUtil.get(UserConstants.MARKETING_SALER_INTEGRAL_BUTTON + organizationId);
        ButtonStatusVo salerIntegralButtonStatus = new ButtonStatusVo("salerIntegral", StringUtils.isBlank(salerIntegral)?ButtonStatus.open:salerIntegral);
        String salerOrder = redisUtil.get(UserConstants.MARKETING_ORDER_BUTTON + organizationId);
        ButtonStatusVo salerOrderButtonStatus = new ButtonStatusVo("salerOrder", StringUtils.isBlank(salerOrder)?ButtonStatus.open:salerOrder);
        List<ButtonStatusVo> buttonStatusList = Lists.newArrayList(redBagButtonStatus, salerIntegralButtonStatus, salerOrderButtonStatus);
        return RestResult.successWithData(buttonStatusList);
    }


}
