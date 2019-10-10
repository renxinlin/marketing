package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("marketing_activity_prize_wheels_record")
public class WheelsRecord implements Serializable {


    private Long id;

    private String mobile;

    @TableField("userId")
    private String userId;

    @TableField("userName")
    private String userName;

    @TableField("RewardName")
    private String rewardName;

    /**
     * 1虚拟2 实物
     */
    @TableField("Type")
    private String type;

    @TableField("Address")
    private String address;

    @TableField("CreateTime")
    private Date createTime;


}
