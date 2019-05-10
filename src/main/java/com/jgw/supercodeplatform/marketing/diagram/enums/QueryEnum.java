package com.jgw.supercodeplatform.marketing.diagram.enums;


/**
 * 任务状态
 */
public enum QueryEnum {
    /**
     * 一周
     */
    WEEK("1"),
    /**
     * 两周
     */
    TWO_WEEK("2"),

    /**
     * 一个月
     */
    MONTH("3"),
    /**
     * 三个月
     */
    THREE_MONTH("4"),
    /**
     * 半年
     */
    HALF_YEAR("5"),
    /**
     * 一年
     */
    YEAR("6");

    private String status;


    QueryEnum(String status) {
        this.status = status;
    }



    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }}
