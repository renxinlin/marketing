package com.jgw.supercodeplatform.marketingsaler.integral.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_saler_record")
@ApiModel("导购积分记录POJO")
public class SalerRecord implements Serializable {


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 注册门店id
     */
    @ApiModelProperty("注册门店id")
    @TableField("CustomerId")
    private String customerId;

    /**
     * 注册门店【h5注册的门店信息】
     */
    @ApiModelProperty("注册门店")
    @TableField("CustomerName")
    private String customerName;

    /**
     * 奖品增减原因编码
     */
    @ApiModelProperty("奖品增减原因编码")
    @TableField("IntegralReasonCode")
    private Integer integralReasonCode;

    /**
     * 奖品增减原因
     */
    @ApiModelProperty("奖品增减原因")
    @TableField("IntegralReason")
    private String integralReason;

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @TableField("ProductId")
    private String productId;

    /**
     * 产品名称
     */
    @ApiModelProperty("产品名称")
    @TableField("ProductName")
    private String productName;

    /**
     * 码信息
     */
    @ApiModelProperty("码信息")
    @TableField("OuterCodeId")
    private String outerCodeId;

    /**
     * 码信息
     */
    @ApiModelProperty("码信息")
    @TableField("CodeTypeId")
    private String codeTypeId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
     @TableField("OrganizationId")
    private String organizationId;

    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    @TableField("OrganizationName")
    private String organizationName;

    /**
     * 积分增减数值
     */
    @ApiModelProperty("积分增减数值")
    @TableField("IntegralNum")
    private Integer integralNum;

    /**
     * 产品价格
     */
    @ApiModelProperty("产品价格")
    @TableField("ProductPrice")
    private BigDecimal productPrice;

    /**
     * 导购员红包金额
     */
    @ApiModelProperty("导购员红包金额")
    @TableField("SalerAmount")
    private BigDecimal salerAmount;

    /**
     * 导购Id
     */
    @ApiModelProperty("导购Id")
    @TableField("SalerId")
    private Long salerId;

    /**
     * 导购员名称
     */
    @ApiModelProperty("导购员名称")
    @TableField("SalerName")
    private String salerName;

    /**
     * 导购员手机
     */
    @ApiModelProperty("导购员手机")
    @TableField("SalerMobile")
    private String salerMobile;

    /**
     * 扫码状态1获得2未获得
     */
    @ApiModelProperty("扫码状态1获得2未获得")
    @TableField("Status")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("CreateDate")
    private Date createDate;


}
