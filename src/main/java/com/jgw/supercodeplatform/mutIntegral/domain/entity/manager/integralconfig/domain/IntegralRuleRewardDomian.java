package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardTypeConstants;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.http.util.Asserts;

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
public class IntegralRuleRewardDomian implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 主表主键
     */
    private Long integralRuleId;

    /**
     * 1会员
     * 2导购
     * 3门店
     * 4经销商
     */
    private Integer integralRuleType;

    /**
     * 1固定2随机3不送红包
     */
    private Integer rewardMoneyType;

    /**
     * 固定金额
     */
    private Double fixedMoney;

    /**
     * 随机金额上限
     */
    private Double highRandomMoney;

    /**
     * 随机金额下限
     */
    private Double lowerRandomMoney;

    /**
     * 1已选择送积分2未选择积分送
     */
    private Integer chooseedIntegral;

    private Integer sendIntegral;

    /**
     * 经销商级别上级1上上级2以此类推
     */
    private Integer level;


    /**
     * 概率
     */
    private double probability;


    /**
     * 每人每天領取上限
     */
    private Integer customerLimitNum;

    private String organizationId;
    private String organizationName;
    /**
     * 类型1积分2 红包
     */
    private Integer rewardType;
    /**
     * 红包未中奖标志:1中奖2未中奖
     */
    private Integer  unRewardFlag;


    /**
     * 未中奖概率是100-中奖概率;
     * 中奖概率是概率中RewardMoneyType!=3 de的概率和
     * @param unRewardprobability
     * @return
     */
    public IntegralRuleRewardDomian cloneUnrewardInfo(double unRewardprobability ){
        Asserts.check(unRewardprobability >=MutiIntegralCommonConstants.emptyProbability && unRewardprobability<=MutiIntegralCommonConstants.fullProbability , MutiIntegralCommonConstants.RewardprobabilityError);
            IntegralRuleRewardDomian unreward = new IntegralRuleRewardDomian();
            // 设置未中奖信息的属性，包括  未中獎只需要关注概率 红包未中奖标志:1中奖2未中奖
            unreward.rewardType = RewardTypeConstants.reward_money;
            unreward.customerLimitNum = MutiIntegralCommonConstants.ZERO;
            unreward.probability =unRewardprobability;
            unreward.unRewardFlag =RewardTypeConstants.unrewardFlag;
            return  unreward;
    }


}
