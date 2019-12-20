package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SegmentCodeDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Slf4j
public class SegmentCodeDomainServiceImpl implements SegmentCodeDomainService {
    @Override
    public void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSegmentCodeDomain> segmentCodeDtos) {
        log.info("多级积分调用码管理号段码校验入参 segmentCodeDtos{}" ,segmentCodeDtos);
        // TODO 调用feign
    }
}
