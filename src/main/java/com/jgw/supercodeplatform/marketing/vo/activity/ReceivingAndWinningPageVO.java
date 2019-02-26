package com.jgw.supercodeplatform.marketing.vo.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingReceivingPage;
import com.jgw.supercodeplatform.marketing.pojo.MarketingWinningPage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "根据活动id获取领取页中奖页数据model")
public class ReceivingAndWinningPageVO {

	@ApiModelProperty(value = "领取页信息")
	private MarketingReceivingPage maReceivingPage;

	@ApiModelProperty(value = "中奖页信息")
	private MarketingWinningPage maWinningPage;

	public MarketingReceivingPage getMaReceivingPage() {
		return maReceivingPage;
	}

	public void setMaReceivingPage(MarketingReceivingPage maReceivingPage) {
		this.maReceivingPage = maReceivingPage;
	}

	public MarketingWinningPage getMaWinningPage() {
		return maWinningPage;
	}

	public void setMaWinningPage(MarketingWinningPage maWinningPage) {
		this.maWinningPage = maWinningPage;
	}

}
