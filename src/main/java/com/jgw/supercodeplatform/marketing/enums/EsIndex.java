package com.jgw.supercodeplatform.marketing.enums;

/**
 * @author Created by jgw136 on 2018/04/27.
 */
public enum  EsIndex {
    MARKETING("marketing"),INTEGRAL("integral"),MARKET_DIAGRAM_REMBER("marketdiagram"),

    MARKET_SCAN_INFO("marketingscan");

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
