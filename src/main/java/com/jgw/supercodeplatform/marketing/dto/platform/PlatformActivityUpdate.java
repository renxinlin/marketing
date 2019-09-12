package com.jgw.supercodeplatform.marketing.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@ApiModel("修改活动")
public class PlatformActivityUpdate extends PlatformActivityAdd {
    @ApiModelProperty("活动设置ID<即对应activitySetId>")
    @NotNull(message = "活动设置ID不能为空")
    @Positive(message = "活动设置ID必须为正数")
    private Long id;

}
