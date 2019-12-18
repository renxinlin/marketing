package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IntegralRuleRewardAggDto implements Serializable,Cloneable {

    @ApiModelProperty("红包信息")
    private List<IntegralRuleRewardDto> integralRuleRewardDtos;


    @ApiModelProperty("赠送积分数值")
    private Integer sendIntegral;


    @ApiModelProperty("1已选择送积分2未选择积分送")
    private Integer chooseedIntegral;


    @ApiModelProperty("1会员2导购3门店4经销商")
    private Integer integralRuleType;

}
