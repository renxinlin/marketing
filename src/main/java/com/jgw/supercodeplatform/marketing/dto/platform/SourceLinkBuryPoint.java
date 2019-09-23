package com.jgw.supercodeplatform.marketing.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel("更多链接埋点")
public class SourceLinkBuryPoint {
    @ApiModelProperty("组织ID")
    @NotBlank(message = "组织ID不能为空")
    private String organizationId;
    @ApiModelProperty("组织名称")
    @NotBlank(message = "组织名称不能为空")
    private String organizationFullName;
    @ApiModelProperty("链接地址")
    @NotBlank(message = "链接地址不能为空")
    private String sourceLink;
}
