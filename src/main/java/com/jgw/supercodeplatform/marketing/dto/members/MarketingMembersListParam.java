package com.jgw.supercodeplatform.marketing.dto.members;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "用户列表model")
@Data
public class MarketingMembersListParam extends DaoSearch{
    @ApiModelProperty(hidden = true)
    private String organizationId;
    
}
