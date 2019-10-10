package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 大转盘网页对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WheelsDto {

    @NotNull(message = "参加活动的产品不存在")
    @Valid
    private List<ProductDto> productDtos;


    private List<WheelsRewardDto> wheelsRewardDtos;
}
