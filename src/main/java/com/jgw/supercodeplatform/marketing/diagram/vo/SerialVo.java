package com.jgw.supercodeplatform.marketing.diagram.vo;

import lombok.Data;

/**
 * 折线
 */
@Data
public class SerialVo {
    private String time;
    private int value;


    public void add(int i) {
        value = value + 1;
    }
}
