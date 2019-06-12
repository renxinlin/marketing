package com.jgw.supercodeplatform.marketing.dto.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deductionStartDate;

    /**
     * 领取截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deductionEndDate;
}
