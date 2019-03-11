package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信商户信息绑定model")
public class MarketingWxMerchantsParam {
    @ApiModelProperty(value = "序列Id")
    private Long id;
    @ApiModelProperty(value = "商户账号appid")
    private String mchAppid;//商户账号appid
    @ApiModelProperty(value = "商户号")
    private String mchid;//商户号
    @ApiModelProperty(value = "商户名称")
    private String MerchantName;//商户名称
    @ApiModelProperty(value = "商户key")
    private String merchantKey;//商户key
    @ApiModelProperty(value = "证书地址")
    private String certificateAddress;//证书地址
    @ApiModelProperty(value = "证书密码")
    private String certificatePassword;//证书密码
    @ApiModelProperty(value = "文件名")
    private String fileName;


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

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	public String getMerchantKey() {
        return merchantKey;
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
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
