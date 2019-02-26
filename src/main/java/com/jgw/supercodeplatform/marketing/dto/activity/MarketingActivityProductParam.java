package com.jgw.supercodeplatform.marketing.dto.activity;

import java.util.List;

public class MarketingActivityProductParam {

    private Long id;//Id
    private String productId;//活动产品Id
    private String productName;//活动产品名称
    private List<ProductBatchParam> batchParams;
    
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
	public List<ProductBatchParam> getBatchParams() {
		return batchParams;
	}
	public void setBatchParams(List<ProductBatchParam> batchParams) {
		this.batchParams = batchParams;
	}

   
}
