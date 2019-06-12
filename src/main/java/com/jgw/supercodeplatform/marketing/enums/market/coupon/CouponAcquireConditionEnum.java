package com.jgw.supercodeplatform.marketing.enums.market.coupon;

import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券获得条件枚举
 */
public enum CouponAcquireConditionEnum {

    /**
     * 首次积分
     */
    FIRST((byte)0,"首次积分"),
    /**
     * 一次积分达到
     */
    ONCE_LIMIT((byte)1,"一次积分达到"),
    /**
     * 累计积分达到
     */
    LIMIT((byte)2,"累计积分达到"),

    /**
     * 购买商品
     */
    SHOPPING((byte)4,"参加获得抵扣券的产品");

    @Getter
    @Setter
    private Byte condition;



    @Getter
    @Setter
    private String desc;

    CouponAcquireConditionEnum(Byte condition, String desc){
        this.condition=condition;
        this.desc=desc;

    }

}
