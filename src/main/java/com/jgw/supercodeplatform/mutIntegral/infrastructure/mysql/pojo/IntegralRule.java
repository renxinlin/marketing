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
@TableName("market_new_integral_rule")
public class IntegralRule implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 积分有效期1永久有效 2到期时间
     */
    @TableField("ExpiredType")
    private Integer expiredType;

    /**
     * 过期时间
     */
    @TableField("ExpiredDate")
    private Date expiredDate;

    /**
     * 积分上限
     */
    @TableField("IntegralLimit")
    private Integer integralLimit;

    /**
     * 1无上限2 有上限
     */
    @TableField("IntegralLimitType")
    private Integer integralLimitType;

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


}
