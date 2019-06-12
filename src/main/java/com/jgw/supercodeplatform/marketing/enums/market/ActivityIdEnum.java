package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;
// TODO 合并ID和type枚举

/**
 * 对应activity表
 */
public enum ActivityIdEnum {
    /**
     * 同marketing_activity 表的ActivityType值
     */
    ACTIVITY_WX_RED_PACKAGE((byte)1,(byte)1,"微信红包"),
    ACTIVITY_2((byte)2,(byte)1,"锦鲤翻牌"),
    ACTIVITY_SALER((byte)3,(byte)2,"导购红包"),
    ACTIVITY_COUPON((byte)4,(byte)1,"抵扣券"),

    /**
     * 判断入参是否在最值之间
     */
    Min((byte)1,(byte)1,""),

    Max((byte)3,(byte)2,"");

    @Getter
    @Setter
    private Byte id;

    @Getter
    @Setter
    private Byte type;

    @Getter
    @Setter
    private String desc;

    ActivityIdEnum(Byte id, Byte type, String desc){
        this.id=id;
        this.type=type;
        this.desc=desc;
    }
}
