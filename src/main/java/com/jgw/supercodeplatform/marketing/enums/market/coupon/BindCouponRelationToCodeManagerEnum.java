package com.jgw.supercodeplatform.marketing.enums.market.coupon;

import lombok.Getter;
import lombok.Setter;

public enum  BindCouponRelationToCodeManagerEnum {
    /**
     * 解绑
     */
    UNBIND((byte)0,"解绑"),

    /**
     * 绑定
     */
    BIND((byte)1,"绑定");

    @Getter
    @Setter
    private Byte binding;



    @Getter
    @Setter
    private String desc;

    BindCouponRelationToCodeManagerEnum(Byte binding, String desc){
        this.binding=binding;
        this.desc=desc;

    }
}
