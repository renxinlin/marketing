package com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo;

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
@TableName("market_new_integral_record_channel")
public class IntegralRecordChannel implements Serializable {

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

    /**
     * 积分money
     */
    @TableField("IntegralMoney")
    private Double integralMoney;

    /**
     * 积分变动
     */
    @TableField("IntegralNum")
    private Integer integralNum;

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
     * 原因
     */
    @TableField("Reason")
    private String reason;

    /**
     * 上级积分变动
     */
    @TableField("SupIntegralChange")
    private String supIntegralChange;

    /**
     * 上级money变动
     */
    @TableField("SupMoneyChange")
    private String supMoneyChange;

    /**
     * 下级积分变动
     */
    @TableField("SubIntegralChange")
    private Integer subIntegralChange;

    /**
     * 下级money变动
     */
    @TableField("SubMoneyChange")
    private Double subMoneyChange;

    /**
     * 哪种业务数据
     */
    @TableField("ComeType")
    private String comeType;

    @TableField("ChannelId")
    private String channelId;

    @TableField("ChannelName")
    private String channelName;

    /**
     * 经销商 个人 门店
     */
    @TableField("ChannelType")
    private Integer channelType;

    /**
     * 渠道负责人iD
     */
    @TableField("ChannelMasterId")
    private String channelMasterId;

    @TableField("ChannelMasterName")
    private String channelMasterName;

    @TableField("ChannelMasterMobile")
    private String channelMasterMobile;

    /**
     * 渠道级别
     */
    @TableField("Level")
    private String level;


}
