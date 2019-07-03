package com.jgw.supercodeplatform.marketing.dto.coupon;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("抵扣券规则")
public class MarketingCouponVo {


    private List<MarketingCouponAmoutAndDateVo> couponRules = new ArrayList<>();

    private Byte deductionProductType = 1;
    @ApiModelProperty("支持被抵扣的渠道 1仅获得抵扣券的门店 0不限制")
    private Byte deductionChannelType;

}