package com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("通用积分奖励产品,,,单码设置")
@AllArgsConstructor
@NoArgsConstructor
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
