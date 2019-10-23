package com.jgw.supercodeplatform.marketing.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("跳转微信之前，先预存的信息，如微信登录后跳转前端地址等信息")
public class WechatPreStore {
    @ApiModelProperty("前端跳转地址")
    @NotBlank(message = "前端跳转地址不能为空")
    private String frontRedirectUrl;
    @ApiModelProperty("组织ID")
    @NotBlank(message = "组织ID不能为空")
    private String organizationId;

}
