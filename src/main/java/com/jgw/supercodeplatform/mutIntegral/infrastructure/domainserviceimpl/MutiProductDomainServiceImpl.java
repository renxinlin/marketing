package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.service.MutiProductDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Slf4j
public class MutiProductDomainServiceImpl implements MutiProductDomainService {
    @Override
    public void judgeCodeCanbeSettingByIntegralFromCodeManager(List<IntegralProductDomain> productDomains) {
        log.info("多级积分调用码管理号段码校验入参 segmentCodeDtos{}" , JSONObject.toJSONString(productDomains));
        // TODO 调用feign
    }
}
