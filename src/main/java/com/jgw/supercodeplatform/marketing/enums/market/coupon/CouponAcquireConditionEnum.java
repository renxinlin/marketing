package com.jgw.supercodeplatform.marketing.enums.market.coupon;

/**
 * 优惠券获得条件枚举
 */
public enum CouponAcquireConditionEnum {

    /**
     * 首次积分
     */
    FIRST((byte)1,"首次积分"),
    /**
     * 一次积分达到
     */
    ONCE_LIMIT((byte)2,"一次积分达到"),
    /**
     * 累计积分达到
     */
    LIMIT((byte)3,"累计积分达到"),

    /**
     * 购买商品
     */
    SHOPPING((byte)4,"参加获得抵扣券的产品");

    private Byte condition;

    private String desc;

    CouponAcquireConditionEnum(byte condition, String desc){
        this.condition=condition;
        this.desc=desc;

    }
    
	/**
	 * 根据type获得对应的枚举类型
	 * @param type
	 * @return
	 */
	public static CouponAcquireConditionEnum getConditionEnumByType(byte condition) {
		CouponAcquireConditionEnum[] conditionEnums = CouponAcquireConditionEnum.values();
		for(CouponAcquireConditionEnum conditionEnum : conditionEnums) {
			if(conditionEnum.condition.byteValue() == condition)
				return conditionEnum;
		}
		return null;
	}
	
	/**
	 * 根据type获得对应的枚举类型解释
	 * @param type
	 * @return
	 */
	public static String getConditionDescByType(byte type) {
		CouponAcquireConditionEnum conditionEnum = getConditionEnumByType(type);
		if(conditionEnum != null)
			return conditionEnum.desc;
		return null;
	}

	public Byte getCondition() {
		return condition;
	}

	public String getDesc() {
		return desc;
	}

}
