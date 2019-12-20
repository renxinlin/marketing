package com.jgw.supercodeplatform.mutIntegral.infrastructure.constants;

public interface RewardMonryTypeConstants {
    // 1固定2随机3不送红包
    int fixed = 1;
    int random = 2;
    int nosend = 3;
    String fixedError = "固定金额填写错误";
    String lowerRandomMoneyError = "随机金额下限填写错误";
    String highRandomMoneyError = "随机金额上限填写错误" ;
    String highShouldPlusError = "金额上限应该大于下限";

}
