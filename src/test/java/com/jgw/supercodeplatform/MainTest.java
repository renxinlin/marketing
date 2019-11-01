package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtilWithOutCodeNum;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) throws SuperCodeException, IOException {

//        List<MarketingPrizeTypeMO> marketingPrizeTypeMOs = new ArrayList<MarketingPrizeTypeMO>();
//
//        MarketingPrizeTypeMO m0 = new MarketingPrizeTypeMO();
//        m0.setAwardGrade((byte)0);
//        m0.setRemainingStock(Integer.MAX_VALUE);
//        m0.setPrizeProbability(10);
//        m0.setPrizeTypeName("未中奖");
//
//        MarketingPrizeTypeMO m1 = new MarketingPrizeTypeMO();
//        m1.setAwardGrade((byte)1);
//        m1.setRemainingStock(Integer.MAX_VALUE);
//        m1.setPrizeProbability(10);
//        m1.setPrizeTypeName("一等奖");
//
//        MarketingPrizeTypeMO m2 = new MarketingPrizeTypeMO();
//        m2.setAwardGrade((byte)2);
//        m2.setRemainingStock(Integer.MAX_VALUE);
//        m2.setPrizeProbability(10);
//        m2.setPrizeTypeName("二等奖");
//
//        MarketingPrizeTypeMO m3 = new MarketingPrizeTypeMO();
//        m3.setAwardGrade((byte)3);
//        m3.setRemainingStock(Integer.MAX_VALUE);
//        m3.setPrizeProbability(10);
//        m3.setPrizeTypeName("三中奖");
//
//        MarketingPrizeTypeMO m4 = new MarketingPrizeTypeMO();
//        m4.setAwardGrade((byte)4);
//        m4.setRemainingStock(Integer.MAX_VALUE);
//        m4.setPrizeProbability(20);
//        m4.setPrizeTypeName("四中奖");
//
//        MarketingPrizeTypeMO m5 = new MarketingPrizeTypeMO();
//        m5.setAwardGrade((byte)5);
//        m5.setRemainingStock(Integer.MAX_VALUE);
//        m5.setPrizeProbability(50);
//        m5.setPrizeTypeName("五中奖");
//
//        marketingPrizeTypeMOs.add(m0);
//        marketingPrizeTypeMOs.add(m1);
//        marketingPrizeTypeMOs.add(m2);
//        marketingPrizeTypeMOs.add(m3);
//        marketingPrizeTypeMOs.add(m4);
//        marketingPrizeTypeMOs.add(m5);
//
//
//        MarketingPrizeTypeMO lto = LotteryUtilWithOutCodeNum.platfromStartLottery(marketingPrizeTypeMOs, false);
//
//        System.out.println("------>"+ JSON.toJSONString(lto));

//        String redirectUri = "https://antifake.h5.kf315.net/?s=52179&uuid=1297cf6ba9fd436aa8cb0468c682d4f0#/h2";
//        String fd = URLEncoder.encode(redirectUri, "UTF-8");
        //String hg = redirectUri.replaceAll("#", "&");
        byte[] files = FileUtils.readFileToByteArray(new File("C:\\Users\\JGW\\Desktop\\acfb9d3fd706442989bde4edaebfec6c.p12"));

        System.out.println("--->" + files.length);
    }

}
