package com.jgw.supercodeplatform.marketing.enums.market.coupon;

import lombok.Getter;
import lombok.Setter;

/**
 * 抵扣券:不选默认所有渠道
 */
public enum CouponWithAllChannelEnum {
    ALL(1,"1所有渠道"),

    NOT_ALL(0,"0存在选择渠道");


    @Getter
    @Setter
    private int type;
    /**
     * 业务描述
     */
    @Getter
    @Setter
    private String desc;

    CouponWithAllChannelEnum(int type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
