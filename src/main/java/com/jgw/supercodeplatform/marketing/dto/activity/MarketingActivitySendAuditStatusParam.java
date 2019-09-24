package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("活动红包发送状态")
public class MarketingActivitySendAuditStatusParam {

    @ApiModelProperty(value = "活动主键Id",required=true)
    private Long activitySetId;

    @ApiModelProperty(value = "活动状态<0:不审核，1：需要审核>",required=true)
    private Integer sendAudit;
}
