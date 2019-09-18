package com.jgw.supercodeplatform.marketing.vo.platform;

import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("日参与量与扫码量")
public class DayActivityJoinQuantityVo {
    @ApiModelProperty("横坐标")
    private List<String> data;
    @ApiModelProperty("纵坐标")
    private List<Long> value;
    @ApiModelProperty("最大值")
    private Long maxValue;
    @ApiModelProperty("最小值")
    private Long minValue;
}
