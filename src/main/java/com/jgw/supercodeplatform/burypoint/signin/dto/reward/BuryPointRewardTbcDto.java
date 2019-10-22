package com.jgw.supercodeplatform.burypoint.signin.dto.reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/16 14:11
 */
@Data
public class BuryPointRewardTbcDto {
    @ApiModelProperty(value = "奖励Id")
    private String rewardId;

    @ApiModelProperty(value = "奖励name")
    private String rewardName;

    @ApiModelProperty(value = "活动Id")
    private String activityId;

    @ApiModelProperty(value = "第三方链接")
    private String thirdUrl;
}
