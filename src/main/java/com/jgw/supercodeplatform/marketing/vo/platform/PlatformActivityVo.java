package com.jgw.supercodeplatform.marketing.vo.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@ApiModel("活动")
public class PlatformActivityVo {
    @ApiModelProperty(hidden = true)
    private Date activityStartDate;
    @ApiModelProperty(hidden = true)
    private Date activityEndDate;
    @ApiModelProperty("活动id")
    private Long id;
    @ApiModelProperty("活动类型")
    private String activityName;
    @ApiModelProperty("活动标题")
    private String activityTitle;
    @ApiModelProperty("活动时间")
    private String activityDate;
    @ApiModelProperty("更新人")
    private String updateUserName;
    @ApiModelProperty(value = "更新时间", dataType = "java.lang.String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    @ApiModelProperty("活动状态 <1、表示上架进展，0 表示下架>")
    private Byte activityStatus;

}
