package com.jgw.supercodeplatform.marketing.dto;

import lombok.Data;

/**
 * 导购员登录
 */
@Data
public class SalerLoginParam {
    private String mobile;
    private String verificationCode;
}
