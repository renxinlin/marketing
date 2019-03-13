package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "产品批次model")
public class ProductBatchParam {
	@ApiModelProperty(value = "批次号" ,name="productBatchId", example="59547d98e7984f3da3fd157b15d7bcf4")
    private String productBatchId;//批次号
	
	@ApiModelProperty(value = "产品批次名称",name="productBatchName", example="产品批次名称")
    private String productBatchName;//产品批次名称

	public String getProductBatchId() {
		return productBatchId;
	}
	public void setProductBatchId(String productBatchId) {
		this.productBatchId = productBatchId;
	}
	public String getProductBatchName() {
		return productBatchName;
	}
	public void setProductBatchName(String productBatchName) {
		this.productBatchName = productBatchName;
	}
    
}
