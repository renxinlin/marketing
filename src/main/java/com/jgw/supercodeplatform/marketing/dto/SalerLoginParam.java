package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导购员登录
 */
@Data
public class SalerLoginParam {
    @ApiModelProperty(value = "组织ID",required=true)
    private String organizationId;
    private String mobile;
    private String verificationCode;
}
