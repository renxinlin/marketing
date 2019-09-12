package com.jgw.supercodeplatform.marketing.controller.dispacther;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@ApiModel("页面按钮状态")
public class ButtonStatusVo {
    @ApiModelProperty("按钮名称<红包:redBag,积分:salerIntegral,订货:salerOrder>")
    private String buttonName;
    @ApiModelProperty("按钮状态<1:已开启，2：未开启>")
    private String buttonStatus;

}
