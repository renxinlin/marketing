package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SegmentCodeRepository {
      void deleteOldSetting(List<IntegralSegmentCodeDomain> segmentCodeDomains);
      void saveNewSetting(List<IntegralSegmentCodeDomain> segmentCodeDomains);
}
