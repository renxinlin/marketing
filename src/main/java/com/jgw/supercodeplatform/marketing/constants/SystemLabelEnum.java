package com.jgw.supercodeplatform.marketing.constants;

import org.apache.commons.lang.StringUtils;

public enum SystemLabelEnum {
	FAKE("031001",21,21L,"防伪码"),
	LOGISTICES("031002",20,20L,"物流码"),
	TRACE("031003",18,15L,"溯源码"),
	MARKETING_12("031004",17,12L,"17位营销码"),
	MARKETING_13("031005",19,13L,"19位营销码");

	private String label;
	private Integer codeLength;
	private Long codeTypeId;
	private String businessName;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getCodeLength() {
		return codeLength;
	}
	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}


	public Long getCodeTypeId() {
		return codeTypeId;
	}
	public void setCodeTypeId(Long codeTypeId) {
		this.codeTypeId = codeTypeId;
	}
	SystemLabelEnum(String label, Integer codeLength, Long codeTypeId, String businessName) {
		this.label = label;
		this.codeLength = codeLength;
		this.codeTypeId = codeTypeId;
		this.businessName = businessName;
	}
	public static SystemLabelEnum getByLabel(String label) {
		if (StringUtils.isBlank(label)) {
			return null;
		}
		switch (label) {
		case "031001":
			return FAKE;
		case "031002":

			return LOGISTICES;
		case "031003":

			return TRACE;
		case "031004":
			return MARKETING_12;
		case "031005":
				return MARKETING_13;
		default:
			return null;
		}
	}

	public static SystemLabelEnum getByCodeLen(Integer codeLength) {
		if (null==codeLength) {
			return null;
		}
		switch (codeLength) {
		case 21:
			return FAKE;
		case 20:
			return LOGISTICES;
		case 18:
			return TRACE;
		case 17:
			return MARKETING_12;
		case 19:
			return MARKETING_13;

		default:
			return null;
		}
	}

	public static SystemLabelEnum getByCodeTypeId(Long codeTypeId) {

		if (null==codeTypeId) {
			return null;
		}
		int intTypeId=codeTypeId.intValue();
		switch (intTypeId) {
		case 21:
			return FAKE;
		case 20:
			return LOGISTICES;
		case 15:
			return TRACE;
		case 12:
			return MARKETING_12;
		case 13:
			return MARKETING_13;
		default:
			return null;
		}
	}
}
