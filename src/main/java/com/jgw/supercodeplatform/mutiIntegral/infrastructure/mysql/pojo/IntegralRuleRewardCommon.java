package com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

  import   com.baomidou.mybatisplus.annotation.TableName;
  import   com.baomidou.mybatisplus.annotation.IdType;
  import   com.baomidou.mybatisplus.annotation.TableId;
  import   com.baomidou.mybatisplus.annotation.TableField;
  import   java.io.Serializable;

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
@TableName("market_new_integral_rule_reward_common")
public class IntegralRuleRewardCommon implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 主表主键
     */
    @TableField("IntegralRuleId")
    private Long integralRuleId;

    /**
     * 1新会员注册
2生日当天首次领取积分，额外送
3历史首次领取积分，额外送
4会员拉新，新会员送
5会员拉新，老会员送






     */
    @TableField("IntegralRuleType")
    private Integer integralRuleType;

    /**
     * 1固定2随机3不送红包
     */
    @TableField("RewardMoneyType")
    private Integer rewardMoneyType;

    /**
     * 固定金额
     */
    @TableField("FixedMoney")
    private Double fixedMoney;

    /**
     * 随机金额上限
     */
    @TableField("HighRandomMoney")
    private Double highRandomMoney;

    /**
     * 随机金额下限
     */
    @TableField("LowerRandomMoney")
    private Double lowerRandomMoney;

    /**
     * 1已选择送积分2未选择积分送
     */
    @TableField("ChooseedIntegral")
    private Integer chooseedIntegral;

    @TableField("SendIntegral")
    private Integer sendIntegral;


}
