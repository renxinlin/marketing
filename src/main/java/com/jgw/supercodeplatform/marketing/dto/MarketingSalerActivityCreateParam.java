package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.dto.activity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class MarketingSalerActivityCreateParam {

    @ApiModelProperty(value = "活动基础参数")
    private MarketingActivitySetParam mActivitySetParam;
    /**
     * 该参数辅助其他参数进行保存
     */
    @ApiModelProperty(value = "活动类型参数")
    private MarketingActivityParam mActivityParam;


    @ApiModelProperty(value = "活动设置产品参数")
    private List<MarketingActivityProductParam> mProductParams;

    @ApiModelProperty(value = "活动设置中奖奖次")
    private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;

    @ApiModelProperty(value = "渠道")
    private List<MarketingChannelParam> mChannelParams;


}
