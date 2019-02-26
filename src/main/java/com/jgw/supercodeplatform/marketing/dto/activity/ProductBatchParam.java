package com.jgw.supercodeplatform.marketing.dto.activity;

public class ProductBatchParam {
    private String codeType;//类型
    private String productBatchId;//批次号
    private String productBatchName;//产品批次名称
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
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
