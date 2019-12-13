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
@TableName("market_new_exchange_unsale_product")
public class ExchangeUnsaleProduct implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

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

    /**
     * 规格
     */
    @TableField("SpecsJsons")
    private String specsJsons;

    @TableField("ShowPrices")
    private Double showPrices;

    @TableField("Details")
    private String details;

    @TableField("Picture")
    private String picture;

    @TableField("CreateDate")
    private Date createDate;

    @TableField("CreateUserId")
    private String createUserId;


}
