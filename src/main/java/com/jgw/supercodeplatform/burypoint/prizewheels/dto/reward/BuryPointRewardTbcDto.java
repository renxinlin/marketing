package com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/16 14:11
 */
@Data
public class BuryPointRewardTbcDto {
    /**
     * 奖励id
     */
    private String rewardId;

    private String rewardName;

    private String wheelsId;

    private String thirdUrl;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "手机型号")
    private String mobileModel;

    @ApiModelProperty(value = "系统型号")
    private String systemModel;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "浏览器版本")
    private String browserModel;
}
