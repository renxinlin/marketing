package com.jgw.supercodeplatform.burypoint.signin.dto.mall;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/21 16:09
 */
@Data
public class BuryPointMallExchangeTcDto {
    @ApiModelProperty(value = "活动ID")
    private String activityId;
    @ApiModelProperty(value = "商品ID")
    private String mallId;
    @ApiModelProperty(value = "商品名称")
    private String mallName;
    @ApiModelProperty(value = "商品兑换的代币值")
    private String goodsCondition;
}
