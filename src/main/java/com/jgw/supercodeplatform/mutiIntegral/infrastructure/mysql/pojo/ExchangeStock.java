package com.jgw.supercodeplatform.mutiIntegral.infrastructure.mysql.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

  import   com.baomidou.mybatisplus.annotation.TableName;
  import   com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("market_new_exchange_stock")
public class ExchangeStock implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 1自卖2非自卖
     */
    @TableField("ExchangeId")
    private Integer exchangeId;

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

    /**
     * 库存类别1普通分片2保守分片
     */
    @TableField("Label")
    private Integer label;

    /**
     * 库存数量
     */
    @TableField("StockNum")
    private Integer stockNum;


}
