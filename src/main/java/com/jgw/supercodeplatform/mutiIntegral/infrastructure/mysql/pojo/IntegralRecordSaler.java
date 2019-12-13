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
 * 积分领取记录表
 * </p>
 *
 * @author renxinlin
 * @since 2019-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("market_new_integral_record_saler")
public class IntegralRecordSaler implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员id
     */
    @TableField("RecordId")
    private Long recordId;

    @TableField("SalerId")
    private Long salerId;

    /**
     * 手机
     */
    @TableField("SalerMobile")
    private String salerMobile;

    /**
     * 积分增减数值
     */
    @TableField("SalerName")
    private Integer salerName;

    /**
     * 积分money
     */
    @TableField("IntegralMoney")
    private Double integralMoney;

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

    @TableField("CustomerId")
    private String customerId;

    @TableField("CustomerName")
    private String customerName;

    @TableField("IntegralNum")
    private Integer integralNum;

    /**
     * 原因
     */
    @TableField("Reason")
    private String reason;

    /**
     * 渠道积分变动
     */
    @TableField("ChannelIntegralChange")
    private String channelIntegralChange;

    /**
     * 渠道money变动
     */
    @TableField("ChannelMoneyChange")
    private String channelMoneyChange;

    /**
     * 门店积分变动
     */
    @TableField("CustomerIntegralChange")
    private Integer customerIntegralChange;

    /**
     * 门店money变动
     */
    @TableField("CustomerMoneyChange")
    private Double customerMoneyChange;

    /**
     * 哪种业务数据
     */
    @TableField("ComeType")
    private String comeType;


}
