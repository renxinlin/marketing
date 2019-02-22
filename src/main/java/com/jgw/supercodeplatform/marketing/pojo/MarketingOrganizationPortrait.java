package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingOrganizationPortrait {

    private int id;//序列
    private String organizationId;//组织Id
    private String organizationFullName;//组织
    private String portraitCode;//画像编码
    private String portraitName;//画像名称

    public MarketingOrganizationPortrait() {
    }

    public MarketingOrganizationPortrait(int id, String organizationId, String organizationFullName, String portraitCode, String portraitName) {
        this.id = id;
        this.organizationId = organizationId;
        this.organizationFullName = organizationFullName;
        this.portraitCode = portraitCode;
        this.portraitName = portraitName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getPortraitCode() {
        return portraitCode;
    }

    public void setPortraitCode(String portraitCode) {
        this.portraitCode = portraitCode;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public void setPortraitName(String portraitName) {
        this.portraitName = portraitName;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }
}
