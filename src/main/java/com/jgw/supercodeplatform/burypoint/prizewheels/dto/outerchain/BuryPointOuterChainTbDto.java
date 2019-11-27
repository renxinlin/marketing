package com.jgw.supercodeplatform.burypoint.prizewheels.dto.outerchain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author fangshiping
 * @date 2019/10/16 13:14
 */
@Data
@ApiModel(value = "b端大转盘外链点击入参")
public class BuryPointOuterChainTbDto {
    @ApiModelProperty(value = "第三方链接")
    private String thirdUrl;

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
