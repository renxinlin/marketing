package com.jgw.supercodeplatform.marketing.enums.market;

public enum CouponVerifyEnum {
	
	
	SALER((byte)1, "导购人员");
	
	private byte type;
	
	private String condition;
	
	private CouponVerifyEnum(byte type, String condition) {
		this.type = type;
		this.condition = condition;
	}
	
	/**
	 * 根据type获得对应的枚举类型
	 * @param type
	 * @return
	 */
	public static CouponVerifyEnum getConditionEnumByType(byte type) {
		CouponVerifyEnum[] conditionEnums = CouponVerifyEnum.values();
		for(CouponVerifyEnum conditionEnum : conditionEnums) {
			if(conditionEnum.type == type) {
                return conditionEnum;
            }
		}
		return null;
	}
	
	/**
	 * 根据type获得对应的枚举类型解释
	 * @param type
	 * @return
	 */
	public static String getConditionByType(byte type) {
		CouponVerifyEnum conditionEnum = getConditionEnumByType(type);
		if(conditionEnum != null) {
            return conditionEnum.condition;
        }
		return null;
	}

	public byte getType() {
		return type;
	}

	public String getCondition() {
		return condition;
	}

}
