package com.jgw.supercodeplatform.burypoint.prizewheels.dto.reward;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/18 15:52
 */
@Data
public class BuryPointWxMerchantsTcDto {
    @TableField("MerchantName")
    private String merchantName;

    @TableField("MchAppid")
    private String mchAppid;
}
