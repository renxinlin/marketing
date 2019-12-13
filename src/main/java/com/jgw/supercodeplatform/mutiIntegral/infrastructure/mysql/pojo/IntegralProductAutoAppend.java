package com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("market_new_integral_product_auto_append")
public class IntegralProductAutoAppend implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 单码废弃，不支持自动追加
     */
    @TableField("SingleCode")
    private String singleCode;

    /**
     * 产品批次名称
     */
    @TableField("SbatchID")
    private String sbatchID;

    /**
     * 活动产品Id
     */
    @TableField("StartSegmentCode")
    private String startSegmentCode;

    /**
     * 活动产品名称
     */
    @TableField("EndSegmentCode")
    private String endSegmentCode;

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
     * 2单码1号段码3 批次码
     */
    @TableField("Level")
    private Integer level;

    @TableField("ProductID")
    private String productID;

    @TableField("ProductName")
    private String productName;

    @TableField("ProductBatchId")
    private String productBatchId;

    @TableField("ProductBatchName")
    private String productBatchName;

    /**
     * 马关联主键，
     */
    @TableField("CodeRelationId")
    private String codeRelationId;


}
