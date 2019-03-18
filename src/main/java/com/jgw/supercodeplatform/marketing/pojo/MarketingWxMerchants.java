package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingWxMerchants {

    private String id;
    private String mchAppid;//商户账号appid
    private String mchid;//商户号
    private String merchantName;//商户名称
    private String merchantKey;//商户key，微信支付签名时需要
    private String certificateAddress;//证书地址
    private String certificatePassword;//证书密码
    private String organizationId;//组织id
    private String organizatioIdlName;//组织
    private String merchantSecret;//公众号secret微信授权获取token时需要用到
    private String appId;//公众号appid
    public String getId() {
		return id;
	}

	public void setId(String id) {
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

    public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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


    public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantSecret() {
		return merchantSecret;
	}

	public void setMerchantSecret(String merchantSecret) {
		this.merchantSecret = merchantSecret;
	}

}
