package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardTypeConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.http.util.Asserts;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class IntegralRuleRewardDto implements Serializable,Cloneable {


    private Long id;


    @ApiModelProperty("1会员2导购3门店4经销商")
    private Integer integralRuleType;


    @ApiModelProperty("1固定2随机3不送红包")
    private Integer rewardMoneyType;

    @ApiModelProperty("固定金额")
    private Double fixedMoney;

    @ApiModelProperty("随机金额上限")
    private Double highRandomMoney;
    @ApiModelProperty("随机金额下限")
    private Double lowerRandomMoney;


    @ApiModelProperty("经销商级别上级1上上级2以此类推")
    private Integer level;

    @ApiModelProperty("概率")
    private Integer probability;


    @ApiModelProperty("每人每天領取上限")
    private Integer customerLimitNum;

}
