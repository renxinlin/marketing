package com.jgw.supercodeplatform.marketing.pojo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel("发送中奖红包入参")
public class RedPackageParam {

    @ApiModelProperty("用户微信ID")
    @NotBlank(message = "用户微信ID不能为空")
    private String openId;
    @ApiModelProperty("中奖码")
    @NotBlank(message = "中奖码不能为空")
    private String winningCode;
}
