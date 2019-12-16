package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.ChooseedIntegralConsants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardMonryTypeConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class IntegralRuleRewardCommonDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 主表主键
     */
    private Long integralRuleId;

    /**
     * 1新会员注册
     * 2生日当天首次领取积分，额外送
     * 3历史首次领取积分，额外送
     * 4会员拉新，新会员送
     * 5会员拉新，老会员送
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

     private String organizationId;

    /**
     * 组织名称
     */
     private String organizationName;


    /**
     * 通用积分设置检验
     */
    public void checkWhenSetting() {
        log.info("IntegralRuleRewardCommonDomain.checkWhenSetting => {}",this);
        checkMoney();
        checkIntegral();
    }

    private void checkIntegral() {
        if(chooseedIntegral == ChooseedIntegralConsants.choose){
            Asserts.check(chooseedIntegral > 0,ChooseedIntegralConsants.chooseError);

        }
    }

    private void checkMoney() {
        if(rewardMoneyType == RewardMonryTypeConstants.fixed){
            Asserts.check(fixedMoney != null && fixedMoney > 0,RewardMonryTypeConstants.fixedError);

        }

        if(rewardMoneyType == RewardMonryTypeConstants.random){
            Asserts.check(lowerRandomMoney != null && lowerRandomMoney > 0,RewardMonryTypeConstants.lowerRandomMoneyError);
            Asserts.check(highRandomMoney != null && highRandomMoney > 0,RewardMonryTypeConstants.highRandomMoneyError);
            Asserts.check(highRandomMoney > lowerRandomMoney,RewardMonryTypeConstants.highShouldPlusError);

        }
    }
}
