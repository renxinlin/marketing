package com.jgw.supercodeplatform.signin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
@TableName("marketing_activity_sign_in_record")
public class SignInRecord implements Serializable {



    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机
     */
    @TableField("Mobile")
    private String mobile;

    @TableField("UserId")
    private String userId;

    @TableField("UserName")
    private String userName;

    /**
     * 奖励名称
     */
    @TableField("RewardName")
    private String rewardName;

    /**
     * 1虚拟2 代币
     */
    @TableField("Type")
    private Integer type;

    @TableField("CreateTime")
    private Date createTime;


}
