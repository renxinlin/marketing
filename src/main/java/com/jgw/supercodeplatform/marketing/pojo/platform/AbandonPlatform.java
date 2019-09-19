package com.jgw.supercodeplatform.marketing.pojo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ApiModel("放弃抽奖")
public class AbandonPlatform {
	@ApiModelProperty("码")
    @NotBlank(message = "码不能为空")
    private String codeId;
	@ApiModelProperty("码制")
    @NotBlank(message = "码制不能为空")
    private String codeType;
	@ApiModelProperty("商品ID")
    @NotBlank(message = "商品ID不能为空")
    private String productId;
	@ApiModelProperty("商品批次ID")
    @NotBlank(message = "商品批次ID不能为空")
    private String productBatchId;
	@ApiModelProperty("组织ID")
    @NotBlank(message = "组织ID不能为空")
    private String organizationId;
	@ApiModelProperty("组织名称")
    @NotBlank(message = "组织名称不能为空")
    private String organizationFullName;

}
