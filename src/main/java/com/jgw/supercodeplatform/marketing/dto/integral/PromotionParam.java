package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 积分商城值对象
 */
@ApiModel(value = "积分推广")
public class PromotionParam {
    @ApiModelProperty(value = "组织id")
    private String organizationId;
    @ApiModelProperty(value = "组织名称")
    private String organizationName;
    @ApiModelProperty(value = "企业头像")
    private String logo;
    @ApiModelProperty(value = "H5推广链接")
    private String url;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
