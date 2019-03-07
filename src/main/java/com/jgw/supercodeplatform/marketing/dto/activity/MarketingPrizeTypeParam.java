package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 活动中奖奖次设置实体
 * @author czm
 *
 */
@ApiModel(value = "活动中奖奖次设置")
public class MarketingPrizeTypeParam {

	@ApiModelProperty(value = "序列Id")
    private Long id;//序列Id
	@ApiModelProperty(value = "金额数量,类型跟微信接口保持一致")
    private Integer prizeAmount;//金额数量,类型跟微信接口保持一致
	@ApiModelProperty(value = "中奖几率")
    private Integer prizeProbability;//中奖几率
	@ApiModelProperty(value = "奖品类型名称")
    private String prizeTypeName;//奖品类型名称
	@ApiModelProperty(value = "是否随机金额 1随机 0固定")
    private Byte randomAmount;//是否随机金额 1随机 0固定
	@ApiModelProperty(value = "已中奖数")
	private Long winingNum;//已中奖数
	@ApiModelProperty(value = "是否由用户创建的真实奖次")
	private Byte realPrize;//是否由用户创建的真实奖次

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
	public Byte getRandomAmount() {
		return randomAmount;
	}
	public void setRandomAmount(Byte randomAmount) {
		this.randomAmount = randomAmount;
	}

	public Long getWiningNum() {
		return winingNum;
	}

	public void setWiningNum(Long winingNum) {
		this.winingNum = winingNum;
	}

	public Byte getRealPrize() {
		return realPrize;
	}

	public void setRealPrize(Byte realPrize) {
		this.realPrize = realPrize;
	}
}
