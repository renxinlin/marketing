package com.jgw.supercodeplatform.marketing.vo.activity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("获取微信签名")
public class WxSignVo {
    @ApiModelProperty("noncestr")
    private String noncestr;
    @ApiModelProperty("timestamp")
    private String timestamp;
    @ApiModelProperty("appId")
    private String appId;
    @ApiModelProperty("appSecret")
    private String appSecret;
    @ApiModelProperty("signature")
    private String signature;
}
