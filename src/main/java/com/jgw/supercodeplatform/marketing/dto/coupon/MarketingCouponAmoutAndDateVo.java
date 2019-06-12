package com.jgw.supercodeplatform.marketing.dto.coupon;

import lombok.Data;

import java.util.Date;

@Data
public class MarketingCouponAmoutAndDateVo {
    /**
     * 优惠券金额
     */
    private Double couponAmount;
    /**
     * 领取截止时间
     */
    private Date deductionDate;
}
