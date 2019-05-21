package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

public enum  MemberTypeEnums {
    /**
     * 无条件
     */
    VIP((byte)0,"会员"),
    SALER((byte)1,"导购员"),


    Min((byte)1,""),

    Max((byte)4,"");

    @Getter
    @Setter
    private Byte type;

    @Getter
    @Setter
    private String desc;

    MemberTypeEnums(Byte type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
