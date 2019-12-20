package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SegmentCodeDomainService {
   void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSegmentCodeDomain> segmentCodeDtos);

 }
