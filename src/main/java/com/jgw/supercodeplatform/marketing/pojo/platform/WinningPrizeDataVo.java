package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("活动中奖率")
public class WinningPrizeDataVo {
    @ApiModelProperty("活动参与量")
    private Long activityJoinNum;
    @ApiModelProperty("活动中奖量")
    private Long winningPrizeNum;
    @ApiModelProperty("活动中奖率")
    private String winningPrizeRate;
}
