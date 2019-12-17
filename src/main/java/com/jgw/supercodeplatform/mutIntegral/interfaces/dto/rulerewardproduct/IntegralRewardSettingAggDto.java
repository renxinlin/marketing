package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("通用积分奖励产品,,,单码设置")
public class IntegralRewardSettingAggDto {

    @ApiModelProperty("单码设置")
    private List<IntegralSingleCodeDto> singleCodeDtos;

    @ApiModelProperty("号段码设置")
    private List<IntegralSegmentCodeDto> segmentCodeDtos;

    @ApiModelProperty("批次码设置")
    private List<IntegralSbatchDto> sbatchDtos;

    @ApiModelProperty("码关联产品设置")
    private IntegralProductAggDto productAggDto;
}
