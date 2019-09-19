package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel("抽奖")
public class LotteryPlatform extends AbandonPlatform {
    @ApiModelProperty("微信state")
    @NotBlank(message = "微信state为空")
    private String wxstate;
}
