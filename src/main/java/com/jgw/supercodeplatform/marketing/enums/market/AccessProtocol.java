package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;
import lombok.Setter;

/**
 * 前后台微信授授权协议定义
 */
public enum AccessProtocol {

    ACTIVITY_MEMBER(0,"活动"),
    ACTIVITY_SALER(2,"导购业务");
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

    AccessProtocol(int type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
