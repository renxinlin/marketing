package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

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
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_product")
public class Product implements Serializable {


    /**
     * Id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动设置主键Id
     */
    @TableField("ActivitySetId")
    private Long activitySetId;

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
     * 该批次关联码总数
     */
    @TableField("CodeTotalAmount")
    private Integer codeTotalAmount;

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
     * 活动大类0会员活动1导购活动
     */
    @TableField("ReferenceRole")
    private Byte referenceRole;

    /**
     * 生码批次ID
     */
    @TableField("SbatchId")
    private String sbatchId;

    /**
     * 产品所属活动类型其他0大转盘1
     */
    @TableField("Type")
    private Integer type;


}
