package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSingleCode;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SingleCodePojoTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<IntegralSingleCode> transfer(List<IntegralSingleCodeDomain> singledomains) {

        log.info("SingleCodePojoTransfer transfer singledomains {}", JSONObject.toJSONString(singledomains));
       return singledomains.stream().map(singledomain-> {
           IntegralSingleCode pojo = modelMapper.map(singledomain, IntegralSingleCode.class);
           return pojo;
        }).collect(Collectors.toList());

    }
}
