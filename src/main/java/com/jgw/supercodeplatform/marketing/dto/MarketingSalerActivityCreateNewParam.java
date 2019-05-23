package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySalerSetAddParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

public class MarketingSalerActivityCreateNewParam {

    @ApiModelProperty(value = "活动基础参数")
    private MarketingActivitySalerSetAddParam mActivitySetParam;




    @ApiModelProperty(value = "活动设置产品参数")
    private List<MarketingActivityProductParam> mProductParams;

    @ApiModelProperty(value = "活动设置中奖奖次")
    private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;

    @ApiModelProperty(value = "本期不传渠道")
    private List<MarketingChannelParam> mChannelParams;

    public MarketingActivitySalerSetAddParam getmActivitySetParam() {
        return mActivitySetParam;
    }

    public void setmActivitySetParam(MarketingActivitySalerSetAddParam mActivitySetParam) {
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
