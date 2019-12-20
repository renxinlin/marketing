package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

  import   com.baomidou.mybatisplus.annotation.TableName;
  import   com.baomidou.mybatisplus.annotation.IdType;
  import   java.util.Date;
  import   com.baomidou.mybatisplus.annotation.TableId;
  import   com.baomidou.mybatisplus.annotation.TableField;
  import   java.io.Serializable;

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
@TableName("market_new_integral_product")
public class IntegralProduct implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 积分主键
     */
    @TableField("IntegralRuleId")
    private Long integralRuleId;

    /**
     * 类型
     */
    @TableField("CodeType")
    private String codeType;

    /**
     * 产品批次号
     */
    @TableField("ProductBatchId")
    private String productBatchId;

    /**
     * 产品批次名称
     */
    @TableField("ProductBatchName")
    private String productBatchName;

    /**
     * 活动产品Id
     */
    @TableField("ProductId")
    private String productId;

    /**
     * 活动产品名称
     */
    @TableField("ProductName")
    private String productName;

    /**
     * 建立日期
     */
    @TableField("CreateDate")
    private Date createDate;

    /**
     * 修改日期
     */
    @TableField("UpdateDate")
    private Date updateDate;

    /**
     * 是否自动获取(1、自动获取 2、仅此一次 )当前仅当为大转盘和签到有效
     */
    @TableField("AutoType")
    private Integer autoType;

    /**
     * 产品分类来自基础信息定义
     */
    @TableField("ProductSortId")
    private String productSortId;

    /**
     * 产品分类来自基础信息定义
     */
    @TableField("ProductSortName")
    private String productSortName;

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
}
