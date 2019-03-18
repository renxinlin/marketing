package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingOrganizationPortrait {

    private int id;//序列
    private String organizationId;//组织Id
    private String organizationFullName;//组织
    private int fieldWeight;//字段权重 用于控制页面显示顺序
    private Long unitCodeId;//画像编码主键id
    public MarketingOrganizationPortrait() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Long getUnitCodeId() {
		return unitCodeId;
	}

	public void setUnitCodeId(Long unitCodeId) {
		this.unitCodeId = unitCodeId;
	}

	public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public int getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(int fieldWeight) {
        this.fieldWeight = fieldWeight;
    }
}
