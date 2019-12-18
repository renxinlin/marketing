package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardMonryTypeConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardTypeConstants;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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



    public void buildUnRewardInfo(List<List<IntegralRuleRewardDomian>> ruleRewardDomainWithTypes) {
        log.info("IntegralRuleRewardDomianServie buildUnRewardInfo ruleRewardDomainWithTypes{}", JSONObject.toJSONString(ruleRewardDomainWithTypes));
        for(List<IntegralRuleRewardDomian>ruleRewardDomains : ruleRewardDomainWithTypes){
            if(CollectionUtils.isEmpty(ruleRewardDomains)){continue;}
            for(IntegralRuleRewardDomian ruleRewardDomain: ruleRewardDomains){
                if(ruleRewardDomain.getRewardType() == RewardTypeConstants.reward_money){
                    // 随机金额


                }
            }
        }
    }
}
