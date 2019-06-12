package com.jgw.supercodeplatform.marketing.enums.market.coupon;

import lombok.Getter;
import lombok.Setter;

public enum DeductionChannelTypeEnum {
    NO_LIMIT((byte)0,"无限制"),

    ONLY_CHANNELS((byte)1,"仅支持选定渠道");


    @Getter
    @Setter
    private byte type;
    /**
     * 业务描述
     */
    @Getter
    @Setter
    private String desc;

    DeductionChannelTypeEnum(byte type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
