package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 前后台微信授授权协议定义
 */
public enum ActivityStatusEnum {

    UNDER(0,"下架"),
    UP(1,"上架");
    /**
     * 业务类型
     */
    @Getter
    @Setter
    private int type;
    /**
     * 业务描述
     */
    @Getter
    @Setter
    private String desc;

    ActivityStatusEnum(int type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
