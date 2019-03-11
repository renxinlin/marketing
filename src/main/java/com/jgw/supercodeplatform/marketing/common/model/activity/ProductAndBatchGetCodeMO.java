package com.jgw.supercodeplatform.marketing.common.model.activity;

import java.util.List;
import java.util.Map;

public class ProductAndBatchGetCodeMO {
	private String productId;

	private List<Map<String, String>> productBatchList;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<Map<String, String>> getProductBatchList() {
		return productBatchList;
	}

	public void setProductBatchList(List<Map<String, String>> productBatchList) {
		this.productBatchList = productBatchList;
	}

}
