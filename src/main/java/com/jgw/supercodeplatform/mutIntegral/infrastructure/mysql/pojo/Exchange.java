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
@TableName("market_new_exchange")
public class Exchange implements Serializable {

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

    @TableField("UpdateUserId")
    private String updateUserId;

    @TableField("UpdateDate")
    private Date updateDate;

    @TableField("UpdateUserName")
    private String updateUserName;

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

    /**
     * 自动下架设置1库存为02时间为0
     */
    @TableField("AutoUnderType")
    private String autoUnderType;

    /**
     * 自动下架日期
     */
    @TableField("AutoUnderDate")
    private Date autoUnderDate;

    /**
     * 状态1自动下架2手动下架3上架
     */
    @TableField("ExchangeStatus")
    private Integer exchangeStatus;

    /**
     * 规格key
     */
    @TableField("SpecsId")
    private Long specsId;

    @TableField("SpecsName")
    private String specsName;

    /**
     * 规格value
     */
    @TableField("SpecsValueId")
    private Long specsValueId;

    @TableField("SpecsValueName")
    private String specsValueName;

    /**
     * 兑换展示名称
     */
    @TableField("ExchangeName")
    private String exchangeName;

    /**
     * 每人限兑
     */
    @TableField("MemberLimit")
    private Integer memberLimit;


}
