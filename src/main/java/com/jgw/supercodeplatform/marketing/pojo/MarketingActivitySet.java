package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingActivitySet {

    private int activityId;//活动主键Id
    private String organizationId;//组织Id
    private String organizatioIdlName;//组织
    private String activityTitle;//活动标题
    private String activityStartDate;//活动开始时间
    private String activityEndDate;//活动结束时间
    private String updateUserId;//更新用户Id
    private String updateUserName;//更新用户名称
    private String updateDate;//更新时间
    private Integer activityStatus;//活动状态(1、表示上架进展，0 表示下架)
    private int eachDayNumber;//每人每天次数
    private int eachMostNumber;//每人最多获奖次数
    private Integer activityRangeMark;//活动范围标志(1、表示部分产品有效 2、表示全部产品有效 3、仅使用一次 4、自动获取)
    private Integer autoFetch;//是否自动获取(1、自动获取 2、仅此一次 )
    private Integer typeId;//在统一编码表里设置的活动类型

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

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
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

    public Integer getActivityRangeMark() {
        return activityRangeMark;
    }

    public void setActivityRangeMark(Integer activityRangeMark) {
        this.activityRangeMark = activityRangeMark;
    }

	public Integer getAutoFetch() {
		return autoFetch;
	}

	public void setAutoFetch(Integer autoFetch) {
		this.autoFetch = autoFetch;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
    
}
