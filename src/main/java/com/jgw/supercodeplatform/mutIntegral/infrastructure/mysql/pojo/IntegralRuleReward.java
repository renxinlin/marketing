package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

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
@TableName("market_new_integral_rule_reward")
public class IntegralRuleReward implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 主表主键
     */
    @TableField("IntegralRuleId")
    private Long integralRuleId;

    /**
     * 1会员
     * 2导购
     * 3门店
     * 4经销商
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

    /**
     * 经销商级别上级1上上级2以此类推
     */
    @TableField("Level")
    private Integer level;


    /**
     * 概率
     */
    @TableField("Probability")
    private Integer probability;


    /**
     * 每人每天領取上限
     */
    @TableField("CustomerLimitNum")
    private Integer customerLimitNum;

    @TableField("OrganizationId")
    private String organizationId;
    @TableField("OrganizationName")
    private String organizationName;
    /**
     * 类型1积分2 红包
     */
    @TableField("UnRewardFlag")
    private Integer rewardType;
    /**
     * 红包未中奖标志:1中奖2未中奖
     */
    @TableField("UnRewardFlag")
    private Integer  unRewardFlag;

}
