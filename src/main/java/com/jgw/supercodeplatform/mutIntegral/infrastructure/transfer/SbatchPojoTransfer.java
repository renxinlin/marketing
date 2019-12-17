package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSbatch;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
@Slf4j
public class SbatchPojoTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<IntegralSbatch> transfer(List<IntegralSbatchDomain> sbatchDomains) {
        log.info("SbatchPojoTransfer transfer sbatchDomains {}", JSONObject.toJSONString(sbatchDomains));
        return sbatchDomains.stream().map(segmentCodeDomain-> {
            IntegralSbatch pojo = modelMapper.map(segmentCodeDomain, IntegralSbatch.class);
            return pojo;
        }).collect(Collectors.toList());
    }



}
