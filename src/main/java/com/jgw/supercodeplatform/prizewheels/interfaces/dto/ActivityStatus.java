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
@ApiModel("活动停用启用")
public class ActivityStatus {



    @NotNull(message = "活动不存在") @Min(value = 0,message = "活动不存在")
    @ApiModelProperty("大转盘活动id")private Long id;
    @NotEmpty(message = "状态不合法")
    @ApiModelProperty("活动状态(1、表示上架进展，0 表示下架)") private String status;
}
