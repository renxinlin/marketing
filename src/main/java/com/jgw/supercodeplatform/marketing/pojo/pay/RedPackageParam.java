package com.jgw.supercodeplatform.marketing.pojo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel("发送中奖红包入参")
public class RedPackageParam {

    @ApiModelProperty("订单号")
    @NotBlank(message = "订单号不能为空")
    private String tradeNo;
    @ApiModelProperty("中奖码")
    @NotBlank(message = "中奖码不能为空")
    private String winningCode;
}
