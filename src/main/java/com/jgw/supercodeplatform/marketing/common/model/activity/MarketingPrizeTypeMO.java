package com.jgw.supercodeplatform.marketing.common.model.activity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 活动中奖奖次设置实体
 * @author czm
 *
 */
public class MarketingPrizeTypeMO implements Comparable<MarketingPrizeTypeMO>{

    private long id;//序列Id
    private long activitySetId;//活动设置Id
    private Float prizeAmount;//金额数量,类型跟微信接口保持一致
    private Integer prizeProbability;//中奖几率
    private String prizeTypeName;//奖品类型名称
    private Byte isRrandomMoney;//是否随机金额 1随机 0固定
    private long totalNum;//当前奖次按照中奖率计算一共该中奖的码数量
    private Long winingNum;//已中奖数
    private Byte realPrize;//是否由用户创建的真实奖次
    private Float lowRand;
    private Float highRand;
    private Byte awardType;
    private Integer remainingStock;
    private Integer awardIntegralNum;
    private String cardLink;
    private Byte awardGrade;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getActivitySetId() {
		return activitySetId;
	}
	public void setActivitySetId(long activitySetId) {
		this.activitySetId = activitySetId;
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

	public Byte getIsRrandomMoney() {
		return isRrandomMoney;
	}
	public void setIsRrandomMoney(Byte isRrandomMoney) {
		this.isRrandomMoney = isRrandomMoney;
	}
	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
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
	
	public Integer getAwardIntegralNum() {
		return awardIntegralNum;
	}
	public void setAwardIntegralNum(Integer awardIntegralNum) {
		this.awardIntegralNum = awardIntegralNum;
	}
	
	public String getCardLink() {
		return cardLink;
	}
	public void setCardLink(String cardLink) {
		this.cardLink = cardLink;
	}

	public void setAwardGrade(Byte awardGrade) {
		this.awardGrade = awardGrade;
	}

	public Byte getAwardGrade() {
		return awardGrade;
	}

	@Override
	public int compareTo(MarketingPrizeTypeMO o) {
		if (o==null) {
			return 1;
		}
		if (this.prizeProbability<o.getPrizeProbability()) {
			return 1;
		}
		return 0;
	}
	@Override
	public int hashCode() {
	     return new HashCodeBuilder(17, 37).
	       append(id).
	       append(activitySetId).
	       toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false;}
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   MarketingPrizeTypeMO rhs = (MarketingPrizeTypeMO) obj;
		   return new EqualsBuilder()
		   //这里调用父类的equals()方法，一般情况下不需要使用
		                 .appendSuper(super.equals(obj))
		                 .append("id", rhs.id)
		                 .append("activitySetId", rhs.activitySetId)
		                 .isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("奖次名称", prizeTypeName).append("realPrize", realPrize).append("winingNum", winingNum)
				.append("activitySetId", activitySetId)
				.append("该奖次一共该中奖码数量", totalNum)
				.append("activitySetId", activitySetId)
				.append("isRrandomMoney", isRrandomMoney)
				.toString();
	}
	
}
