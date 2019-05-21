package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导购员登录
 */
@Data
@ApiModel("登录参数")
public class SalerLoginParam {
    @ApiModelProperty(value = "组织ID",required=true)
    private String organizationId;
    @ApiModelProperty(value = "手机号",required=true)
    private String mobile;
    @ApiModelProperty(value = "验证码",required=true)
    private String verificationCode;
    @ApiModelProperty(value = "客户端类型，字符串格式1表示微信2表示非微信")
    private String browerType ;
}
