package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SbatchDomainService {

    void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSbatchDomain> sbatchDomains);
}
