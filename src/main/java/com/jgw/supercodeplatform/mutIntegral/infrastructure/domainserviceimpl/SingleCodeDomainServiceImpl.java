package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SingleCodeDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SingleCodeDomainServiceImpl implements SingleCodeDomainService {
    @Override
    public List<IntegralSingleCodeDomain> judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralSingleCodeDomain> singleCodeDtos) {
        log.info("多级积分调用码管理单码校验入参 singleCodeDtos{}" ,singleCodeDtos);
        // TODO 调用feign
        log.info("多级积分调用码管理单码校验 sin出参gleCodeDtos{}" ,singleCodeDtos);
        return null;
    }
}
