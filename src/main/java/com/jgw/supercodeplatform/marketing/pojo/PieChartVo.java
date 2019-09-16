package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("饼图返回")
public class PieChartVo {
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("value")
    private Long vale;
}
