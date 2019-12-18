package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IntegralRuleRewardTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<IntegralRuleRewardDomian> transferDtoToDomain(List<IntegralRuleRewardDto> ruleRewardDtos, String organizationId, String organizationName) {
        log.info("IntegralRuleRewardTransfer transferDtoToDomain ruleRewardDtos=>{} ",ruleRewardDtos);
        if(CollectionUtils.isEmpty(ruleRewardDtos)){
            return new ArrayList<>();
        }
        return ruleRewardDtos.stream().map(ruleRewardDto->{
            IntegralRuleRewardDomian domain = modelMapper.map(ruleRewardDto, IntegralRuleRewardDomian.class);
            domain.setOrganizationId(organizationId);
            domain.setOrganizationName(organizationName);
            return domain;
        }).collect(Collectors.toList());

    }
}
