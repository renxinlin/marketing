package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信商户信息绑定model")
public class MarketingWxMerchantsParam {
    @ApiModelProperty(value = "序列Id",name="id",example="22")
    private Long id;
    @ApiModelProperty(value = "商户账号appid",name="mchAppid",example="22")
    private String mchAppid;//商户账号appid
    @ApiModelProperty(value = "商户号",name="mchid",example="22")
    private String mchid;//商户号
    @ApiModelProperty(value = "商户名称",name="merchantName",example="22")
    private String merchantName;//商户名称
    @ApiModelProperty(value = "商户key",name="merchantKey",example="22")
    private String merchantKey;//商户key
    @ApiModelProperty(value = "证书地址",name="certificateAddress",example="22")
    private String certificateAddress;//证书地址
    @ApiModelProperty(value = "证书密码",name="certificatePassword",example="22")
    private String certificatePassword;//证书密码
    @ApiModelProperty(value = "公众号secret",name="merchantSecret",example="sssdd22")
    private String merchantSecret;//公众号secret
    @ApiModelProperty(value = "组织id必须传",name="organizationId",example="sssdd22")
    private String organizationId;//组织id
    @ApiModelProperty(value = "组织名称不需要传",name="organizatioIdlName",example="sssdd22",hidden=true)
    private String organizatioIdlName;//组织
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}

	public String getMchid() {
		return mchid;
	}

	public String getMerchantSecret() {
		return merchantSecret;
	}

	public void setMerchantSecret(String merchantSecret) {
		this.merchantSecret = merchantSecret;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getMerchantKey() {
        return merchantKey;
    }

    public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizatioIdlName() {
		return organizatioIdlName;
	}

	public void setOrganizatioIdlName(String organizatioIdlName) {
		this.organizatioIdlName = organizatioIdlName;
	}

	public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getCertificateAddress() {
        return certificateAddress;
    }

    public void setCertificateAddress(String certificateAddress) {
        this.certificateAddress = certificateAddress;
    }

    public String getCertificatePassword() {
        return certificatePassword;
    }

    public void setCertificatePassword(String certificatePassword) {
        this.certificatePassword = certificatePassword;
    }

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
