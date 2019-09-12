package com.jgw.supercodeplatform.marketing.controller.dispacther;

import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.generator.mapper.MarketingUserMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.JwtUser;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.OpenIntegralStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.controller.H5SalerRuleExchangeController;
import com.jgw.supercodeplatform.marketingsaler.order.controller.H5SalerOrderFormController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Api(tags = "H5页面查看按钮")
@RestController
@RequestMapping("marketing/h5/button/dispacther")
public class H5DispactherController {
    @Autowired
    protected RedisUtil redisUtil;

    @GetMapping(value = "/getIntegralStatusByH5")
    @ApiOperation(value = "H5查看开启页面按钮", notes = "")
    public RestResult<List<ButtonStatusVo>> getIntegralStatusByH5(@ApiIgnore H5LoginVO jwtUser) throws Exception {
        String organizationId = jwtUser.getOrganizationId();
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
