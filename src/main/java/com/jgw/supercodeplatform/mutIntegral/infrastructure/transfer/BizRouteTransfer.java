package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.BizRoutePriorityConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.BizRoute;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BizRouteTransfer {
    @Autowired
    private ModelMapper modelMapper;



    public List<BizRoute> transferSingleCodeToBizRoutePojo(List<IntegralSingleCodeDomain> singleCodeDomains) {
        log.info("BizRouteTransfer transferSingleCodeToBizRoutePojo singleCodeDomains => {}",singleCodeDomains);
        if(CollectionUtils.isEmpty(singleCodeDomains)){
            return new ArrayList<>();
        }

       return singleCodeDomains.stream().map(singleCodeDomain->{
            BizRoute bizRoute = modelMapper.map(singleCodeDomain, BizRoute.class);
            // todo 补充其他属性
           bizRoute.setPriority(BizRoutePriorityConstants.SingleCode);
           bizRoute.setBizType(MutiIntegralConstants.TYPE);
           bizRoute.setBizUrl(MutiIntegralConstants.URL);
           return bizRoute;
        }).collect(Collectors.toList());

    }

    public List<BizRoute> transferSegmentCodeToBizRoutePojo(List<IntegralSegmentCodeDomain> segmentCodeDomains) {
        log.info("BizRouteTransfer transferSegmentCodeToBizRoutePojo segmentCodeDomains => {}",segmentCodeDomains);
        if(CollectionUtils.isEmpty(segmentCodeDomains)){
            return new ArrayList<>();
        }

        return segmentCodeDomains.stream().map(segmentCodeDomain->{
            BizRoute bizRoute = modelMapper.map(segmentCodeDomain, BizRoute.class);
            // todo 补充其他属性
            bizRoute.setPriority(BizRoutePriorityConstants.SegmentCode);
            bizRoute.setBizType(MutiIntegralConstants.TYPE);
            bizRoute.setBizUrl(MutiIntegralConstants.URL);
            return bizRoute;
        }).collect(Collectors.toList());

    }

    public List<BizRoute> transferSbatchCodeToBizRoutePojo(List<IntegralSbatchDomain> sbatchDomains) {
        log.info("BizRouteTransfer transferSbatchCodeToBizRoutePojo sbatchDomains => {}",sbatchDomains);
        if(CollectionUtils.isEmpty(sbatchDomains)){
            return new ArrayList<>();
        }

        return sbatchDomains.stream().map(sbatchDomain->{
            BizRoute bizRoute = modelMapper.map(sbatchDomain, BizRoute.class);
            // todo 补充其他属性
            bizRoute.setPriority(BizRoutePriorityConstants.SbatchId);
            bizRoute.setBizType(MutiIntegralConstants.TYPE);
            bizRoute.setBizUrl(MutiIntegralConstants.URL);
            return bizRoute;
        }).collect(Collectors.toList());

    }

    public List<BizRoute> transferProductToBizRoutePojo(List<IntegralProductDomain> productDomins) {
        log.info("BizRouteTransfer transferProductToBizRoutePojo sbatchDomains => {}",productDomins);
        if(CollectionUtils.isEmpty(productDomins)){
            return new ArrayList<>();
        }

        return productDomins.stream().map(productDomin->{
            BizRoute bizRoute = modelMapper.map(productDomin, BizRoute.class);
            // todo 补充其他属性
            bizRoute.setPriority(BizRoutePriorityConstants.product);
            bizRoute.setBizType(MutiIntegralConstants.TYPE);
            bizRoute.setBizUrl(MutiIntegralConstants.URL);
            return bizRoute;
        }).collect(Collectors.toList());
    }
}
