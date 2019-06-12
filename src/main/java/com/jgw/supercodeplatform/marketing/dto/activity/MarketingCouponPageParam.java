package com.jgw.supercodeplatform.marketing.dto.activity;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("核销分页查询")
public class MarketingCouponPageParam extends DaoSearch {

    @ApiModelProperty(hidden = true)
    private String organizationId;
    @ApiModelProperty("活动ID")
    private Long activitySetId;
    @ApiModelProperty(hidden = true)
    private Byte used;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

	public Long getActivitySetId() {
		return activitySetId;
	}

	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}

	public Byte getUsed() {
		return used;
	}

	public void setUsed(Byte used) {
		this.used = used;
	}
}
