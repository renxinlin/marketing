package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class PlatformActivityUpdate extends PlatformActivityAdd {
    @ApiModelProperty("活动ID")
    @NotNull(message = "活动ID不能为空")
    @Positive(message = "活动ID必须为正数")
    private Long id;

}
