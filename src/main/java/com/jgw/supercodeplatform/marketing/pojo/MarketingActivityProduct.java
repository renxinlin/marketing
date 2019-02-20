package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingActivityProduct {

    private int id;//Id
    private int activityId;//活动Id
    private String codeType;//类型
    private String batchId;//批次号
    private int activityProductId;//活动产品Id
    private String activityProductName;//活动产品名称
    private String createDate;//建立日期
    private String UpdateDate;//修改日期


    public MarketingActivityProduct() {
    }

    public MarketingActivityProduct(int id, int activityId, String codeType, String batchId, int activityProductId, String activityProductName, String createDate, String updateDate) {
        this.id = id;
        this.activityId = activityId;
        this.codeType = codeType;
        this.batchId = batchId;
        this.activityProductId = activityProductId;
        this.activityProductName = activityProductName;
        this.createDate = createDate;
        UpdateDate = updateDate;
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

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getActivityProductId() {
        return activityProductId;
    }

    public void setActivityProductId(int activityProductId) {
        this.activityProductId = activityProductId;
    }

    public String getActivityProductName() {
        return activityProductName;
    }

    public void setActivityProductName(String activityProductName) {
        this.activityProductName = activityProductName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }
}
