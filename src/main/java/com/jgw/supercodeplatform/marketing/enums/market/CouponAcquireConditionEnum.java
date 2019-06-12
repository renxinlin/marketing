package com.jgw.supercodeplatform.marketing.enums.market;

public enum CouponAcquireConditionEnum {
	
	FIRST_INTEGRAL(1, "首次积分"), ONECE_ACHIEVE(2, "次积分达到"),
	SUM_INTEGRAL(3, "累计积分达到"), COUPON_PRODUCT(4, "参加获得抵扣券的产品");
	
	private int type;
	
	private String condition;
	
	private CouponAcquireConditionEnum(int type, String condition) {
		this.type = type;
		this.condition = condition;
	}
	
	/**
	 * 根据type获得对应的枚举类型
	 * @param type
	 * @return
	 */
	public static CouponAcquireConditionEnum getConditionEnumByType(int type) {
		CouponAcquireConditionEnum[] conditionEnums = CouponAcquireConditionEnum.values();
		for(CouponAcquireConditionEnum conditionEnum : conditionEnums) {
			if(conditionEnum.type == type)
				return conditionEnum;
		}
		return null;
	}
	
	/**
	 * 根据type获得对应的枚举类型解释
	 * @param type
	 * @return
	 */
	public static String getConditionByType(int type) {
		CouponAcquireConditionEnum conditionEnum = getConditionEnumByType(type);
		if(conditionEnum != null)
			return conditionEnum.condition;
		return null;
	}
	
}
