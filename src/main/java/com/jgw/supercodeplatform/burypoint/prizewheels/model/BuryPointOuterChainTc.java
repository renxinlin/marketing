package com.jgw.supercodeplatform.burypoint.prizewheels.model;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_prizewheels_bury_point_outer_chain_tc")
public class BuryPointOuterChainTc implements Serializable {


    /**
     * 主键ID
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 外链地址
     */
    @TableField("ThirdUrl")
    private String thirdUrl;

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

