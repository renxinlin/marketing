package com.jgw.supercodeplatform.marketing.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("新建活动时企业公众号信息")
public class WxMerchantInfoVo {

    @ApiModelProperty("公众号Secret")
    private String merchantSecret;

    @ApiModelProperty("公众号Appid")
    private String mchAppid;
}
