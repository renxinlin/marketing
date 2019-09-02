package com.jgw.supercodeplatform.marketingsaler.integral.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 产品积分规则表
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_saler_rule_reward")
public class SalerRuleReward implements Serializable {


    /**
     * 主键
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 产品id
     */
    @TableField("ProductId")
    private String productId;

    /**
     * 产品名称|注意基础信息可以发生改变
     */
    @TableField("ProductName")
    private String productName;

    /**
     * 产品价
     */
    @TableField("ProductPrice")
    private Float productPrice;

    /**
     * 奖励对象0会员
     */
    @TableField("MemberType")
    private Boolean memberType;

    /**
     * 0直接按产品，1按消费金额：（价格）除以（ 每消费X元）乘以 （积分）
     */
    @TableField("RewardRule")
    private Boolean rewardRule;

    /**
     * 每消费多少元
     */
    @TableField("PerConsume")
    private Float perConsume;

    /**
     * 奖励积分
     */
    @TableField("RewardIntegral")
    private Integer rewardIntegral;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    @TableField("UpdateDate")
    private Date updateDate;

    @TableField("CreateDate")
    private Date createDate;


}
