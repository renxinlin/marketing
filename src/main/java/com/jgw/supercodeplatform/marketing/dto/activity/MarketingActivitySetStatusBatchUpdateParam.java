package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "批量更改活动状态")
public class MarketingActivitySetStatusBatchUpdateParam {
    @ApiModelProperty(value = "活动主键Id",required=true)
    private List<Long> activitySetIds;//活动主键Ids

    @ApiModelProperty(value = "活动状态",required=true, notes = "1、表示上架进展，0 表示下架")
    private Integer activityStatus;//活动状态(1、表示上架进展，0 表示下架)
}
