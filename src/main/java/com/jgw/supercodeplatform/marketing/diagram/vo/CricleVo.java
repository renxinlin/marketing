package com.jgw.supercodeplatform.marketing.diagram.vo;

import lombok.Data;

/**
 * 圆饼图
 *
 */
@Data
public class CricleVo {
    private  String item;
    private  double percent;
    // 默认值同percent
    private  String percentStr = "0";
    private  int count;
    /**
     *
     * @param i 统计量增加
     */
    public void add(int i) {
        this.count = this.count+i;
    }
}
