package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "活动产品model")
public class MarketingActivityProductParam {

	@ApiModelProperty(value = "id",name="id" )
    private Long id;//Id
	@ApiModelProperty(value = "活动产品Id" ,name="productId" ,example="59547d98e7984f3da3fd157b15d7bcf4")
    private String productId;//活动产品Id
	
	@ApiModelProperty(value = "产品名称",name="productName" ,example="59547d98e7984f3da3fd157b15d7bcf4")
    private String productName;//活动产品名称
	
	@ApiModelProperty(value = "产品批次参数")
    private List<ProductBatchParam> productBatchParams;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<ProductBatchParam> getProductBatchParams() {
		return productBatchParams;
	}
	public void setProductBatchParams(List<ProductBatchParam> productBatchParams) {
		this.productBatchParams = productBatchParams;
	}

   
}
