package com.jgw.supercodeplatform.burypoint.prizewheels.dto.pv;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/11/27 11:46
 */
@Data
public class BuryPointPageVisitTcDto {

    @ApiModelProperty(value = "设备")
    private String device;

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
