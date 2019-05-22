package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;
// TODO 合并ID和type枚举
public enum ActivityIdEnum {
    /**
     * 同marketing_activity 表的ActivityType值
     */
    ACTIVITY_WX_RED_PACKAGE((byte)1,"微信红包"),
    ACTIVITY_2((byte)2,"锦鲤翻牌"),
    ACTIVITY_SALER((byte)3,"导购"),

    /**
     * 判断入参是否在最值之间
     */
    Min((byte)1,""),

    Max((byte)3,"");

    @Getter
    @Setter
    private Byte id;

    @Getter
    @Setter
    private String desc;

    ActivityIdEnum(Byte id, String desc){
        this.id=id;
        this.desc=desc;
    }
}
