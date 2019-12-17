package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardCommonDomain;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardCommonDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
public class IntegralRuleTransfer {

    @Autowired
    private ModelMapper mapper;



    /**
     * 通用积分规则聚合根
     * @param integralRuleDto
     * @param organizationId
     * @param organizationName
     * @param accountId 用户iD
     * @param userName  用户名称
     * @return
     */
    public IntegralRuleDomain tranferDtoToDomain(IntegralRuleDto integralRuleDto, String organizationId, String organizationName, String accountId, String userName) {
        IntegralRuleDomain integralRuleDomain = mapper.map(integralRuleDto, IntegralRuleDomain.class);
        List<IntegralRuleRewardCommonDto> integralRuleRewardCommons = integralRuleDto.getIntegralRuleRewardCommons();


        integralRuleDomain.setOrganizationId(organizationId);
        integralRuleDomain.setOrganizationName(organizationName);
        integralRuleDomain.setUpdateDate(new Date());
        integralRuleDomain.setUpdateUserId(accountId);
        integralRuleDomain.setUpdateUserName(userName);


        integralRuleDomain.setIntegralRuleRewardCommonDomains(
                integralRuleRewardCommons.stream().map(integralRuleRewardCommonDto -> {
                    IntegralRuleRewardCommonDomain domain = mapper.map(integralRuleRewardCommonDto, IntegralRuleRewardCommonDomain.class);
                    domain.setOrganizationId(organizationId);
                    domain.setOrganizationName(organizationName);
                    return domain;
                }).collect(Collectors.toList())
        );
        return integralRuleDomain;
    }


    public IntegralRuleDto tranferDomainToDto(IntegralRuleDomain domain) {
        log.info("查询通用积分配置");
        IntegralRuleDto dto = mapper.map(domain, IntegralRuleDto.class);
        if(CollectionUtils.isEmpty(domain.getIntegralRuleRewardCommonDomains())){
            dto.setIntegralRuleRewardCommons(new ArrayList<>());
        }else {
            dto.setIntegralRuleRewardCommons(
                    domain.getIntegralRuleRewardCommonDomains().stream().map(integralRuleRewardCommonDomain -> {
                        IntegralRuleRewardCommonDto commonDtos = mapper.map(integralRuleRewardCommonDomain, IntegralRuleRewardCommonDto.class);
                        return commonDtos;
                    }).collect(Collectors.toList())
            );
        }


        return dto;
    }

    public List<IntegralRuleRewardCommonVo> tranferDtoToView(IntegralRuleDto integralRuleDto) {
        log.info("通用积分列表tranferDtoToView integralRuleDto=>{}",integralRuleDto);
        List<IntegralRuleRewardCommonDto> integralRuleRewardCommons = integralRuleDto.getIntegralRuleRewardCommons();
        if(CollectionUtils.isEmpty(integralRuleRewardCommons)){
            return new ArrayList<>();
        }

        return integralRuleRewardCommons.stream().map(common->{
            IntegralRuleRewardCommonVo vo = mapper.map(common, IntegralRuleRewardCommonVo.class);

            vo.setExpiredDate(integralRuleDto.getExpiredDate());
            vo.setExpiredType(integralRuleDto.getExpiredType());
            vo.setIntegralLimitType(integralRuleDto.getIntegralLimitType());
            vo.setIntegralLimit(integralRuleDto.getIntegralLimit());
            return vo;
        }).collect(Collectors.toList());

    }
}
