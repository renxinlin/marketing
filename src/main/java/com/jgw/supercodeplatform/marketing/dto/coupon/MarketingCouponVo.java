package com.jgw.supercodeplatform.marketing.dto.coupon;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("抵扣券规则")
public class MarketingCouponVo {


    private List<MarketingCouponAmoutAndDateVo> couponAmoutAndDateVo;

    private Byte deductionProductType = 1;
@ApiModelProperty("支持被抵扣的渠道 1仅获得抵扣券的门店 0不限制")
    private Byte deductionChannelType;

}