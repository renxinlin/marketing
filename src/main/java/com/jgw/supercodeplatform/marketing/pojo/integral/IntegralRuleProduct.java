package com.jgw.supercodeplatform.marketing.pojo.integral;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "积分通用规则产品")
@Data
public class IntegralRuleProduct {
	 /** 主键 */
    @ApiModelProperty(value = "主键")
    @NotBlank
    private String id;

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

    /** 积分规则主键 */
    @ApiModelProperty(value = "积分规则主键")
    private Long integralRuleId;
    
    @ApiModelProperty(value = "企业id",hidden=true)
    private String organizationId;

}