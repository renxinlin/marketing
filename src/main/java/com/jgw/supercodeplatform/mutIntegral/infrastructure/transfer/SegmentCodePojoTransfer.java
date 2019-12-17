package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSingleCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SegmentCodePojoTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<IntegralSegmentCode> transfer(List<IntegralSegmentCodeDomain> segmentCodeDomains) {
        log.info("SegmentCodePojoTransfer transfer segmentCodeDomains {}", JSONObject.toJSONString(segmentCodeDomains));
        return segmentCodeDomains.stream().map(segmentCodeDomain-> {
            IntegralSegmentCode pojo = modelMapper.map(segmentCodeDomain, IntegralSegmentCode.class);
            return pojo;
        }).collect(Collectors.toList());


    }
}
