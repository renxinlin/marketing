package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("活动参与率")
public class ActivityJoinDataVo {
    @ApiModelProperty("活动参与量")
    private Long activityJoinNum;
    @ApiModelProperty("扫码量")
    private Long scanCodeNum;
    @ApiModelProperty("活动中奖率")
    private String activityJoinRate;
}
