package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("活动企业")
public class ActivityOrganizationDataVo {
    @ApiModelProperty("组织ID")
    private String organizationId;
    @ApiModelProperty("组织名称")
    private String organizationFullName;
    @ApiModelProperty("活动参与量")
    private Long activityJoinNum;
}
