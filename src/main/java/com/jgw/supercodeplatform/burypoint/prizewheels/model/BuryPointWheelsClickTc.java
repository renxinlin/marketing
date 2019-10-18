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
 * @date 2019/10/18 11:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_bury_point_outer_wheels_click_tc")
public class BuryPointWheelsClickTc {
    /**
     * 主键ID
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

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

}
