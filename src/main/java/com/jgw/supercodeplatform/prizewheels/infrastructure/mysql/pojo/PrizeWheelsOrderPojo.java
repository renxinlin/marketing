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
 * @since 2019-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_prize_wheels_order")
public class PrizeWheelsOrderPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    @TableField("OrganizationId")
    private String organizationId;

    @TableField("OrganizatioIdName")
    private String organizatioIdName;

    @TableField("CreateDate")
    private Date createDate;

    @TableField("CreateUser")
    private String createUser;

    @TableField("CreateUserId")
    private Long createUserId;

    @TableField("Status")
    private Integer status;

    @TableField("PrizeRewardId")
    private Long prizeRewardId;

    @TableField("Address")
    private String address;

    @TableField("Content")
    private String content;

    /**
     * 收货人姓名[备用]
     */
    @TableField("ReceiverName")
    private String receiverName;

    /**
     * 收货手机
     */
    @TableField("ReceiverMobile")
    private String receiverMobile;

    /**
     * 用户手机
     */
    @TableField("Mobile")
    private String mobile;


}
