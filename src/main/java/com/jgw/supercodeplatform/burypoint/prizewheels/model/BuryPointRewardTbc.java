package com.jgw.supercodeplatform.burypoint.prizewheels.model;

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
@TableName("marketing_prizewheels_bury_point_reward_tbc")
public class BuryPointRewardTbc {
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

    @TableField("WheelsId")
    private String wheelsId;

    @TableField("ThirdUrl")
    private String thirdUrl;

    @TableField("MemberType")
    private Byte memberType;

    @TableField("Mobile")
    private String mobile;

    @TableField("MobileModel")
    private String mobileModel;

    @TableField("SystemModel")
    private String systemModel;

    @TableField("Browser")
    private String browser;

    @TableField("BrowserModel")
    private String browserModel;
}
