package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动设置model")
public class MarketingActivitySetParam {
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

	public void setActivityEndDate(String activityEndDate) {
		this.activityEndDate = activityEndDate;
	}
    
}