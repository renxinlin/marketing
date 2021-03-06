package com.jgw.supercodeplatform.burypoint.prizewheels.dto.wx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/11/27 11:44
 */
@Data
public class BuryPointWxMerchantsTbDto {

    @ApiModelProperty(value = "公众号")
    private String WxPicture;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "手机型号")
    private String mobileModel;

    @ApiModelProperty(value = "系统型号")
    private String systemModel;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "浏览器版本")
    private String browserModel;
}
