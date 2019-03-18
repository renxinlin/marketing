package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活动中奖奖次设置实体
 * @author czm
 *
 */
@ApiModel(value = "活动中奖奖次设置model")
public class MarketingPrizeTypeParam {

	@ApiModelProperty(value = "序列Id",name = "id",  example = "1")
    private Long id;//序列Id
	@ApiModelProperty(value = "固定金额数量,类型跟微信接口保持一致",name = "prizeAmount",  example = "50")
    private Integer prizeAmount;//金额数量,类型跟微信接口保持一致
	@ApiModelProperty(value = "中奖几率",name = "prizeProbability",  example = "20")
    private Integer prizeProbability;//中奖几率
	@ApiModelProperty(value = "奖品类型名称",name = "prizeTypeName",  example = "一等奖")
    private String prizeTypeName;//奖品类型名称
	@ApiModelProperty(value = "是否随机金额 1随机 0固定",name = "isRrandomMoney",  example = "1")
    private Byte isRrandomMoney;//是否随机金额 1随机 0固定
	@ApiModelProperty(value = "随机金额低取值",name = "lowRand",  example = "1")
    private Integer lowRand;
	@ApiModelProperty(value = "随机金额高取值",name = "highRand",  example = "100")
    private Integer highRand;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPrizeAmount() {
		return prizeAmount;
	}
	public void setPrizeAmount(Integer prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	public Integer getPrizeProbability() {
		return prizeProbability;
	}
	public void setPrizeProbability(Integer prizeProbability) {
		this.prizeProbability = prizeProbability;
	}
	public String getPrizeTypeName() {
		return prizeTypeName;
	}
	public void setPrizeTypeName(String prizeTypeName) {
		this.prizeTypeName = prizeTypeName;
	}
	public Integer getLowRand() {
		return lowRand;
	}
	public void setLowRand(Integer lowRand) {
		this.lowRand = lowRand;
	}
	public Integer getHighRand() {
		return highRand;
	}
	public void setHighRand(Integer highRand) {
		this.highRand = highRand;
	}
	public Byte getIsRrandomMoney() {
		return isRrandomMoney;
	}
	public void setIsRrandomMoney(Byte isRrandomMoney) {
		this.isRrandomMoney = isRrandomMoney;
	}
	
}
