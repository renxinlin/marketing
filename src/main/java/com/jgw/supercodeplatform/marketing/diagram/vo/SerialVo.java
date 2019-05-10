package com.jgw.supercodeplatform.marketing.diagram.vo;

/**
 * 折线
 */
public class SerialVo {
    private String time;
    private int value;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void add(int i) {
        value = value + 1;
    }
}
