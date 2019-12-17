package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface SbatchRepository {
    void deleteOldSetting(List<IntegralSbatchDomain> sbatchDomains);

    void saveNewSetting(List<IntegralSbatchDomain> sbatchDomains);
}
