package com.jgw.supercodeplatform.marketing.dto.activity;
/**
 * 活动中奖奖次设置实体
 * @author czm
 *
 */
public class MarketingPrizeTypeParam {

    private Long id;//序列Id
    private Integer prizeAmount;//金额数量,类型跟微信接口保持一致
    private Integer prizeProbability;//中奖几率
    private String prizeTypeName;//奖品类型名称
    private Byte randomAmount;//是否随机金额 1随机 0固定
	
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
	
   
}
