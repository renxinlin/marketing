package com.jgw.supercodeplatform.marketing.dto.baseservice.product;

import com.jgw.supercodeplatform.marketing.common.page.Page;
import com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale.ProductView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "restTemplate 接收")
public  class PageResults {
	@ApiModelProperty("分页结果数据")
	private List<ProductView>   list;
	@ApiModelProperty("分页信息")
	private Page pagination;
	@ApiModelProperty("额外信息,没有特别声明不需要关注")
	private Object other;

	public PageResults() {
	}

	public PageResults(List<ProductView> list, Page pagination) {
		this.list = list;
		this.pagination = pagination;
	}
	public Page getPagination() {
		return pagination;
	}
	public void setPagination(Page pagination) {
		this.pagination = pagination;
	}
	public Object getOther() {
		return other;
	}
	public void setOther(Object other) {
		this.other = other;
	}

	public List<ProductView> getList() {
		return list;
	}

	public void setList(List<ProductView> list) {
		this.list = list;
	}
}