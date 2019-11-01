package com.jgw.supercodeplatform.burypoint.signin.dto.reward;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/18 15:52
 */
@Data
public class BuryPointWxMerchantsTcDto {
    @ApiModelProperty(value = "商户Id")
    private String merchantName;
    @ApiModelProperty(value = "商户名称")
    private String mchAppid;
}
