package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;

public class DaoSearchWithOrganizationIdParam extends DaoSearch {

    private String organizationId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }
}
