package com.jgw.supercodeplatform.marketing.dto.activity;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动列表model")
public class MarketingActivityListParam  extends DaoSearch{
    @ApiModelProperty(name = "search", value = "组织id后台获取不需前端传递", example = "dsad",hidden=true)
    private String organizationId;
    @ApiModelProperty(name = "otherCondition", value = "额外拼接的条件SQL", hidden = true)
    private String otherCondition;
    
	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOtherCondition() {
		return otherCondition;
	}

	public void setOtherCondition(String otherCondition) {
		this.otherCondition = otherCondition;
	}
}
