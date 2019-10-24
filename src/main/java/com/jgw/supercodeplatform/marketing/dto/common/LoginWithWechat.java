package com.jgw.supercodeplatform.marketing.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Setter
@Getter
@ApiModel("根据openid和组织id")
public class LoginWithWechat {
    @ApiModelProperty("微信授权后的openid")
    @NotBlank(message = "openid不能为空")
    private String openid;
    @ApiModelProperty("组织ID")
    @NotBlank(message = "组织ID不能为空")
    private String organizationId;

}
