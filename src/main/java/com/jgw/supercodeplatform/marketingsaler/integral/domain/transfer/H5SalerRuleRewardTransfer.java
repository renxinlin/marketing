package com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer;

import com.jgw.supercodeplatform.marketingsaler.integral.constants.RewardRuleConstant;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleReward;
import org.apache.http.util.Asserts;

public class H5SalerRuleRewardTransfer {

    public static int computeIntegral(SalerRuleReward rewardPojo) {
        if(RewardRuleConstant.product == rewardPojo.getRewardRule().intValue()){
            Integer rewardIntegral = rewardPojo.getRewardIntegral();
            Asserts.check(rewardIntegral!=null && rewardIntegral>0,"奖励积分异常");
            return rewardIntegral;
        }

        if(RewardRuleConstant.consume == rewardPojo.getRewardRule().intValue()){
            // （价格）除以（ 每消费X元）乘以 （积分）
            Float productPrice = rewardPojo.getProductPrice();
            Float perConsume = rewardPojo.getPerConsume();
            try {
                int  rewardIntegral = (int) (productPrice / perConsume * rewardPojo.getRewardIntegral());
                return rewardIntegral;
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("奖励积分异常");
            }

        }
        throw new RuntimeException("奖励积分规则未获取成功");
    }

}
