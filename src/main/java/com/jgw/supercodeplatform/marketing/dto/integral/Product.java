package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModelProperty;

public class Product{
	    /** 产品id */
	    @ApiModelProperty(value = "产品id")
	    private String productId;

	    /** 产品名称|注意基础信息可以发生改变 */
	    @ApiModelProperty(value = "产品名称")
	    private String productName;

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
	}