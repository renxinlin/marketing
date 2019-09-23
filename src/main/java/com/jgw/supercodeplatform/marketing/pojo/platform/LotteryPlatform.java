package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ApiModel("抽奖")
public class LotteryPlatform extends AbandonPlatform {
    @ApiModelProperty("微信state")
    @NotBlank(message = "微信state为空")
    private String wxstate;
    @ApiModelProperty("微信用户ID")
    @NotNull(message = "微信用户ID")
    private Long memberId;
    @ApiModelProperty("商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String productName;

}
