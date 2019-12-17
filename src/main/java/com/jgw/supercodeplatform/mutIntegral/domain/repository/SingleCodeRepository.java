package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleCodeRepository {


    void deleteOldSetting(List<IntegralSingleCodeDomain> singledomains);

    void saveNewSetting(List<IntegralSingleCodeDomain> singledomains);
}
