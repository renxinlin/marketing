package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel("H5兑换分页查询参数")
public class DaoSearchWithOrganizationId extends DaoSearch {
    @NotEmpty(message = "组织id不可为空")
    @ApiModelProperty("组织id")
    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
