package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动更改状态")
public class MarketingActivitySetStatusUpdateParam {

    @ApiModelProperty(value = "活动主键Id",required=true)
    private Long activitySetId;//活动主键Id

    @ApiModelProperty(value = "活动状态",required=true)
    private Integer activityStatus;//活动状态(1、表示上架进展，0 表示下架)

    public Long getActivitySetId() {
        return activitySetId;
    }

    public void setActivitySetId(Long activitySetId) {
        this.activitySetId = activitySetId;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

}
