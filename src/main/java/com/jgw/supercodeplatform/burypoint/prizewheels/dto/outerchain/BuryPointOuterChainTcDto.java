package com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author fangshiping
 * @date 2019/10/16 13:14
 */
@Data
@ApiModel(value = "c端大转盘外链点击入参")
public class BuryPointOuterChainTcDto {
    @NotEmpty(message = "外链地址不可为空")
    private String thirdUrl;

    @NotEmpty(message = "点击所属活动的id不可为空")
    private String activityId;
}
