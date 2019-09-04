package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("组织信息")
public class PlatformOrganizationDataVo {
    @ApiModelProperty("组织ID")
    private String organizationId;
    @ApiModelProperty("组织名称")
    private String organizationFullName;

}
