package com.jgw.supercodeplatform.marketingsaler.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("订货")
@AllArgsConstructor
@NoArgsConstructor
public class ColumnnameAndValueDto {
    @NotEmpty(message = "订货字段不可为空")
    @ApiModelProperty("订货字段")
    String columnName;
    @NotEmpty(message = "订货字段值不可为空")
    @ApiModelProperty("订货字段值")
    String columnValue;

    Integer status;
}
