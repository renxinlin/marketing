package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("兑换VO值对象")
public class PrizeRulesVo implements Serializable {

    @NotNull
    @Min(value = 0,message = "是否随机金额，1是 0不是")
    @Max(value = 1,message = "是否随机金额，1是 0不是")
    @ApiModelProperty("是否随机金额，1是 0不是")
    private Integer isRrandomMoney;

    /**
     * 中奖金额随机下限
     */
    @ApiModelProperty("中奖金额随机下限")
    private Float lowRand;

    /**
     * 中奖金额随机上限
     */
    @ApiModelProperty("中奖金额随机上限")
    private Float highRand;


    /**
     * 中奖金额
     */
    @ApiModelProperty("中奖金额")
    private Float prizeAmount;


}


