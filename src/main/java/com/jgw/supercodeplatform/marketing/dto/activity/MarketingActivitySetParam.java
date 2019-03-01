package com.jgw.supercodeplatform.marketing.dto.activity;

import java.util.List;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivity;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动创建model")
public class MarketingActivitySetParam {

    @ApiModelProperty(value = "活动基础参数")
	private MarketingActivitySet mActivitySetParam;

    @ApiModelProperty(value = "活动类型参数")
    private MarketingActivity mActivityParam;
	
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

    public MarketingActivitySet getmActivitySetParam() {
        return mActivitySetParam;
    }

    public void setmActivitySetParam(MarketingActivitySet mActivitySetParam) {
        this.mActivitySetParam = mActivitySetParam;
    }

    public MarketingActivity getmActivityParam() {
        return mActivityParam;
    }

    public void setmActivityParam(MarketingActivity mActivityParam) {
        this.mActivityParam = mActivityParam;
    }
}
