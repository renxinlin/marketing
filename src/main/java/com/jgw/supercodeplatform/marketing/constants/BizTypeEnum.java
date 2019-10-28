package com.jgw.supercodeplatform.marketing.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizTypeEnum {

    MARKETING_ACTIVITY(5, "营销活动"), MARKETING_COUPON(6, "优惠券"), MARKETING_WHEEL(7, "大转盘"), MARKETING_SIGN(8, "签到");

    private Integer businessType;

    private String businessName;

}
