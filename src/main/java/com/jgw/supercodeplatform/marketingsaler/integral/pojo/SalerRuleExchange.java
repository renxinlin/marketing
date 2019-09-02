package com.jgw.supercodeplatform.marketingsaler.integral.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class SalerRuleExchange implements Serializable {


    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 兑换积分
     */
    @TableField("ExchangeIntegral")
    private Integer exchangeIntegral;

    /**
     * 兑换库存[活动总共参与数量]
     */
    @TableField("ExchangeStock")
    private Integer exchangeStock;

    /**
     * 预剩余库存
     */
    @TableField("PreHaveStock")
    private Integer preHaveStock;

    /**
     * 剩余库存
     */
    @TableField("HaveStock")
    private Integer haveStock;

    /**
     * 每人限兑
     */
    @TableField("CustomerLimitNum")
    private Integer customerLimitNum;

    /**
     * 兑换活动状态0上架1手动下架2自动下架
     */
    @TableField("Status")
    private Boolean status;

    /**
     * 支付手段：0积分
     */
    @TableField("PayWay")
    private Boolean payWay;

    /**
     * 自动下架设置0库存为0，1时间范围
     */
    @TableField("UndercarriageSetWay")
    private Boolean undercarriageSetWay;

    /**
     * 自动下架时间
     */
    @TableField("UnderCarriage")
    private Date underCarriage;

    /**
     * 库存预警0不发出警告1发出警告
     */
    @TableField("StockWarning")
    private Boolean stockWarning;

    /**
     * 库存预警数量
     */
    @TableField("StockWarningNum")
    private Integer stockWarningNum;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 中奖金额
     */
    @TableField("PrizeAmount")
    private Float prizeAmount;

    /**
     * 中奖概率
     */
    @TableField("PrizeProbability")
    private Integer prizeProbability;

    /**
     * 是否随机金额，1是 0不是
     */
    @TableField("IsRrandomMoney")
    private Integer isRrandomMoney;

    /**
     * 中奖金额随机下限
     */
    @TableField("LowRand")
    private Float lowRand;

    /**
     * 中奖金额随机上限
     */
    @TableField("HighRand")
    private Float highRand;


}
