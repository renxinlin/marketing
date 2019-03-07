package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "活动中奖奖次设置")
public class ProductBatchParam {
	@ApiModelProperty(value = "类型")
    private String codeType;//类型
	@ApiModelProperty(value = "批次号")
    private String productBatchId;//批次号
	@ApiModelProperty(value = "产品批次名称")
    private String productBatchName;//产品批次名称
	@ApiModelProperty(value = "该批次码对应总量")
    private Integer codeTotalAmount;//该批次码对应总量
    
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
	public Integer getCodeTotalAmount() {
		return codeTotalAmount;
	}
	public void setCodeTotalAmount(Integer codeTotalAmount) {
		this.codeTotalAmount = codeTotalAmount;
	}
    
}
