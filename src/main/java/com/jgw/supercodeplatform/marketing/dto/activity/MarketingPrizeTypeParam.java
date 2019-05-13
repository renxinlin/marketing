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
    private Float prizeAmount;//金额数量,类型跟微信接口保持一致
	@ApiModelProperty(value = "中奖几率",name = "prizeProbability",  example = "20")
    private Integer prizeProbability;//中奖几率
	@ApiModelProperty(value = "奖品类型名称",name = "prizeTypeName",  example = "一等奖")
    private String prizeTypeName;//奖品类型名称
	@ApiModelProperty(value = "是否随机金额 1随机 0固定",name = "isRrandomMoney",  example = "1")
    private Byte isRrandomMoney;//是否随机金额 1随机 0固定
	@ApiModelProperty(value = "随机金额低取值",name = "lowRand",  example = "1")
    private Float lowRand;
	@ApiModelProperty(value = "随机金额高取值",name = "highRand",  example = "100")
    private Float highRand;
	
    @ApiModelProperty(value = "奖项类型",name = "awardType",  example = "1实物 2奖券 3积分 9其它")
    private Byte awardType;
    
    @ApiModelProperty(value = "剩余库存",name = "remainingStock",  example = "21")
    private Integer remainingStock;
    
    @ApiModelProperty(value = "卡券链接",name = "cardLink",  example = "http://sdj")
    private String cardLink;
    
    @ApiModelProperty(value = "奖励的积分",name = "awardIntegralNum",  example = "21")
    private Integer awardIntegralNum;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getPrizeAmount() {
		return prizeAmount;
	}
	public void setPrizeAmount(Float prizeAmount) {
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
	public Float getLowRand() {
		return lowRand;
	}
	public void setLowRand(Float lowRand) {
		this.lowRand = lowRand;
	}
	public Float getHighRand() {
		return highRand;
	}
	public void setHighRand(Float highRand) {
		this.highRand = highRand;
	}
	public Byte getIsRrandomMoney() {
		return isRrandomMoney;
	}
	public void setIsRrandomMoney(Byte isRrandomMoney) {
		this.isRrandomMoney = isRrandomMoney;
	}
	public Byte getAwardType() {
		return awardType;
	}
	public void setAwardType(Byte awardType) {
		this.awardType = awardType;
	}
	public Integer getRemainingStock() {
		return remainingStock;
	}
	public void setRemainingStock(Integer remainingStock) {
		this.remainingStock = remainingStock;
	}
	public String getCardLink() {
		return cardLink;
	}
	public void setCardLink(String cardLink) {
		this.cardLink = cardLink;
	}
	public Integer getAwardIntegralNum() {
		return awardIntegralNum;
	}
	public void setAwardIntegralNum(Integer awardIntegralNum) {
		this.awardIntegralNum = awardIntegralNum;
	}
	
}
