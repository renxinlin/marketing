package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

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
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_prize_wheels")
public class WheelsPojo implements Serializable {


    @TableId(value = "id", type = IdType.AUTO)
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

    @TableField("ThirdUrlButton")
    private String thirdUrlButton;



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
     * 本次活动奖品种类
     */
    @TableField("PrizeNum")
    private Integer prizeNum;

    /**
     * 前端模板id
     */
    @TableField("TemplateId")
    private String  templateId;


    @TableField("ActivityStatus")
    private String  activityStatus;

}
