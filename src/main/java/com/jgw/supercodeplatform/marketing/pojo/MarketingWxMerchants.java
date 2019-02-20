package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingWxMerchants {

    private String merchantId;//商户Id
    private String merchantName;//商户名称
    private String merchantKey;//商户key
    private String certificateAddress;//证书地址
    private String certificatePassword;//证书密码
    private String organizationId;//组织id
    private String organizatioIdlName;//组织

    public MarketingWxMerchants() {
    }

    public MarketingWxMerchants(String merchantId, String merchantName, String merchantKey, String certificateAddress, String certificatePassword, String organizationId, String organizatioIdlName) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.merchantKey = merchantKey;
        this.certificateAddress = certificateAddress;
        this.certificatePassword = certificatePassword;
        this.organizationId = organizationId;
        this.organizatioIdlName = organizatioIdlName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
}
