package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "领取页中奖页更新model")
public class MarketingPageUpdateParam {
	@ApiModelProperty(value = "领取页参数")
    private MarketingReceivingPageParam mReceivingPageParam;
	
	@ApiModelProperty(value = "中奖页页参数")
    private MarketingWinningPageParam mWinningPageParam;

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
	
	
}
