package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSingleCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SingleCodeTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<IntegralSingleCodeDomain> transferDtoToDomain(List<IntegralSingleCodeDto> singleCodeDtos, String organizationId, String organizationName) {
        log.info("SingleCodeTransfer transferDtoToDomain singleCodeDtos=>{} ", JSONObject.toJSONString(singleCodeDtos));

        return singleCodeDtos.stream().map(singleCodeDto->{
            IntegralSingleCodeDomain domain = modelMapper.map(singleCodeDto, IntegralSingleCodeDomain.class);
            domain.setOrganizationId(organizationId);
            domain.setOrganizationName(organizationName);
            return domain;
        }).collect(Collectors.toList());

    }
}
