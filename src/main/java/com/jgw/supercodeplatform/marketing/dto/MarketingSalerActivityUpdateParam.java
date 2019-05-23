package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.dto.activity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

public class MarketingSalerActivityUpdateParam {

    @ApiModelProperty(value = "活动基础参数")
    private MarketingActivitySalerSetUpdateParam mActivitySetParam;


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


    public MarketingActivitySalerSetUpdateParam getmActivitySetParam() {
        return mActivitySetParam;
    }

    public void setmActivitySetParam(MarketingActivitySalerSetUpdateParam mActivitySetParam) {
        this.mActivitySetParam = mActivitySetParam;
    }

    public List<MarketingActivityProductParam> getmProductParams() {
        return mProductParams;
    }

    public void setmProductParams(List<MarketingActivityProductParam> mProductParams) {
        this.mProductParams = mProductParams;
    }

    public List<MarketingPrizeTypeParam> getMarketingPrizeTypeParams() {
        return marketingPrizeTypeParams;
    }

    public void setMarketingPrizeTypeParams(List<MarketingPrizeTypeParam> marketingPrizeTypeParams) {
        this.marketingPrizeTypeParams = marketingPrizeTypeParams;
    }

    public List<MarketingChannelParam> getmChannelParams() {
        return mChannelParams;
    }

    public void setmChannelParams(List<MarketingChannelParam> mChannelParams) {
        this.mChannelParams = mChannelParams;
    }
}
