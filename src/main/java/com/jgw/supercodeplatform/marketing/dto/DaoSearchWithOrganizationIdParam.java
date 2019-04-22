package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("分页查询参数")
public class DaoSearchWithOrganizationIdParam extends DaoSearch {
    @ApiModelProperty(hidden = true)
    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
