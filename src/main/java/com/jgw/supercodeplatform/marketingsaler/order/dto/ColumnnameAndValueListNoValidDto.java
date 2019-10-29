package com.jgw.supercodeplatform.marketingsaler.order.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("订货")
public class ColumnnameAndValueListNoValidDto {
   @Valid
   @NotNull
   List<ColumnnameAndValueNoValidDto> datas;
}
