package com.jgw.supercodeplatform.mutIntegral.domain.service;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.SendIntegralPersonDomain;

public interface SendIntegralPersonDomainService {
    SendIntegralPersonDomain findSendIntegralPerson(SendIntegralPersonDomain sendIntegralPersonDomain);

    void addIntegral(SendIntegralPersonDomain person);
}
