package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralProductAggDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralProductBatchDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralProductDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSbatchDto;
import com.jgw.supercodeplatform.prizewheels.domain.service.ProductDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Component
@Slf4j
public class MutiProductTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<IntegralProductDomain> transferDtoToDomain(IntegralProductAggDto productAggDto, String organizationId, String organizationName) {
        List<IntegralProductDto> productDtos = productAggDto.getProductDtos();
        if(productAggDto == null || (CollectionUtils.isEmpty(productDtos))){
            return new ArrayList<>();
        }
        Integer autoType = productAggDto.getAutoType();
        Asserts.check(autoType!=null, MutiIntegralCommonConstants.nullError);
        log.info("MutiProductTransfer transferDtoToDomain productAggDto=>{} ", JSONObject.toJSONString(productAggDto));
        List<IntegralProductDomain> domains = new ArrayList<>();
        for(IntegralProductDto productDto:productDtos){
            List<IntegralProductBatchDto> productBatchDtos = productDto.getProductBatchDtos();
            if(CollectionUtils.isEmpty(productBatchDtos)){
                IntegralProductDomain domain = new IntegralProductDomain();
                domain.setAutoType(autoType);
                domain.setCreateDate(new Date());
                domain.setOrganizationId(organizationId);
                domain.setOrganizationName(organizationName);
                domain.setProductId(productDto.getProductId());
                domain.setProductName(productDto.getProductName());
                domain.setUpdateDate(new Date());
                domains.add(domain);
            }else {
                for(IntegralProductBatchDto productBatchDto:productBatchDtos){
                    IntegralProductDomain domain = new IntegralProductDomain();
                    domain.setAutoType(autoType);
                    domain.setCreateDate(new Date());
                    domain.setOrganizationId(organizationId);
                    domain.setOrganizationName(organizationName);
                    domain.setProductId(productDto.getProductId());
                    domain.setProductName(productDto.getProductName());
                    domain.setProductBatchId(productBatchDto.getProductBatchId());
                    domain.setProductBatchId(productBatchDto.getProductBatchId());
                    domain.setUpdateDate(new Date());
                    domains.add(domain);
                };
            }

        };

        return domains;
    }
}






