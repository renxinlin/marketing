package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySalerSetAddParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MarketingSalerActivityCreateNewParam {

    @ApiModelProperty(value = "活动基础参数")
    private MarketingActivitySalerSetAddParam mActivitySetParam;


    /**
     * 该参数辅助其他参数进行保存
     */
//    @ApiModelProperty(value = "活动类型参数")
//    private MarketingActivityParam mActivityParam;


    @ApiModelProperty(value = "活动设置产品参数")
    private List<MarketingActivityProductParam> mProductParams;

    @ApiModelProperty(value = "活动设置中奖奖次")
    private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;

    @ApiModelProperty(value = "本期不传渠道")
    private List<MarketingChannelParam> mChannelParams;
}
