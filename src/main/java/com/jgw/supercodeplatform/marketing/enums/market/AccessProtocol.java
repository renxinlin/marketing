package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Getter;

/**
 * 前后台微信授授权协议定义
 */
public enum AccessProtocol {

    ACTIVITY_MEMBER(0,"活动"), ACTIVITY_SALER(2,"导购业务"),
    ACTIVITY_COUPON(4,"抵扣券"), ACTIVITY_PLATFORM(5, "全网平台运营活动");
    /**
     * 业务类型
     */
    @Getter
    private int type;
    /**
     * 业务描述
     */
    @Getter
    private String desc;

    AccessProtocol(int type, String desc){
        this.type=type;
        this.desc=desc;
    }
}
