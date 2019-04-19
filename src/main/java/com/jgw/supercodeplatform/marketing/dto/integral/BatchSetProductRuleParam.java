package com.jgw.supercodeplatform.marketing.dto.integral;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class BatchSetProductRuleParam {
    /** 产品价格 */
    @ApiModelProperty(value = "产品价格",example="10")
    private Float productPrice;

    /** 奖励对象0会员 */
    @ApiModelProperty(value = "奖励对象0会员",example="0")
    private Byte memberType;

    /** 0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分） */
    @NotNull
    @ApiModelProperty(value = "0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分）",example="0")
    private Byte rewardRule;

    /** 每消费多少元 */
    @ApiModelProperty(value = "每消费多少元",example="10")
    private Float perConsume;

    /** 奖励积分 */
    @NotNull
    @ApiModelProperty(value = "奖励积分",example="10")
    private Integer rewardIntegral;
    
    @ApiModelProperty(value = "设置的产品集合")
    private List<Product> products;

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public Byte getMemberType() {
		return memberType;
	}

	public void setMemberType(Byte memberType) {
		this.memberType = memberType;
	}

	public Byte getRewardRule() {
		return rewardRule;
	}

	public void setRewardRule(Byte rewardRule) {
		this.rewardRule = rewardRule;
	}

	public Float getPerConsume() {
		return perConsume;
	}

	public void setPerConsume(Float perConsume) {
		this.perConsume = perConsume;
	}

	public Integer getRewardIntegral() {
		return rewardIntegral;
	}

	public void setRewardIntegral(Integer rewardIntegral) {
		this.rewardIntegral = rewardIntegral;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
    

}

