package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingActivityProduct {

    private int id;//Id
    private int activityId2;//活动Id
    private String codeType;//类型
    private String batchId;//批次号
    private String startCode;//开始号码端
    private String endCode;//结束号码端
    private String outCode;//码

    public MarketingActivityProduct() {
    }

    public MarketingActivityProduct(int id, int activityId2, String codeType, String batchId, String startCode, String endCode, String outCode) {
        this.id = id;
        this.activityId2 = activityId2;
        this.codeType = codeType;
        this.batchId = batchId;
        this.startCode = startCode;
        this.endCode = endCode;
        this.outCode = outCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId2() {
        return activityId2;
    }

    public void setActivityId2(int activityId2) {
        this.activityId2 = activityId2;
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

    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    public String getEndCode() {
        return endCode;
    }

    public void setEndCode(String endCode) {
        this.endCode = endCode;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }
}
