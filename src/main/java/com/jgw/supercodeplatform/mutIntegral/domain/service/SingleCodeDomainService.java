package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSingleCodeDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SingleCodeDomainService {
      List<IntegralSingleCodeDomain> judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSingleCodeDomain> singleCodeDtos);
}
