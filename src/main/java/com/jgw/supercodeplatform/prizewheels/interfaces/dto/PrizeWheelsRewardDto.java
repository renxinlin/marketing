package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("H5领奖dto")
public class PrizeWheelsRewardDto {
    @ApiModelProperty("大转盘活动id")
    private Long id;

    @ApiModelProperty("外码")
    private String outerCodeId;

    @ApiModelProperty("码制")
    private String codeTypeId;
}
