package com.jgw.supercodeplatform.marketing.diagram.vo;

import lombok.Data;

/**
 * 地图
 */
@Data
public class MemberMapVo   {
    private String name;
    private int value;

    /**
     *
     * @param i 统计量增加
     */
    public void add(int i) {
        this.value = this.value+i;
    }


}
