package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralProduct;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class MutiProductPojoTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<IntegralProduct> transfer(List<IntegralProductDomain> productDomains) {
        log.info(" MutiProductPojoTransfer transfer productDomains =>{}",productDomains);

        return productDomains.stream().map(productDomain -> {
            IntegralProduct pojo = modelMapper.map(productDomain, IntegralProduct.class);
            // todo 属性补充
            return pojo;
        }).collect(Collectors.toList());
    }
}
