package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("H5领奖dto")
public class PrizeWheelsRewardDto {
    private Long id;


    private String outerCodeId;


    private String codeTypeId;
}
