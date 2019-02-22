package com.jgw.supercodeplatform.marketing.common.model.base;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动model")
public class MarketingActivityParam {
	
	@ApiModelProperty(value = "活动id")
    private Integer activityId;//活动Id
	
	@ApiModelProperty(value = "活动标题")
    private String activityTitle;//活动标题
	
	@ApiModelProperty(value = "活动开始时间")
    private String activityStartDate;//活动开始时间
	
	@ApiModelProperty(value = "活动结束时间")
    private String activityEndDate;//活动结束时间
	
	@ApiModelProperty(value = "活动结束时间")
    private Integer eachDayNumber;//每人每天次数
	
	@ApiModelProperty(value = "每人最多获奖次数")
    private Integer eachMostNumber;//每人最多获奖次数
	
	@ApiModelProperty(value = "活动范围标志(1、表示部分产品有效 2、表示全部产品有效)")
    private Integer activityRangeMark;//活动范围标志(1、表示部分产品有效 2、表示全部产品有效)

	@ApiModelProperty(value = "是否自动获取(1、自动获取 2、仅此一次 )")
    private Integer autoFetch;
	
	@ApiModelProperty(value = "领取页参数")
    private MarketingTemplateParam mTemplateParam;
	
	@ApiModelProperty(value = "中奖页页参数")
    private MarketingWinningPageParam mWinningPageParam;
	
	@ApiModelProperty(value = "活动设置产品参数")
	private List<MarketingActivityProductParam> mProductParams;
	
	@ApiModelProperty(value = "活动设置中奖奖次")
	private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;
	
	//TODO 活动渠道暂时省略待基础平台定义好再说
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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


    public Integer getEachDayNumber() {
        return eachDayNumber;
    }

    public void setEachDayNumber(Integer eachDayNumber) {
        this.eachDayNumber = eachDayNumber;
    }

    public Integer getEachMostNumber() {
        return eachMostNumber;
    }

    public void setEachMostNumber(Integer eachMostNumber) {
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

	public MarketingTemplateParam getmTemplateParam() {
		return mTemplateParam;
	}

	public void setmTemplateParam(MarketingTemplateParam mTemplateParam) {
		this.mTemplateParam = mTemplateParam;
	}

	public MarketingWinningPageParam getmWinningPageParam() {
		return mWinningPageParam;
	}

	public void setmWinningPageParam(MarketingWinningPageParam mWinningPageParam) {
		this.mWinningPageParam = mWinningPageParam;
	}

	public List<MarketingActivityProductParam> getmProductParams() {
		return mProductParams;
	}

	public void setmProductParams(List<MarketingActivityProductParam> mProductParams) {
		this.mProductParams = mProductParams;
	}

	public List<MarketingPrizeTypeParam> getMarketingPrizeTypeParams() {
		return marketingPrizeTypeParams;
	}

	public void setMarketingPrizeTypeParams(List<MarketingPrizeTypeParam> marketingPrizeTypeParams) {
		this.marketingPrizeTypeParams = marketingPrizeTypeParams;
	}
    
    
}
