package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户端类型
 */
public enum BrowerTypeEnum {

    /**
     * 无条件
     */
    WX((byte)1,"微信"),
    OTHER((byte)2,"其他"),
    QQ((byte)3,"qq"),
    ZFBAO((byte)4,"支付宝"),


    Min((byte)1,""),

    Max((byte)4,"");

    @Getter
    @Setter
    private Byte status;

    @Getter
    @Setter
    private String desc;

    BrowerTypeEnum(Byte status, String desc){

    }

}
