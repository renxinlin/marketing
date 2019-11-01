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
    private String rewardId;

    private String rewardName;

    private String activityId;

    private String thirdUrl;
}
