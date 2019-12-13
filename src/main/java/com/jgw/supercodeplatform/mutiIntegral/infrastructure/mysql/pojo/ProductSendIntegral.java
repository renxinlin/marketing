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
@TableName("market_new_product_send_integral")
public class ProductSendIntegral implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员
     */
    @TableField("MemberId")
    private Long memberId;

    /**
     * 会员
     */
    @TableField("MemberName")
    private String memberName;

    /**
     * 会员
     */
    @TableField("MemberMobile")
    private String memberMobile;

    /**
     * 备注
     */
    @TableField("Remark")
    private String remark;

    /**
     * 门店
     */
    @TableField("CustomerId")
    private String customerId;

    /**
     * 门店
     */
    @TableField("CustomerName")
    private String customerName;

    /**
     * 积分
     */
    @TableField("IntegralNum")
    private Integer integralNum;

    /**
     * 积分时间
     */
    @TableField("OperationTime")
    private Date operationTime;

    /**
     * 操作人
     */
    @TableField("OperaterId")
    private String operaterId;

    /**
     * 操作人
     */
    @TableField("OperaterName")
    private String operaterName;

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
