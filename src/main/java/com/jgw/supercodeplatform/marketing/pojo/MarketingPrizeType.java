package com.jgw.supercodeplatform.marketing.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 活动中奖奖次设置实体
 * @author czm
 *
 */
public class MarketingPrizeType {



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
	@ApiModelProperty(value = "活动设置Id",name = "activitySetId",  example = "1")
	private Long activitySetId;//活动设置Id
	@ApiModelProperty(value = "已中奖数",name = "winingNum",  example = "1")
	private Long winingNum;//已中奖数
	@ApiModelProperty(value = "是否由用户创建的真实奖次",name = "realPrize",  example = "1")
	private Byte realPrize;//是否由用户创建的真实奖次

    private long totalNum;//测试使用--当前奖次按照中奖率计算一共该中奖的码数量

    @ApiModelProperty(value = "奖项类型",name = "awardType",  example = "1实物 2奖券 3积分 9其它")
    private Byte awardType;
    
    @ApiModelProperty(value = "剩余库存",name = "remainingStock",  example = "21")
    private Integer remainingStock;
    
    @ApiModelProperty(value = "卡券链接",name = "cardLink",  example = "http://sdj")
    private String cardLink;
    
    @ApiModelProperty(value = "奖励的积分",name = "awardIntegralNum",  example = "21")
    private Integer awardIntegralNum;

    @ApiModelProperty("奖品等级<1:一等奖，2：二等奖，3：三等奖。。。。>")
	private Byte awardGrade;
    
 	public long getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getActivitySetId() {
		return activitySetId;
	}
	public void setActivitySetId(Long activitySetId) {
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

	public void setAwardGrade(Byte awardGrade) {
		this.awardGrade = awardGrade;
	}

	public Byte getAwardGrade() {
		return awardGrade;
	}

	@Override
	public boolean equals(Object obj) {
		   if (obj == null) { return false;}
		   if (obj == this) { return true; }
		   if (obj.getClass() != getClass()) {
		     return false;
		   }
		   MarketingPrizeType rhs = (MarketingPrizeType) obj;
		   return new EqualsBuilder()
		   //这里调用父类的equals()方法，一般情况下不需要使用
		                 .appendSuper(super.equals(obj))
		                 .append("id", rhs.id)
		                 .append("activitySetId", rhs.activitySetId)
		                 .isEquals();
	}

}
