package com.jgw.supercodeplatform.mutIntegral.interfaces.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("积分规则通用列表")
public class IntegralRuleRewardCommonVo {


    private Long id;

    private Long integralRuleId;

    @Range(min = 1,max = 5,message = "通用积分类型不合法")
    @ApiModelProperty(
            "1新会员注册" +
                    "2生日当天首次领取积分，额外送" +
                    "3历史首次领取积分，额外送" +
                    "4会员拉新，新会员送" +
                    "5会员拉新，老会员送")
    private Integer integralRuleType;

    @ApiModelProperty("1固定2随机3不送红包")
    private Integer rewardMoneyType;

    @ApiModelProperty("固定金额")
    private Double fixedMoney;

    @ApiModelProperty("随机金额上限")
    private Double highRandomMoney;

    /**
     *
     */
    @ApiModelProperty("随机金额下限")
    private Double lowerRandomMoney;

    /**
     *
     */
    @ApiModelProperty("1已选择送积分2未选择积分送")
    private Integer chooseedIntegral;

    @ApiModelProperty("送积分数值")
    private Integer sendIntegral;




    @ApiModelProperty("积分有效期1永久2到期时间")  private Integer expiredType;

    @ApiModelProperty("积分有效期过期时间")        private Date expiredDate;

    @ApiModelProperty("积分上限")                 private Integer integralLimit;

    @ApiModelProperty("1无上限2有上限")           private Integer integralLimitType;

}
