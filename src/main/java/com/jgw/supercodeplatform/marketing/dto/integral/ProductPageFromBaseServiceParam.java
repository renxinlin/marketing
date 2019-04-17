package com.jgw.supercodeplatform.marketing.dto.integral;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("TO基础服务:已选产品")
public class ProductPageFromBaseServiceParam extends DaoSearch {
    @ApiModelProperty("已选产品列表")
    private List<String> excludeProductIds;
    @ApiModelProperty("已选产品SKU列表")
    private List<String> excludeSkuIds;

    public List<String> getExcludeProductIds() {
        return excludeProductIds;
    }

    public void setExcludeProductIds(List<String> excludeProductIds) {
        this.excludeProductIds = excludeProductIds;
    }

    public List<String> getExcludeSkuIds() {
        return excludeSkuIds;
    }

    public void setExcludeSkuIds(List<String> excludeSkuIds) {
        this.excludeSkuIds = excludeSkuIds;
    }
}
