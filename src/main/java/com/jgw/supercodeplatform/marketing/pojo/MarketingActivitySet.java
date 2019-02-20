package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingActivitySet {

    private int activityId;//活动Id
    private String organizationId;//组织Id
    private String organizatioIdlName;//组织
    private String activityTitle;//活动标题
    private String activityStartDate;//活动开始时间
    private String activityEndDate;//活动结束时间
    private int activityProductId;//活动产品Id
    private String activityProductName;//活动产品名称
    private String updateUserId;//更新用户Id
    private String updateUserName;//更新用户名称
    private String updateDate;//更新时间
    private String activityStatus;//活动状态(1、表示上架进展，0 表示下架)
    private int eachDayNumber;//每人每天次数
    private int eachMostNumber;//每人最多获奖次数
    private String activityRangeMark;//活动范围标志(1、表示部分产品有效 2、表示全部产品有效 3、仅使用一次 4、自动获取)

    public MarketingActivitySet() {
    }

    public MarketingActivitySet(int activityId, String organizationId, String organizatioIdlName, String activityTitle, String activityStartDate, String activityEndDate, int activityProductId, String activityProductName, String updateUserId, String updateUserName, String updateDate, String activityStatus, int eachDayNumber, int eachMostNumber, String activityRangeMark) {
        this.activityId = activityId;
        this.organizationId = organizationId;
        this.organizatioIdlName = organizatioIdlName;
        this.activityTitle = activityTitle;
        this.activityStartDate = activityStartDate;
        this.activityEndDate = activityEndDate;
        this.activityProductId = activityProductId;
        this.activityProductName = activityProductName;
        this.updateUserId = updateUserId;
        this.updateUserName = updateUserName;
        this.updateDate = updateDate;
        this.activityStatus = activityStatus;
        this.eachDayNumber = eachDayNumber;
        this.eachMostNumber = eachMostNumber;
        this.activityRangeMark = activityRangeMark;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizatioIdlName() {
        return organizatioIdlName;
    }

    public void setOrganizatioIdlName(String organizatioIdlName) {
        this.organizatioIdlName = organizatioIdlName;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(String activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public String getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(String activityEndDate) {
        this.activityEndDate = activityEndDate;
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

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getEachDayNumber() {
        return eachDayNumber;
    }

    public void setEachDayNumber(int eachDayNumber) {
        this.eachDayNumber = eachDayNumber;
    }

    public int getEachMostNumber() {
        return eachMostNumber;
    }

    public void setEachMostNumber(int eachMostNumber) {
        this.eachMostNumber = eachMostNumber;
    }

    public String getActivityRangeMark() {
        return activityRangeMark;
    }

    public void setActivityRangeMark(String activityRangeMark) {
        this.activityRangeMark = activityRangeMark;
    }
}
