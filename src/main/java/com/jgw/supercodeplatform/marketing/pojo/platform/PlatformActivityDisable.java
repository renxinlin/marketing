package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class PlatformActivityDisable {
    @ApiModelProperty("活动主键Id")
    @NotNull(message = "活动主键ID不能为空")
    @Positive(message = "活动ID只能为正整数")
    private Long id;
    @ApiModelProperty("活动状态 <1、表示上架进展，0 表示下架>")
    @NotNull(message = "活动状态不能为空")
    @Range(min = 0,max = 1,message = "状态只能为0或者1")
    private Integer activityStatus;
}
