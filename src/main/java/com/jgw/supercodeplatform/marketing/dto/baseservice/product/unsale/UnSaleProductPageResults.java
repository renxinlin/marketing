package com.jgw.supercodeplatform.marketing.dto.baseservice.product.unsale;

import com.jgw.supercodeplatform.marketing.common.page.Page;
import com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale.ProductView;
import com.jgw.supercodeplatform.marketing.dto.baseservice.product.unsale.NonSelfSellingProductMarketingSearchView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "restTemplate 接收")
public  class UnSaleProductPageResults {
	@ApiModelProperty("分页结果数据")
	private List<NonSelfSellingProductMarketingSearchView>   list;
	@ApiModelProperty("分页信息")
	private Page pagination;
	@ApiModelProperty("额外信息,没有特别声明不需要关注")
	private Object other;

	public List<NonSelfSellingProductMarketingSearchView> getList() {
		return list;
	}

	public void setList(List<NonSelfSellingProductMarketingSearchView> list) {
		this.list = list;
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
}