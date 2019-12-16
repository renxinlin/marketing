package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardCommonDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRule;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleRewardCommon;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
public class IntegralRulePojoTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<IntegralRuleRewardCommon> transferCommonDomainsToPojos(List<IntegralRuleRewardCommonDomain> integralRuleRewardCommonDomains) {
        List<IntegralRuleRewardCommon> pojos = new ArrayList<>();
        List<IntegralRuleRewardCommon> commonPojos = integralRuleRewardCommonDomains.stream().map(integralRuleRewardCommonDomain -> {
            return modelMapper.map(integralRuleRewardCommonDomain, IntegralRuleRewardCommon.class);
        }).collect(Collectors.toList());
        log.info(" IntegralRulePojoTransfer transferCommonDomainsToPojos  commonPojos => {}",commonPojos);
        return commonPojos == null ? pojos : commonPojos;
    }

    public IntegralRule transferDomainToPojo(IntegralRuleDomain integralRuleDomain) {
        return modelMapper.map(integralRuleDomain, IntegralRule.class);
    }

    public IntegralRuleDomain transferPojoToDomain(IntegralRule integralRule, List<IntegralRuleRewardCommon> commonsRules) {
        log.info("通用积分查询配置 integralRule=>{}, commonsRules=>{}", integralRule, commonsRules);
        IntegralRuleDomain domain = modelMapper.map(integralRule, IntegralRuleDomain.class);

        List<IntegralRuleRewardCommonDomain> integralRuleRewardCommonDomains = commonsRules.stream().map(commonsRule -> {
            IntegralRuleRewardCommonDomain commonDomain = modelMapper.map(commonsRule, IntegralRuleRewardCommonDomain.class);
            return commonDomain;
        }).collect(Collectors.toList());

        domain.setIntegralRuleRewardCommonDomains(integralRuleRewardCommonDomains);
        return domain;
    }
}
