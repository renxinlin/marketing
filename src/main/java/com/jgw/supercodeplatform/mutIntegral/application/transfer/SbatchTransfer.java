package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSbatchDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SbatchTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<IntegralSbatchDomain> transferDtoToDomain(List<IntegralSbatchDto> sbatchDtos, String organizationId, String organizationName) {
        log.info("SbatchTransfer transferDtoToDomain sbatchDtos=>{} ", JSONObject.toJSONString(sbatchDtos));

        return sbatchDtos.stream().map(sbatchDto->{
            IntegralSbatchDomain domain = modelMapper.map(sbatchDto, IntegralSbatchDomain.class);
            domain.setOrganizationId(organizationId);
            domain.setOrganizationName(organizationName);
            return domain;
        }).collect(Collectors.toList());
    }
}
