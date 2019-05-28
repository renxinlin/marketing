package com.jgw.supercodeplatform.marketing.enums.market;

/**
 * 积分原因枚举
 */
public enum IntegralReasonEnum {
    REGISTER_MEMBER(1,"招募会员"),
    BIRTHDAY(2,"生日"),
    FIRST_INTEGRAL(3,"首次积分"),
    PRODUCT_INTEGRAL(4,"产品积分"),
    EXCHANGE_PRODUCT(5,"兑换商品"),
    SALER_NO_CONDITION(6,"无条件"),
    SALER_ACTIVITY(7,"协助会员抽奖"),
    SALER_INTEGRAL(8,"协助会员领积分");

    IntegralReasonEnum(Integer integralReasonCode, String integralReason) {
        this.integralReasonCode = integralReasonCode;
        this.integralReason = integralReason;
    }

    private Integer integralReasonCode;
    private String integralReason;

    public Integer getIntegralReasonCode() {
        return integralReasonCode;
    }

    public void setIntegralReasonCode(Integer integralReasonCode) {
        this.integralReasonCode = integralReasonCode;
    }

    public String getIntegralReason() {
        return integralReason;
    }

    public void setIntegralReason(String integralReason) {
        this.integralReason = integralReason;
    }}
