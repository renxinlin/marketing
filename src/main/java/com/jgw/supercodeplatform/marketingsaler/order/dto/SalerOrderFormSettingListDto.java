package com.jgw.supercodeplatform.marketingsaler.order.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("表单集合")
public class SalerOrderFormSettingListDto {
    List<SalerOrderFormSettingDto> salerOrderFormSettingDto;
}
