package com.jgw.supercodeplatform.burypoint.signin.dto.reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/18 11:53
 */
@Data
public class BuryPointSignClickTcDto {
    @ApiModelProperty(value = "活动Id")
    private String activityId;
}
