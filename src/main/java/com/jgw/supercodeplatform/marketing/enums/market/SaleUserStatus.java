package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

public enum SaleUserStatus {

    /**
     * 无条件
     */
    AUDITED((byte)1,"待审核"),
    DISABLE((byte)2,"停用"),
    ENABLE((byte)3,"启用"),


    Min((byte)1,""),

    Max((byte)3,"");

    @Getter
    @Setter
    private Byte status;

    @Getter
    @Setter
    private String desc;

    SaleUserStatus(Byte condition, String desc){

    }

}
