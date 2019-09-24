package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) {

        JSONObject jsb = new JSONObject();
        jsb.put("dd", 1L);


        System.out.println("------>"+ "redirect:" + jsb.getByte("dd"));
    }

}
