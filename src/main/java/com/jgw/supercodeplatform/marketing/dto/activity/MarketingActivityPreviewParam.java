package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "活动预览model")
public class MarketingActivityPreviewParam {

	@ApiModelProperty(value = "领取页参数")
    private MarketingReceivingPageParam mReceivingPageParam;
	
	@ApiModelProperty(value = "基础参数")
	private MarketingActivitySetParam mActivitySetParam;
	
	@ApiModelProperty(value = "活动设置中奖奖次")
	private List<MarketingPrizeTypeParam> marketingPrizeTypeParams;
	

	public MarketingReceivingPageParam getmReceivingPageParam() {
		return mReceivingPageParam;
	}

	public void setmReceivingPageParam(MarketingReceivingPageParam mReceivingPageParam) {
		this.mReceivingPageParam = mReceivingPageParam;
	}

	public List<MarketingPrizeTypeParam> getMarketingPrizeTypeParams() {
		return marketingPrizeTypeParams;
	}

	public void setMarketingPrizeTypeParams(List<MarketingPrizeTypeParam> marketingPrizeTypeParams) {
		this.marketingPrizeTypeParams = marketingPrizeTypeParams;
	}

	public MarketingActivitySetParam getmActivitySetParam() {
		return mActivitySetParam;
	}

	public void setmActivitySetParam(MarketingActivitySetParam mActivitySetParam) {
		this.mActivitySetParam = mActivitySetParam;
	}



}
