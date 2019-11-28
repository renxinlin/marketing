package com.jgw.supercodeplatform.burypoint.prizewheels.model;

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
 * 
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_prizewheels_bury_point_outer_chain_tb")
public class BuryPointOuterChainTb implements Serializable {


    /**
     * 主键ID
     */
    @TableId(value = "Id", type = IdType.ID_WORKER)
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

    @TableField("UpdateUserId")
    private String updateUserId;

    @TableField("UpdateUserName")
    private String updateUserName;

    @TableField("UpdateDate")
    private Date updateDate;

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
