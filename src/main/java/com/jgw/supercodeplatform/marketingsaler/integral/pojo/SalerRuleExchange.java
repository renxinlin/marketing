package com.jgw.supercodeplatform.marketingsaler.integral.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("marketing_saler_rule_exchange")
@ApiModel("兑换")
public class SalerRuleExchange implements Serializable {


    @ApiModelProperty("兑换ID")
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换积分
     */
    @ApiModelProperty("兑换积分")
    @TableField("ExchangeIntegral")
    private Integer exchangeIntegral;

    /**
     * 兑换库存[活动总共参与数量]
     */
    @ApiModelProperty("兑换库存[活动总共参与数量]")
    @TableField("ExchangeStock")
    private Integer exchangeStock;

    /**
     * 预剩余库存
     */
    @ApiModelProperty("预剩余库存")
    @TableField("PreHaveStock")
    private Integer preHaveStock;

    /**
     * 剩余库存
     */
    @ApiModelProperty("剩余库存")
    @TableField("HaveStock")
    private Integer haveStock;

    /**
     * 每人限兑
     */
    @ApiModelProperty("每人限兑")
    @TableField("CustomerLimitNum")
    private Integer customerLimitNum;

    /**
     * 兑换活动状态0上架1手动下架2自动下架
     */
    @ApiModelProperty("兑换活动状态3上架1手动下架2自动下架")
    @TableField("Status")
    private Byte status;

    /**
     * 支付手段：0积分
     */
    @Deprecated
    @ApiModelProperty("废弃：支付手段")
    @TableField("PayWay")
    private Byte payWay;

    /**
     * 自动下架设置0库存为0，1时间范围
     */
    @ApiModelProperty("自动下架设置0库存为0，1时间范围")
    @TableField("UndercarriageSetWay")
    private Byte undercarriageSetWay;

    /**
     * 自动下架时间
     */
    @ApiModelProperty("自动下架时间")
    @TableField("UnderCarriage")
    private Date underCarriage;

    /**
     * 库存预警0不发出警告1发出警告
     */
    @Deprecated
    @ApiModelProperty("废弃: 库存预警0不发出警告1发出警告")
    @TableField("StockWarning")
    private Byte stockWarning;

    /**
     * 库存预警数量
     */
    @Deprecated
    @TableField("StockWarningNum")
    private Integer stockWarningNum;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 中奖金额
     */
    @ApiModelProperty("中奖金额")
    @TableField("PrizeAmount")
    private Float prizeAmount;

    /**
     * 中奖概率
     */
    @ApiModelProperty("中奖概率")
    @TableField("PrizeProbability")
    private Integer prizeProbability;

    /**
     * 是否随机金额，1是 0不是
     */
    @ApiModelProperty("是否随机金额，1是 0不是")
    @TableField("IsRrandomMoney")
    private Integer isRrandomMoney;

    /**
     * 中奖金额随机下限
     */
    @ApiModelProperty("中奖金额随机下限")
    @TableField("LowRand")
    private Float lowRand;

    /**
     * 中奖金额随机上限
     */
    @ApiModelProperty("中奖金额随机上限")
    @TableField("HighRand")
    private Float highRand;


    public static SalerRuleExchange toUpdateStatus(Long id,Byte status){
        ;SalerRuleExchange salerRuleExchange = new SalerRuleExchange();
        salerRuleExchange.id = id;
        salerRuleExchange.status = status;
        return salerRuleExchange;
    }


}


