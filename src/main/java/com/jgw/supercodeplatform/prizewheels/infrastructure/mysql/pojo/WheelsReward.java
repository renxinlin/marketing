package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("marketing_activity_prize_wheels_reward")
public class WheelsReward implements Serializable {


    @TableId("Id")
    private Long id;

    @TableField("PrizeWheelId")
    private Long prizeWheelId;

    /**
     * 奖励类型:1 虚拟2 实物
     */
    @TableField("Type")
    private Integer type;

    @TableField("Name")
    private String name;

    /**
     * 概率
     */
    @TableField("Probability")
    private double probability;

    @TableField("Num")
    private Integer num;

    @TableField("Picture")
    private String picture;

    /**
     * 图片规格
     */
    @TableField("PictureSpecs")
    private String pictureSpecs;


}
