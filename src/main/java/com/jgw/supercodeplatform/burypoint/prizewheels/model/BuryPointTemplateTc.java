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
 * @date 2019/10/18 15:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_prizewheels_bury_point_template_tc")
public class BuryPointTemplateTc {
    /**
     * 主键ID
     */
    @TableId(value = "Id", type = IdType.ID_WORKER)
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

    @TableField("TemplateId")
    private String templateId;

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
