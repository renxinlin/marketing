package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AwardTypeEnum {

    ENTITY(1, "实物"), COUPON(2, "优惠券"),
    INTEGRAL(3, "积分"), MONEY(4, "红包"),
    OTHER(9, "其他");

    public final int type;

    public final String desc;

}
