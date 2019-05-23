package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "导购员活动编辑model")
public class MarketingActivitySalerSetUpdateParam extends MarketingActivitySalerSetAddParam {
	@ApiModelProperty(name = "id", value = "活动设置主键", example = "1")
    private Long id;

}
