package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    private Integer probability;


    /**
     * 每人每天領取上限
     */
    private Integer customerLimitNum;


}
