package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_new_exchange_record")
public class ExchangeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 1自卖2非自卖
     */
    @TableField("ProductType")
    private Integer productType;

    /**
     * 1会员2导购3渠道
     */
    @TableField("ExchangeType")
    private Integer exchangeType;

    /**
     * 组织id
     */
    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 组织名称
     */
    @TableField("OrganizationName")
    private String organizationName;

    @TableField("ProductId")
    private String productId;

    @TableField("ProductName")
    private String productName;

    @TableField("ProductBatchId")
    private String productBatchId;

    @TableField("ProductBatchName")
    private String productBatchName;

    /**
     * 兑换消耗积分
     */
    @TableField("NeedIntegral")
    private Integer needIntegral;

    /**
     * 剩余库存[读请求][写请求队列通知]
     */
    @TableField("HaveStock")
    private String haveStock;

    /**
     * 原始库存数量
     */
    @TableField("Stock")
    private String stock;

    @TableField("SkuName")
    private String skuName;

    @TableField("ReciverId")
    private Long reciverId;

    @TableField("ReciverName")
    private String reciverName;

    @TableField("RevicerMobile")
    private String revicerMobile;

    @TableField("RevicerAddress")
    private String revicerAddress;

    /**
     * 兑换积分
     */
    @TableField("ExchangeIntegral")
    private String exchangeIntegral;

    @TableField("ExchangeTime")
    private Date exchangeTime;

    /**
     * 订单状态1未发货2发货
     */
    @TableField("OrderStatus")
    private Integer orderStatus;

    /**
     * 订单号
     */
    @TableField("OrderNumber")
    private String orderNumber;

    /**
     * 物流公司
     */
    @TableField("LogisticsCompanyId")
    private String logisticsCompanyId;

    @TableField("LogisticsCompanyName")
    private String logisticsCompanyName;

    /**
     * 物流单号Id
     */
    @TableField("LogisticsNumber")
    private String logisticsNumber;


}
