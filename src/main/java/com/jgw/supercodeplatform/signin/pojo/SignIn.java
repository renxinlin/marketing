package com.jgw.supercodeplatform.signin.pojo;

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
 * @author fangshiping
 * @since 2019-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_sign_in")
public class SignIn implements Serializable {


    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField("Title1")
    private String title1;

    @TableField("Title2")
    private String title2;

    @TableField("Title3")
    private String title3;

    /**
     * 企业logo
     */
    @TableField("Logo")
    private String logo;

    @TableField("StartTime")
    private Date startTime;

    @TableField("EndTime")
    private Date endTime;

    @TableField("WxErcode")
    private String wxErcode;

    /**
     * 公众号图片规格信息
     */
    @TableField("WxErcodeSpecs")
    private String wxErcodeSpecs;

    /**
     * 第三方链接
     */
    @TableField("ThirdUrl")
    private String thirdUrl;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizatioIdName")
    private String organizatioIdName;

    @TableField("UpdateUserId")
    private String updateUserId;

    @TableField("UpdateUserName")
    private String updateUserName;

    @TableField("UpdateDate")
    private Date updateDate;

    @TableField("CreateDate")
    private Date createDate;

    @TableField("CreateUser")
    private String createUser;

    @TableField("CreateUserId")
    private String createUserId;

    /**
     * 领取条件(3579)
     */
    @TableField("GetByCondition")
    private Integer getByCondition;

    /**
     * 前端模板id
     */
    @TableField("TemplateId")
    private String templateId;

    @TableField("ThirdUrlButton")
    private String thirdUrlButton;

    /**
     * 产品详情
     */
    @TableField("ProductDetailsName")
    private String productDetailsName;

    /**
     * 产品详情链接
     */
    @TableField("ProductDetailsUrl")
    private String productDetailsUrl;

    /**
     * 扫码次数
     */
    @TableField("ScanningNum")
    private String scanningNum;


}
