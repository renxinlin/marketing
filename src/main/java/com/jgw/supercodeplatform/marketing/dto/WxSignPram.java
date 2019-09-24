package com.jgw.supercodeplatform.marketing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel("微信签名入参")
public class WxSignPram {
    @ApiModelProperty("微信账户appid")
    @NotBlank(message = "微信账户appid不能为空")
    private String appId;
    @ApiModelProperty("微信账户appSecret")
    @NotBlank(message = "微信账户appSecret不能为空")
    private String appSecret;
    @ApiModelProperty("当前签名页面的URL")
    @NotBlank(message = "当前签名页面的URL不能为空")
    private String url;
}
