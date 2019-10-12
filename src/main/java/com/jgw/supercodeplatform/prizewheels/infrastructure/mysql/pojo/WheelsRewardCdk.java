package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * CDK值对象
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("marketing_activity_prize_wheels_reward_cdk")
public class WheelsRewardCdk implements Serializable {


    @TableField("prizeRewardId")
    private Long prizeRewardId;

    @TableField("cdk")
    private String cdk;

    @TableField("cdkKey")
    private String cdkKey;

    /**
     * 1未领取 2 待领取 3 领取
     */
    @TableField("status")
    private Integer status;


}
