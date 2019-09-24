package com.jgw.supercodeplatform.marketing.vo.platform;

import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@ApiModel("会员地域")
public class MemberAreaVo {
    @ApiModelProperty("会员地域列表")
    private List<PieChartVo> regionList;
    @ApiModelProperty("最大值")
    private Long maxNum;
    @ApiModelProperty("最小值")
    private Long minNum;

}
