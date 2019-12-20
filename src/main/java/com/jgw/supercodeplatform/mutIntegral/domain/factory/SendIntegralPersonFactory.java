package com.jgw.supercodeplatform.mutIntegral.domain.factory;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral.SendIntegralPersonDomain;

public class SendIntegralPersonFactory {
    public static SendIntegralPersonDomain build(String organizationId,String organizationName, String memberMobile, int integralNum) {

        SendIntegralPersonDomain sendIntegralPersonDomain = new SendIntegralPersonDomain();
        sendIntegralPersonDomain.setMobile(memberMobile);
        sendIntegralPersonDomain.setIntegralNum(integralNum);
        sendIntegralPersonDomain.setOrganizationId(organizationId);
        sendIntegralPersonDomain.setOrganizationName(organizationName);
        return sendIntegralPersonDomain;
    }
}
