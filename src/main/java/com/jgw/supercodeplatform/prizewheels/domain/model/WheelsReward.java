package com.jgw.supercodeplatform.prizewheels.domain.model;

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
 * 转盘奖励
 * @author renxinlin
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WheelsReward implements Serializable {


    private Long id;

    private Long prizeWheelId;

    /**
     * 奖励类型:1 虚拟2 实物
     */
    private Integer type;

    private String name;

    /**
     * 概率
     */
    private String probability;

    private String num;

    private String picture;

    /**
     * 图片规格
     */
    private String pictureSpecs;
    // 绑定excel的key
    private String cdkKey;


}
