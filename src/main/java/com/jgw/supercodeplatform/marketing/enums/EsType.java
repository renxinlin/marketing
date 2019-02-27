package com.jgw.supercodeplatform.marketing.enums;

/**
 * @author Created by jgw136 on 2018/04/27.
 */
public enum EsType {
    INFO("info"),

    ;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    EsType(String type) {
        setType(type);
    }
}
