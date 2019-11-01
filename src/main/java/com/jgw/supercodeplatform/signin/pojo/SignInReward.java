package com.jgw.supercodeplatform.signin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("marketing_activity_sign_in_reward")
public class SignInReward implements Serializable {

    @TableId(value = "Id", type = IdType.AUTO)
    private Long id;

    /**
     * 签到ID
     */
    @TableField("SignInId")
    private Long signInId;

    /**
     * 奖励类型:1 虚拟2 代币
     */
    @TableField("Type")
    private Integer type;

    /**
     * 代币数值
     */
    @TableField("FalseMoneyNum")
    private String falseMoneyNum;

    /**
     * 奖励图片
     */
    @TableField("Picture")
    private String picture;

    /**
     * 图片大小
     */
    @TableField("PictureSpecs")
    private String pictureSpecs;

    /**
     * 图片格式
     */
    @TableField("PictureFormat")
    private String pictureFormat;

    /**
     * 图片尺寸
     */
    @TableField("PictureSize")
    private String pictureSize;

    /**
     * 奖励列表
     */
    @TableField("RewardList")
    private String rewardList;


}
