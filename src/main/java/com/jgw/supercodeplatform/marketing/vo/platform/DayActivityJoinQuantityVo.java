package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("日参与量与扫码量")
public class DayActivityJoinQuantityVo {
    @ApiModelProperty("日期")
    private String dayDate;
    @ApiModelProperty("扫码量")
    private Long scanCodeNum;
    @ApiModelProperty("活动参与量")
    private Long activityJoinNum;

}
