package com.jgw.supercodeplatform.burypoint.signin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author fangshiping
 * @date 2019/10/16 14:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_signin_bury_point_mallexchange_tc")
public class BuryPointMallExchangeTc {
    /**
     * 主键ID
     */
    @TableId(value = "Id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 奖励id
     */
    @TableField("RewardId")
    private String rewardId;

    @TableField("RewardName")
    private String rewardName;

    /**
     * 组织ID
     */
    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizationName")
    private String organizationName;

    @TableField("CreateUserId")
    private String createUserId;

    @TableField("CreateUser")
    private String createUser;

    @TableField("CreateDate")
    private Date createDate;

    @TableField("ActivityId")
    private String activityId;

    @TableField("MallId")
    private String mallId;

    @TableField("MallName")
    private String mallName;

    @TableField("GoodsCondition")
    private String goodsCondition;
}
