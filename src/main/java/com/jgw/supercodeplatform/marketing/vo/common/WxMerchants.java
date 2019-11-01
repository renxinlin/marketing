package com.jgw.supercodeplatform.marketing.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("返回微信信息")
public class WxMerchants {
    @ApiModelProperty("公众号appid")
    private String appid;

}
