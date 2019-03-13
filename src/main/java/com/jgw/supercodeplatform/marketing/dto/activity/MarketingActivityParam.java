package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动(*注意是微信红包，砸金蛋等活动实体)model")
public class MarketingActivityParam {
	@ApiModelProperty(name = "id", value = "活动主键", example = "1")
	private Long id;
	
	 @ApiModelProperty(name = "activityName", value = "活动名称", example = "微信红包")
	private String activityName;// 活动名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
}
