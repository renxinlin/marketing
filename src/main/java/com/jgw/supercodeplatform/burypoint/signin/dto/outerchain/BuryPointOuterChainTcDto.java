package com.jgw.supercodeplatform.burypoint.signin.dto.outerchain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/16 13:14
 */
@Data
@ApiModel(value = "c端签到外链点击入参")
public class BuryPointOuterChainTcDto {
    @ApiModelProperty(value = "第三方链接")
    private String thirdUrl;
    @ApiModelProperty(value = "活动ID")
    private String activityId;
}
