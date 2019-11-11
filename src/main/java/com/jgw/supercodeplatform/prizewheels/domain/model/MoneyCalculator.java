package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.domain.constants.MoneyTypeConstant;

import java.math.BigDecimal;
import java.util.Random;

import static com.jgw.supercodeplatform.prizewheels.domain.constants.RewardTypeConstant.money;

/**
 * @author fangshiping
 */
public class MoneyCalculator {

    public static double buildRewardMoney(WheelsReward wheelsReward) {
        Double money = null;
        Random rand = new Random();
        if (wheelsReward.getMoneyType().byteValue() == MoneyTypeConstant.random) {
            // 保留两位
            double tempMoney= rand.nextDouble() * (wheelsReward.getRandHighMoney() - wheelsReward.getRandLowMoney()) + wheelsReward.getRandLowMoney();
            BigDecimal bg = new BigDecimal(tempMoney);
            money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return money;
        } else {
            money = wheelsReward.getFixedMoney();
            BigDecimal bg = new BigDecimal(money.toString());
            // 保留两位
            money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return money;
        }
    }
}
