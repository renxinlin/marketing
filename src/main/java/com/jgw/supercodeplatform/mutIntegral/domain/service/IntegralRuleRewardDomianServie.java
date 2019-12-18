package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntegralRuleRewardDomianServie {
    public void checkMoney(List<IntegralRuleRewardDomian> ruleRewardDomains) {

    }

    public void checkIntegral(List<IntegralRuleRewardDomian> ruleRewardDomains) {
    }

    public int getUnrewardProbability(List<IntegralRuleRewardDomian> ruleRewardDomains) {
     //   ruleRewardDomains.stream().filter(ruleRewardDomain->ruleRewardDomain.g)
        return 0;
    }
}
