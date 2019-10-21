package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(value = "产品批次model")
public class ProductBatchDto {
	@ApiModelProperty(value = "批次号" ,name="productBatchId", example="59547d98e7984f3da3fd157b15d7bcf4")
    @NotEmpty(message = "产品批次id不为空") private String productBatchId;//批次号

	@ApiModelProperty(value = "产品批次名称",name="productBatchName", example="产品批次名称")
    @NotEmpty(message = "产品批次不为空") private String productBatchName;//产品批次名称

}