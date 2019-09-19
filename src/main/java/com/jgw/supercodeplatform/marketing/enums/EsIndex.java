package com.jgw.supercodeplatform.marketing.enums;

/**
 * @author Created by jgw136 on 2018/04/27.
 */
public enum  EsIndex {
    /**
     * 业务索引:营销
     */
    MARKETING("marketing"),
    /**
     * 业务索引:integral
     */
    INTEGRAL("integral"),
    /**
     * 业务索引:导购
     */
    MARKET_SALER_INFO("marketingsaler"),
    /**
     * 业务索引:
     */
    MARKET_DIAGRAM_REMBER("marketdiagram"),
    /**
     * 活动索引
     */
    MARKET_SCAN_INFO("marketingscan"),
    /**
     *全网平台扫码活动
     */
    MARKET_PLATFORM_SCAN_INFO("marketingplatformscan");

    private String index;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    EsIndex(String index) {
        setIndex(index);
    }
}
