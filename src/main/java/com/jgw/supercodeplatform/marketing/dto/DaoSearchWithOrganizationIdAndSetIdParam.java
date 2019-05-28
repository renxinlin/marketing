package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("导购中奖记录参数")
public class DaoSearchWithOrganizationIdAndSetIdParam extends DaoSearchWithOrganizationIdParam {
    @ApiModelProperty("活动设置ID")
    private Long id;
}
