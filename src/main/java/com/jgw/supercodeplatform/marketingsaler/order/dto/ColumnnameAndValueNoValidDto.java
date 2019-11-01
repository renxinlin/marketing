package com.jgw.supercodeplatform.marketingsaler.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("订货")
@AllArgsConstructor
@NoArgsConstructor
public class ColumnnameAndValueNoValidDto {
    @ApiModelProperty("订货字段")
    String columnName;
    @ApiModelProperty("订货字段值")
    String columnValue;
}
