package com.jgw.supercodeplatform.marketing.constants;

public enum BusinessTypeEnum {
	INTEGRAL(1, "积分"), TRACE(2, "溯源"), FAKE(3, "防伪"), LOGISTICS(4, "物流"), MARKETING_ACTIVITY(5, "营销活动");

	private Integer businessType;

	private String businessName;

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	private BusinessTypeEnum(Integer businessType, String businessName) {
		this.businessType = businessType;
		this.businessName = businessName;
	}

}
