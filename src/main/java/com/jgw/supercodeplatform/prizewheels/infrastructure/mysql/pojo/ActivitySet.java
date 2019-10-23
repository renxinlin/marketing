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
 * @since 2019-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_set")
public class ActivitySet implements Serializable {


    /**
     * 主键id
     */
    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 活动设置Id
     */
    @TableField("ActivityId")
    private Long activityId;

    /**
     * 组织Id
     */
    @TableField("OrganizationId")
    private String organizationId;

    /**
     * 组织
     */
    @TableField("OrganizatioIdlName")
    private String organizatioIdlName;

    /**
     * 活动标题，仅做后台列表展示使用
     */
    @TableField("ActivityTitle")
    private String activityTitle;

    /**
     * 活动开始时间
     */
    @TableField("ActivityStartDate")
    private Date activityStartDate;

    /**
     * 活动结束时间
     */
    @TableField("ActivityEndDate")
    private Date activityEndDate;

    /**
     * 更新用户Id
     */
    @TableField("UpdateUserId")
    private String updateUserId;

    /**
     * 更新用户名称
     */
    @TableField("UpdateUserName")
    private String updateUserName;

    /**
     * 更新时间
     */
    @TableField("UpdateDate")
    private Date updateDate;

    /**
     * 活动状态(1、表示上架进展，0 表示下架)
     */
    @TableField("ActivityStatus")
    private String activityStatus;

    /**
     * 每人每天次数
     */
    @TableField("EachDayNumber")
    private Integer eachDayNumber;

    /**
     * 活动范围标志(1、表示部分产品有效 2、表示全部产品有效 )
     */
    @TableField("ActivityRangeMark")
    private Integer activityRangeMark;

    /**
     * 是否自动获取(1、自动获取 2、仅此一次 )
     */
    @TableField("autoFetch")
    private Integer autoFetch;

    /**
     * 参与该活动一共的码数
     */
    @TableField("CodeTotalNum")
    private Long codeTotalNum;

    @TableField("CreateDate")
    private Date createDate;

    /**
     * 消耗积分
     */
    @TableField("ConsumeIntegralNum")
    private Integer consumeIntegralNum;

    @TableField("ActivityDesc")
    private String activityDesc;

    /**
     * 门槛，参与条件
     */
    @TableField("ValidCondition")
    private String validCondition;

    /**
     * 0:不审核，1：需要审核
     */
    @TableField("SendAudit")
    private Integer sendAudit;

    /**
     * 微信公众号信息
     */
    @TableField("MerchantsInfo")
    private String merchantsInfo;

    /**
     * 拆除去的活动id{+activityId成唯一}
     */
    @TableField("Id1")
    private Long id1;


}
