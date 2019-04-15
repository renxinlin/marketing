package com.jgw.supercodeplatform.marketing.enums.portrait;

/**
 * 企业画像分类标签
 */
public enum PortraitTypeEnum {
    PORTRAIT(14001,"企业画像"),
    LABEL(14002,"标签");

    PortraitTypeEnum(Integer typeId, String mean) {
        this.typeId = typeId;
        this.mean = mean;
    }

    private Integer typeId;
    private String mean;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}