package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MutiProductRepository {
     void deleteOldSetting();

    void saveNewSetting(List<IntegralProductDomain> productDomains);
}
