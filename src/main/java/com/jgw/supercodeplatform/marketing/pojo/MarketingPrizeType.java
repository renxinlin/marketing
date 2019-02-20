package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingPrizeType {

    private int id;//序列Id
    private int activityId;//活动Id
    private String prizeTypeCode;//奖品类型编码
    private String prizeTypeName;//奖品类型名称
    private String organizationId;//组织Id
    private String organizationFullName;//组织全名(门店）

    public MarketingPrizeType() {
    }

    public MarketingPrizeType(int id, int activityId, String prizeTypeCode, String prizeTypeName, String organizationId, String organizationFullName) {
        this.id = id;
        this.activityId = activityId;
        this.prizeTypeCode = prizeTypeCode;
        this.prizeTypeName = prizeTypeName;
        this.organizationId = organizationId;
        this.organizationFullName = organizationFullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getPrizeTypeCode() {
        return prizeTypeCode;
    }

    public void setPrizeTypeCode(String prizeTypeCode) {
        this.prizeTypeCode = prizeTypeCode;
    }

    public String getPrizeTypeName() {
        return prizeTypeName;
    }

    public void setPrizeTypeName(String prizeTypeName) {
        this.prizeTypeName = prizeTypeName;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }
}
