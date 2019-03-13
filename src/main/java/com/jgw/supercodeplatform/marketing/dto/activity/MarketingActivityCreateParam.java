package com.jgw.supercodeplatform.marketing.dto.activity;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "整个活动创建model")
public class MarketingActivityCreateParam {

    @ApiModelProperty(value = "活动基础参数")
	private MarketingActivitySetParam mActivitySetParam;

    @ApiModelProperty(value = "活动类型参数")
    private MarketingActivityParam mActivityParam;
	
	@ApiModelProperty(value = "领取页参数")
    private MarketingReceivingPageParam mReceivingPageParam;
	
	@ApiModelProperty(value = "中奖页页参数")
    private MarketingWinningPageParam mWinningPageParam;
	
	@ApiModelProperty(value = "活动设置产品参数")
	private List<MarketingActivityProductParam> mProductParams;
	
	@ApiModelProperty(value = "活动设置中奖奖次")
	private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;
	
	@ApiModelProperty(value = "渠道")
	private List<MarketingChannelParam> mChannelParams;

	public MarketingReceivingPageParam getmReceivingPageParam() {
		return mReceivingPageParam;
	}

	public void setmReceivingPageParam(MarketingReceivingPageParam mReceivingPageParam) {
		this.mReceivingPageParam = mReceivingPageParam;
	}

	public MarketingWinningPageParam getmWinningPageParam() {
		return mWinningPageParam;
	}

	public void setmWinningPageParam(MarketingWinningPageParam mWinningPageParam) {
		this.mWinningPageParam = mWinningPageParam;
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

}
