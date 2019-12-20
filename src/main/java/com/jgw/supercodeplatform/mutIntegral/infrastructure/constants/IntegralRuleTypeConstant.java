package com.jgw.supercodeplatform.mutIntegral.infrastructure.constants;

import io.swagger.annotations.ApiModelProperty;

public interface IntegralRuleTypeConstant {


    /**
     * 1新会员注册
     */
    Integer newRegister = 1;     String newRegisterStr = "新会员注册";
    /**
     * 生日当天首次领取积分，额外送
     */
    Integer birthDaySend = 2;     String birthDaySendStr = "生日当天首次送";
    /**
     * 历史首次领取积分，额外送
     */
    Integer firstSend = 3;     String firstSendStr = "历史首次送";
    /**
     * 会员拉新，新会员送
     */
    Integer rewardNew = 4;     String rewardNewStr = " 会员拉新，老会员送";

    /**
     * 会员拉新，老会员送
     */
    Integer rewardOld = 5;    String rewardOldStr = " 会员拉新，老会员送";

}
