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
 * 积分领取记录表
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_new_integral_record")
public class IntegralRecord implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员id
     */
    @TableField("MemberId")
    private Long memberId;

    /**
     * 会员名称
     */
    @TableField("MemberName")
    private String memberName;

    /**
     * 手机
     */
    @TableField("Mobile")
    private String mobile;

    /**
     * 奖品增减原因编码
     */
    @TableField("IntegralReasonCode")
    private Integer integralReasonCode;

    /**
     * 奖品增减原因
     */
    @TableField("IntegralReason")
    private String integralReason;

    /**
     * 积分增减数值
     */
    @TableField("IntegralNum")
    private Integer integralNum;

    /**
     * 积分money
     */
    @TableField("IntegralMoney")
    private Double integralMoney;

    /**
     * 产品id
     */
    @TableField("ProductId")
    private String productId;

    /**
     * 产品名称
     */
    @TableField("ProductName")
    private String productName;

    /**
     * 产品批次id
     */
    @TableField("ProductBatchId")
    private String productBatchId;

    /**
     * 产品批次id
     */
    @TableField("ProductBatchName")
    private String productBatchName;

    /**
     * 码信息
     */
    @TableField("OuterCodeId")
    private String outerCodeId;

    /**
     * 码信息
     */
    @TableField("CodeTypeId")
    private String codeTypeId;

    /**
     * 创建时间
     */
    @TableField("CreateDate")
    private Date createDate;

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
     * 导购Id
     */
    @TableField("SalerId")
    private Long salerId;

    /**
     * 导购员名称
     */
    @TableField("SalerName")
    private String salerName;

    /**
     * 导购员手机
     */
    @TableField("SalerMobile")
    private String salerMobile;

    @TableField("SalerNum")
    private Integer salerNum;

    /**
     * 导购员红包金额
     */
    @TableField("SalerMoney")
    private Double salerMoney;

    /**
     * 注册门店id
     */
    @TableField("CustomerId")
    private String customerId;

    /**
     * 注册门店【h5注册的门店信息】
     */
    @TableField("CustomerName")
    private String customerName;

    @TableField("CustomerMoney")
    private Double customerMoney;

    @TableField("CustomerNum")
    private Integer customerNum;

    /**
     * 推荐人
     */
    @TableField("RecommendId")
    private Long recommendId;

    /**
     * 推荐人
     */
    @TableField("RecommendName")
    private String recommendName;

    /**
     * 推荐人
     */
    @TableField("RecommendMobile")
    private String recommendMobile;

    @TableField("RecommendNum")
    private Integer recommendNum;

    /**
     * 推荐人
     */
    @TableField("RecommendMoney")
    private Double recommendMoney;


}
