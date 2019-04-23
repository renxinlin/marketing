package com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
 @ApiModel("自卖产品:产品营销信息")
public class ProductMarketingSearchView {
    @ApiModelProperty("自增唯一id")
    private Long id;
    @ApiModelProperty("产品营销唯一id")
    private String productMarketId;
    @ApiModelProperty("产品唯一id")
    private String productId;
    @ApiModelProperty("组织唯一id")
    private String organizationId;
    @ApiModelProperty("价格")
    private BigDecimal price;
    @ApiModelProperty("展示价格")
    private BigDecimal viewPrice;
    @ApiModelProperty("价格单位编码")
    private String priceUnitCode;
    @ApiModelProperty("价格单位名称")
    private String priceUnitName;
    @ApiModelProperty("展示价格单位编码")
    private String viewPriceUnitCode;
    @ApiModelProperty("展示价格单位名称")
    private String viewPriceUnitName;

    @ApiModelProperty("产品详情")
    private String productDetails;
    @ApiModelProperty("产品营销sku信息")
    private List<ProductMarketingSkuSingleView> productMarketingSkus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductMarketId() {
        return productMarketId;
    }

    public void setProductMarketId(String productMarketId) {
        this.productMarketId = productMarketId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getViewPrice() {
        return viewPrice;
    }

    public void setViewPrice(BigDecimal viewPrice) {
        this.viewPrice = viewPrice;
    }

    public String getPriceUnitCode() {
        return priceUnitCode;
    }

    public void setPriceUnitCode(String priceUnitCode) {
        this.priceUnitCode = priceUnitCode;
    }

    public String getPriceUnitName() {
        return priceUnitName;
    }

    public void setPriceUnitName(String priceUnitName) {
        this.priceUnitName = priceUnitName;
    }

    public String getViewPriceUnitCode() {
        return viewPriceUnitCode;
    }

    public void setViewPriceUnitCode(String viewPriceUnitCode) {
        this.viewPriceUnitCode = viewPriceUnitCode;
    }

    public String getViewPriceUnitName() {
        return viewPriceUnitName;
    }

    public void setViewPriceUnitName(String viewPriceUnitName) {
        this.viewPriceUnitName = viewPriceUnitName;
    }



    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public List<ProductMarketingSkuSingleView> getProductMarketingSkus() {
        return productMarketingSkus;
    }

    public void setProductMarketingSkus(List<ProductMarketingSkuSingleView> productMarketingSkus) {
        this.productMarketingSkus = productMarketingSkus;
    }
}
