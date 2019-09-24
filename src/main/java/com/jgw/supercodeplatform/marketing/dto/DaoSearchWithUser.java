package com.jgw.supercodeplatform.marketing.dto;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询活动列表")
public class DaoSearchWithUser extends DaoSearch {
    @ApiModelProperty(hidden = true)
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
