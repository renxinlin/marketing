package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardMonryTypeConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardTypeConstants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class IntegralRuleRewardDomianServie {
    public void checkMoney(List<List<IntegralRuleRewardDomian>> ruleRewardDomainWithTypes) {
        log.info("IntegralRuleRewardDomianServie checkMoney ruleRewardDomainWithTypes{}", JSONObject.toJSONString(ruleRewardDomainWithTypes));
        for(List<IntegralRuleRewardDomian>ruleRewardDomains : ruleRewardDomainWithTypes){
            if(CollectionUtils.isEmpty(ruleRewardDomains)){continue;}
            for(IntegralRuleRewardDomian ruleRewardDomain: ruleRewardDomains){
                if(ruleRewardDomain.getRewardType() == RewardTypeConstants.reward_money){
                    // 随机金额
                    if( ruleRewardDomain.getRewardMoneyType()== RewardMonryTypeConstants.random){
                        Asserts.check(ruleRewardDomain.getHighRandomMoney() !=null
                                &&ruleRewardDomain.getHighRandomMoney() >=0,"随机金额数值必填");
                        Asserts.check(ruleRewardDomain.getLowerRandomMoney() !=null
                                &&ruleRewardDomain.getLowerRandomMoney() >=0,"随机金额数值必填...");
                        Asserts.check(ruleRewardDomain.getHighRandomMoney()
                                >=ruleRewardDomain.getLowerRandomMoney()  ,"随机金额需上限大于下限");
                    }

                }
            }
        }

    }


    /**
     * 将红包的未中奖信息补充上
     *
     * @param ruleRewardDomainWithTypes 代码搞了一半产品改动导致积分和红包耦合在了一起，这里同时包含积分和monry
     */
    public List<List<IntegralRuleRewardDomian>> buildUnRewardInfo(List<List<IntegralRuleRewardDomian>> ruleRewardDomainWithTypes) {
        log.info("IntegralRuleRewardDomianServie buildUnRewardInfo ruleRewardDomainWithTypes{}", JSONObject.toJSONString(ruleRewardDomainWithTypes));
        for(List<IntegralRuleRewardDomian>ruleRewardDomains : ruleRewardDomainWithTypes){
            if(CollectionUtils.isEmpty(ruleRewardDomains)){continue;}
            double totalProbability = 0D;
            boolean haveMoneyReward = false;
            for(IntegralRuleRewardDomian ruleRewardDomain: ruleRewardDomains){
                if(ruleRewardDomain.getRewardType() == RewardTypeConstants.reward_money){
                    totalProbability += ruleRewardDomain.getProbability();
                }
                haveMoneyReward = true;
            }
            // 存在红包则构建未中奖信息
            if(haveMoneyReward){
                IntegralRuleRewardDomian unreward = new IntegralRuleRewardDomian();
                unreward = unreward.cloneUnrewardInfo(MutiIntegralCommonConstants.fullProbability - totalProbability);
                ruleRewardDomains.add(unreward);
            }

        }
        return ruleRewardDomainWithTypes;
    }












    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////测试////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void test () {
        IntegralRuleRewardDomianServie integralRuleRewardDomianServie = new IntegralRuleRewardDomianServie();
        List<List<IntegralRuleRewardDomian>> data = new ArrayList<>();
        List<IntegralRuleRewardDomian> test1 = new ArrayList<>();
        IntegralRuleRewardDomian data1 = new IntegralRuleRewardDomian();
        data1.setRewardType(RewardTypeConstants.reward_money);
        data1.setProbability(110);

        IntegralRuleRewardDomian data2 = new IntegralRuleRewardDomian();
        data2.setRewardType(RewardTypeConstants.reward_integral);
        test1.add(data1);
        test1.add(data2);
        data.add(test1);
        integralRuleRewardDomianServie.buildUnRewardInfo(data);;
    }

    /**
     * 经销商层级不可重复 ，如果是经销商则存在层级信息
     * @param ruleRewardDomains
     */
    public void checkdealer(List<List<IntegralRuleRewardDomian>> ruleRewardDomains) {
    }
}
