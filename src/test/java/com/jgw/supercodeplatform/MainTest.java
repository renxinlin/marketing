package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) {
        Set<PieChartVo> ss1 = new HashSet<>();
        ss1.add(new PieChartVo("1", 1L));
        Set<PieChartVo> ss2 = new HashSet<>();
        ss2.add(new PieChartVo("1", 2L));
        Set<PieChartVo> ss = new HashSet<>();
        ss.addAll(ss1);ss.addAll(ss2);

        System.out.println("------>"+ JSON.toJSONString(ss));
    }

}
