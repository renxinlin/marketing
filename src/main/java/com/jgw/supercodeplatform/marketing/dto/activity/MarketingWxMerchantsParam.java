package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "微信商户信息绑定model")
@Setter
@Getter
public class MarketingWxMerchantsParam {
    @ApiModelProperty(value = "序列Id",name="id",example="22")
    private Long id;
    @ApiModelProperty(value = "商户账号appid",name="mchAppid",example="22")
	@NotBlank(message = "商户账号appid不能为空")
    private String mchAppid;//商户账号appid
    @ApiModelProperty(value = "商户号",name="mchid",example="22")
	@NotBlank(message = "商户号不能为空")
    private String mchid;//商户号
    @ApiModelProperty(value = "商户key",name="merchantKey",example="22")
	@NotBlank(message = "商户key不能为空")
    private String merchantKey;//商户key
    @ApiModelProperty(value = "证书地址",name="certificateAddress",example="22")
	@NotBlank(message = "证书不能为空")
    private String certificateAddress;//证书地址
    @ApiModelProperty(value = "证书密码",name="certificatePassword",example="22")
	@NotBlank(message = "证书密码不能为空")
    private String certificatePassword;//证书密码
    @ApiModelProperty(value = "公众号secret",name="merchantSecret",example="sssdd22")
	@NotBlank(message = "公众号secret不能为空")
    private String merchantSecret;//公众号secret
    @ApiModelProperty(value = "组织id不需要传",name="organizationId",example="sssdd22")
    private String organizationId;//组织id
    @ApiModelProperty(value = "组织名称不需要传",name="organizatioIdlName",example="sssdd22",hidden=true)
    private String organizatioIdlName;//组织
    @ApiModelProperty("是否使用甲骨文公众号，<0:使用本公司[默认]，1：使用甲骨文公众号>，不传默认0")
    private byte merchantType;
    @ApiModelProperty(value = "商户名称不需要传",name="merchantName",example="sssdd22",hidden=true)
    private String merchantName;

}
