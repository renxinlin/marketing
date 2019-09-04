package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("扫码率")
public class ScanCodeDataVo {
    @ApiModelProperty("生码量")
    private Long produceCodeNum;
    @ApiModelProperty("扫码量")
    private Long scanCodeNum;
    @ApiModelProperty("扫码率")
    private String scanCodeRate;
}
