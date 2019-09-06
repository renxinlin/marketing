package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jgw.supercodeplatform.marketingsaler.integral.constants.ExchangeUpDownStatus;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("兑换VO对象")
public class SalerRuleExchangeVo implements Serializable {
    @NotNull(message = "奖励金额对象不可为null")
    @ApiModelProperty("奖励金额对象")
    private PrizeRulesVo prizeRulesVo;

    private Long id;

    /**
     * 兑换积分
     */
    @NotNull
    @Min(value = 0,message = "兑换积分大于0")
    @ApiModelProperty("兑换积分大于0")
    private Integer exchangeIntegral;

    /**
     * 兑换库存[活动总共参与数量]
     */
    @ApiModelProperty("兑换库存[活动总共参与数量]")
    private Integer exchangeStock;


    /**
     * 剩余库存
     */
    @ApiModelProperty("剩余库存")
    private Integer haveStock;

    /**
     * 每人限兑
     */
    @ApiModelProperty("每人限兑")
    private Integer customerLimitNum;

    /**
     * 兑换活动状态3上架1手动下架2自动下架
     */
    @ApiModelProperty("兑换活动状态3上架1手动下架2自动下架")
    private Byte status;


    /**
     * 自动下架设置0库存为0，1时间范围
     */
    @ApiModelProperty("自动下架设置0库存为0，1时间范围")
    private Boolean undercarriageSetWay;

    /**
     * 自动下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("自动下架时间")
    private Date underCarriage;



    private String organizationId;

    private String organizationName;

    /**
     * 中奖金额
     */
    @ApiModelProperty("中奖金额")
    private Float prizeAmount;

    /**
     * 中奖概率
     */
    @NotNull
    @Min(value = 0,message = "中奖概率大于0")
    @ApiModelProperty("中奖概率大于0")
    private Integer prizeProbability;

    /**
     * 是否随机金额，1是 0不是
     */
    @NotNull
    @Min(value = 0,message = "是否随机金额，1是 0不是")
    @Max(value = 1,message = "是否随机金额，1是 0不是")
    @ApiModelProperty("是否随机金额，1是 0不是")
    private Integer isRrandomMoney;

    /**
     * 中奖金额随机下限
     */
    @ApiModelProperty("中奖金额随机下限")
     private Float lowRand;

    /**
     * 中奖金额随机上限
     */
    @ApiModelProperty("中奖金额随机上限")
    private Float highRand;

    public SalerRuleExchange toPojo(String organizationId,String organizationName){
        SalerRuleExchange salerRuleExchange = new SalerRuleExchange();
        BeanUtils.copyProperties(this,salerRuleExchange);
        salerRuleExchange.setOrganizationId(organizationId);
        salerRuleExchange.setOrganizationName(organizationName);
        salerRuleExchange.setPreHaveStock(this.haveStock);
        salerRuleExchange.setHaveStock(this.haveStock);
        salerRuleExchange.setExchangeStock(this.haveStock);
        salerRuleExchange.setStatus(ExchangeUpDownStatus.up);
        return salerRuleExchange;
    }


}


