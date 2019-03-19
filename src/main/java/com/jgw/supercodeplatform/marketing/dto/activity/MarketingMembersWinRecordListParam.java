package com.jgw.supercodeplatform.marketing.dto.activity;


import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value = "中奖纪录列表model")
public class MarketingMembersWinRecordListParam extends DaoSearch{
	@ApiModelProperty(name = "search", value = "组织id后台获取不需前端传递", example = "dsad",hidden=true)
	private String organizationId;


	@ApiModelProperty(name = "activitySetId", value = "活动设置id", example = "12",dataType="long")
	private Long activitySetId;

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}

	public Long getActivitySetId() {
		return activitySetId;
	}
}
