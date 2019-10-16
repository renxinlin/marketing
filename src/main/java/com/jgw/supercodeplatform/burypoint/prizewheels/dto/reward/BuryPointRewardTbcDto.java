package com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author fangshiping
 * @date 2019/10/16 14:11
 */
@Data
public class BuryPointRewardTbcDto {
    /**
     * 奖励id
     */
    @NotBlank(message = "奖励id不可为空")
    private String rewardId;

    @NotBlank(message = "奖励name不可为空")
    private String rewardName;

    @NotBlank(message = "活动id不可为空")
    private String activityId;
}
