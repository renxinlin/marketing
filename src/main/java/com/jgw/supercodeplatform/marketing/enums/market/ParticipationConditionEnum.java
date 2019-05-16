package com.jgw.supercodeplatform.marketing.enums.market;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public enum ParticipationConditionEnum {

    /**
     * 无条件
     */
    NO_CONDITION((byte)0,"无条件"),
    HELP_FOR_ACTIVITY((byte)1,"协助会员抽奖"),
    HELP_FOR_INTEGRAL((byte)2,"协助会员领红包");

    @Getter
    @Setter
    private Byte condition;

    @Getter
    @Setter
    private String desc;

    ParticipationConditionEnum( Byte condition,String desc){

    }

}
