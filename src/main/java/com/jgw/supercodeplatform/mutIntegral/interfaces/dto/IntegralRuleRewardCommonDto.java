package com.jgw.supercodeplatform.mutIntegral.interfaces.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel("积分通用规则")
public class IntegralRuleRewardCommonDto implements Serializable {


    private Long id;

    /**
     * 主表主键
     */
    @TableField("IntegralRuleId")
    private Long integralRuleId;

    @NotNull(message = "通用积分类型不合法")
    @Range(min = 1,max = 5,message = "通用积分类型不合法")
    @ApiModelProperty(
            "1新会员注册" +
            "2生日当天首次领取积分，额外送" +
            "3历史首次领取积分，额外送" +
            "4会员拉新，新会员送" +
            "5会员拉新，老会员送")
    private Integer integralRuleType;

    @NotNull(message = "红包类型不合法")
    @Range(min = 1,max = 3,message = "红包类型不合法")
    @ApiModelProperty("1固定2随机3不送红包")
    private Integer rewardMoneyType;

    @ApiModelProperty("固定金额")
    @Min(value = 0,message = "金额必须大于0")
    private Double fixedMoney;

    @ApiModelProperty("随机金额上限")
    @Min(value = 0,message = "随机金额下限不合法")
    private Double highRandomMoney;

    /**
     *
     */
    @ApiModelProperty("随机金额下限")
    @Min(value = 0,message = "随机金额下限不合法")
    private Double lowerRandomMoney;

    /**
     *
     */
    @ApiModelProperty("1已选择送积分2未选择积分送")
    @NotNull(message = "积分送类型不合法")
    @Range(min = 1,max = 2,message = "积分送类型不合法")
    private Integer chooseedIntegral;

    @ApiModelProperty("送积分数值")
    @Min(value = 0,message = "送积分数值必须大于0")
    private Integer sendIntegral;


}
