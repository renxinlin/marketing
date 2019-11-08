package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.domain.constants.MoneyTypeConstant;

import java.util.Random;

/**
 * @author fangshiping
 */
public class MoneyCalculator {

    public double buildRewardMoney(WheelsReward wheelsReward) {
        Random rand = new Random();
        if (wheelsReward.getMoneyType() == MoneyTypeConstant.random) {
            return rand.nextInt((wheelsReward.getRandHighMoney() - wheelsReward.getRandLowMoney() + 1)) + wheelsReward.getRandLowMoney();
        } else {
            return wheelsReward.getFixedMoney();
        }
    }
}
