package com.jgw.supercodeplatform.marketingsaler.integral.infrastructure;

import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import org.apache.http.util.Asserts;

import java.math.BigDecimal;

public class MoneyCalculator {
    static int randomMoney = 1;

    public static double calculatorSalerExcgange(SalerRuleExchange salerRuleExchange) {
        // 中奖概率
        Integer prizeProbability = salerRuleExchange.getPrizeProbability();
        if(prizeProbability ==null || prizeProbability<=0){
            throw new BizRuntimeException("同学,没中奖!");
        }
        double random = Math.random(); // [0,1)
        long round = Math.round(random * 100.0D);//[0,100) -> [0,100] 0-prizeProbability 中奖 prizeProbability-100 未中奖
        if(round > prizeProbability){
            throw new BizRuntimeException("同学,没中奖!");
        }
        // 获取金额
        Double money = 0.00D;
        Integer isRrandomMoney = salerRuleExchange.getIsRrandomMoney();
        if(randomMoney == isRrandomMoney){
            float highRand = salerRuleExchange.getHighRand();
            float lowRand = salerRuleExchange.getLowRand();
            money = lowRand +  Math.random() * (highRand - lowRand);
            BigDecimal bg = new BigDecimal(money.toString());
            // 保留两位
            money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        }else {

            money =  salerRuleExchange.getPrizeAmount().doubleValue();
            BigDecimal bg = new BigDecimal(money.toString());
            money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Asserts.check(money>0.00D,"抽中0元");
        return money;
    }

    public static void main(String[] args) throws InterruptedException {
        long prizeProbability = 100;
        int i = 0;
        int c= 0;
        while (i<10000){
            i++;
            double random = Math.random(); // [0,1)

            long round = Math.round(random * 100.0D);//[0,100) -> [0,100] 0-prizeProbability 中奖 prizeProbability-100 未中奖
            System.out.println("============="+round);
            if(round > prizeProbability){
                System.out.println("同学,没中奖!");
                c++;
            }
        }
        System.out.println(c);
    }
}