package com.jgw.supercodeplatform.mutIntegral.infrastructure.constants;

public interface RewardTypeConstants {

    int reward_money= 2;
    int reward_integral= 1;

    /**
     * 红包奖项标志:未中奖
     */
    int rewardFlag = 1;
    /**
     * 奖项标志:未中奖
     */
    int unrewardFlag = 2;

    String cloneError = "未中奖必须从中奖信息中copy";
}
