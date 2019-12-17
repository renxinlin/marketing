package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSegmentCodeDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SegmentCodeTransfer {

    @Autowired
    private ModelMapper modelMapper;

    public List<IntegralSegmentCodeDomain> transferDtoToDomain(List<IntegralSegmentCodeDto> segmentCodeDtos, String organizationId, String organizationName) {
        log.info("SegmentCodeTransfer transferDtoToDomain segmentCodeDtos=>{} ", JSONObject.toJSONString(segmentCodeDtos));

        return segmentCodeDtos.stream().map(segmentCodeDto->{
            IntegralSegmentCodeDomain domain = modelMapper.map(segmentCodeDto, IntegralSegmentCodeDomain.class);
            domain.setOrganizationId(organizationId);
            domain.setOrganizationName(organizationName);
            return domain;
        }).collect(Collectors.toList());
    }
}
