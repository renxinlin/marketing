package com.jgw.supercodeplatform.marketingsaler.integral.dto;

import com.jgw.supercodeplatform.marketing.dto.integral.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("批量积分奖励规则值对象")
public class BatchSalerRuleRewardDto {
    /** 产品价格 */
    @ApiModelProperty(value = "产品价格",example="10")
    private Float productPrice;

    /** 0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分） */
    @NotNull
    @ApiModelProperty(value = "0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分）",example="0")
    private Byte rewardRule;

    /** 每消费多少元 */
    @ApiModelProperty(value = "每消费多少元",example="10")
    private Float perConsume;

    /** 奖励积分 */
    @NotNull
    @ApiModelProperty(value = "奖励积分",example="10")
    private Integer rewardIntegral;

    @ApiModelProperty(value = "设置的产品集合")
    private List<Product> products;


}

