package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "领取页中奖页更新model")
public class MarketingPageUpdateParam {
	@ApiModelProperty(value = "领取页参数")
    private MarketingReceivingPageParam mReceivingPageParam;
	
	public MarketingReceivingPageParam getmReceivingPageParam() {
		return mReceivingPageParam;
	}

	public void setmReceivingPageParam(MarketingReceivingPageParam mReceivingPageParam) {
		this.mReceivingPageParam = mReceivingPageParam;
	}
	
	
}
