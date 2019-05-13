package com.jgw.supercodeplatform.marketing.diagram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class StatisicsVo {
    @ApiModelProperty("活动点击量")
    private Integer clickNum;
    @ApiModelProperty("微信红包发放累计金额")
    private Float moneyNum;
    @ApiModelProperty("积分发放累计数值")
    private Integer integralNum;
    @ApiModelProperty("积分兑换累计数值")
    private Integer exchangeNum;

}
