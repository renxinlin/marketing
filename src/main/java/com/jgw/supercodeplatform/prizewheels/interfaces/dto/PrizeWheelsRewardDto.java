package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("H5领奖dto")
public class PrizeWheelsRewardDto {

    @NotNull(message = "活动不存在") @Min(value = 0,message = "活动不存在")
    @ApiModelProperty("大转盘活动id") private Long id;

    @NotEmpty(message = "码信息不存在")
    @ApiModelProperty("外码")   private String outerCodeId;

    @NotEmpty(message = "码信息不存在")
    @ApiModelProperty("码制")  private String codeTypeId;
}
