package com.jgw.supercodeplatform.marketing.dto;

import java.util.List;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivitySetParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingChannelParam;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingPrizeTypeParam;

import io.swagger.annotations.ApiModelProperty;

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

	public MarketingActivitySetParam getmActivitySetParam() {
		return mActivitySetParam;
	}

	public void setmActivitySetParam(MarketingActivitySetParam mActivitySetParam) {
		this.mActivitySetParam = mActivitySetParam;
	}

	public MarketingActivityParam getmActivityParam() {
		return mActivityParam;
	}

	public void setmActivityParam(MarketingActivityParam mActivityParam) {
		this.mActivityParam = mActivityParam;
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
