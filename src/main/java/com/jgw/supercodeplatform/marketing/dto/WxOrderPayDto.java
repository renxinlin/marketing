package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WxOrderPayDto {

    private byte sendAudit;

    private String outerCodeId;

    private String mobile;

    private String openId;

    private String organizationId;

    private float amount;

    private String remoteAddr;

    private byte referenceRole;

}
