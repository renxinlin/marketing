package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

public enum ActivityTypeEnum {
    /**
     * 同marketing_activity 表的ActivityType值
     */
    ACTIVITY_MEMBER((byte)0,"会员活动"),
    ACTIVITY_SALER((byte)1,"导购活动"),


    Min((byte)1,""),

    Max((byte)4,"");

    @Getter
    @Setter
    private Byte type;

    @Getter
    @Setter
    private String desc;

    ActivityTypeEnum(Byte type, String desc){
        this.type=type;
        this.desc=desc;
    }

}
