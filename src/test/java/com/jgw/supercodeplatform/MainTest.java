package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) {
        String redirectUri = "https://antifake.h5.kf315.net/?codeTypeId=21&fakeCode=488250077757241592801&sBatchId=60711&uuid=36c0804108d349a3b5ee540ca66a5421#/h3";
        String uri = null;
        String[] uris = redirectUri.split("#");
        if (uris.length > 1) {
            String firUri = uris[0] + "&wxstate="+"sss"+"&organizationId="+"ff"+"&memberId="+"pp";
            uri = firUri;
            for (int i= 1;i<uris.length;i++) {
                uri = uri + "#" + uris[i];
            }
        }
        System.out.println("------>"+ "redirect:" + uri);
    }

}
