package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SbatchDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Slf4j
public class SbatchDomainServiceImpl implements SbatchDomainService {

    @Override
    public void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSbatchDomain> sbatchDomains) {
        log.info("多级积分调用码管理批次校验入参 segmentCodeDtos{}" ,sbatchDomains);
        // TODO 调用feign
    }



}
