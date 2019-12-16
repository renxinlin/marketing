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
@TableName("market_new_product_return_integral")
public class ProductReturnIntegral implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * Id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 积分主键
     */
    @TableField("CodeId")
    private String codeId;

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
     * 退货原因
     */
    @TableField("Reason")
    private String reason;

    /**
     * 产品
     */
    @TableField("ProductId")
    private String productId;

    /**
     * 产品
     */
    @TableField("ProductName")
    private String productName;

    /**
     * 产品
     */
    @TableField("ProductBatchId")
    private String productBatchId;

    /**
     * 产品
     */
    @TableField("ProductBatchName")
    private String productBatchName;

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
     * 退货积分
     */
    @TableField("ReturnIntegralNum")
    private Integer returnIntegralNum;

    /**
     * 当前状态下累计积分
     */
    @TableField("TotalIntegral")
    private Integer totalIntegral;

    /**
     * 当前状态下可用积分
     */
    @TableField("HaveIntegral")
    private Integer haveIntegral;

    /**
     * 积分时间
     */
    @TableField("IntegralTime")
    private Date integralTime;

    /**
     * 退货时间
     */
    @TableField("ReturnTime")
    private Date returnTime;


}
