package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.ApiModelProperty;

public class MarketingActivitySet {



    @ApiModelProperty(name = "id", value = "活动设置主键", example = "1")
    private Long id;

    @ApiModelProperty(name = "activityId", value = "活动主键", example = "1")
    private Long activityId;//活动主键Id
    @ApiModelProperty(name = "activityTitle", value = "活动标题", example = "小活动")
    private String activityTitle;//活动标题

    @ApiModelProperty(name = "activityStartDate", value = "活动开始时间", example = "2017-01-11")
    private String activityStartDate;//活动开始时间

    @ApiModelProperty(name = "activityEndDate", value = "活动结束时间", example = "2217-01-11")
    private String activityEndDate;//活动结束时间

    @ApiModelProperty(name = "eachDayNumber", value = "每人每天次数", example = "2")
    private Integer eachDayNumber;//每人每天次数

    @ApiModelProperty(name = "activityRangeMark", value = "活动范围标志(1、表示部分产品有效 2、表示全部产品有效 ", example = "1")
    private Integer activityRangeMark;//活动范围标志(1、表示部分产品有效 2、表示全部产品有效 )

    @ApiModelProperty(name = "autoFetch", value = "是否自动获取(1、自动获取 2、仅此一次 ) ", example = "1")
    private Integer autoFetch;//是否自动获取(1、自动获取 2、仅此一次 )

    @ApiModelProperty(name = "organizationId", value = "组织Id", example = "111")
     private String organizationId;//组织Id
    @ApiModelProperty(name = "organizatioIdlName", value = "组织", example = "组织")
    private String organizatioIdlName;//组织
     private String updateUserId;//更新用户Id
    private String updateUserName;//更新用户名称
    private String createDate;//创建时间
    private String updateDate;//更新时间
    @ApiModelProperty(name = "activityStatus", value = "活动状态(1、表示上架进展 2、表示下架 ) ", example = "1")
    private Integer activityStatus;//活动状态(1、表示上架进展，0 表示下架)
    @ApiModelProperty(name = "codeTotalNum", value = "参与该活动一共的码数", example = "11")
    private Long codeTotalNum;//参与该活动一共的码数
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public void setEachDayNumber(Integer eachDayNumber) {
		this.eachDayNumber = eachDayNumber;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

    public Integer getEachDayNumber() {
		return eachDayNumber;
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

    public Long getCodeTotalNum() {
		return codeTotalNum;
	}

	public void setCodeTotalNum(Long codeTotalNum) {
		this.codeTotalNum = codeTotalNum;
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
    
}
