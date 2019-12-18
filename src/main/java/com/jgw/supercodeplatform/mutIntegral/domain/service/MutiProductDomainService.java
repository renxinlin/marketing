package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MutiProductDomainService {
    void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralProductDomain> productDomains);
}
