package com.jgw.supercodeplatform.marketing.enums.market.coupon;

import lombok.Getter;
import lombok.Setter;

public enum DeductionProductTypeEnum {
    NO_LIMIT((byte)0,"是否支持被抵扣产品,无限制");



    @Getter
    @Setter
    private byte type;
    /**
     * 业务描述
     */
    @Getter
    @Setter
    private String desc;

    DeductionProductTypeEnum(byte type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
