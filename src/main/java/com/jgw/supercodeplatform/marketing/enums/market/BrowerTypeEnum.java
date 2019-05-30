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
    ZFBAO((byte)2,"支付宝"),
    DINGDING((byte)3,"钉钉"),
    BROWER((byte)4,"浏览器 "),
    QQ((byte)5,"qq"),
    OTHER((byte)6,"其他"),


    Min((byte)1,""),
    Max((byte)6,"");

    @Getter
    @Setter
    private Byte status;

    @Getter
    @Setter
    private String desc;

    BrowerTypeEnum(Byte status, String desc){
        this.status = status;
        this.desc = desc;
    }

}
