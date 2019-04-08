package com.jgw.supercodeplatform.marketing.pojo.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "积分通用规则产品")
public class IntegralRuleProduct {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 积分规则主键 */
    @ApiModelProperty(value = "积分规则主键")
    private Integer integralRuleId;

    /** 产品id */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /** 产品名称|注意基础信息可以发生改变 */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /** 产品价格 */
    @ApiModelProperty(value = "产品价格")
    private Float productPrice;

    /** 奖励对象0会员 */
    @ApiModelProperty(value = "奖励对象0会员")
    private Byte memberType;

    /** 0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分） */
    @ApiModelProperty(value = "0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分）")
    private Byte rewardRule;

    /** 每消费多少元 */
    @ApiModelProperty(value = "每消费多少元")
    private Float perConsume;

    /** 奖励积分 */
    @ApiModelProperty(value = "奖励积分")
    private Integer rewardIntegral;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIntegralRuleId() {
        return integralRuleId;
    }

    public void setIntegralRuleId(Integer integralRuleId) {
        this.integralRuleId = integralRuleId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

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
}