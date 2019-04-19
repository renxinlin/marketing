package com.jgw.supercodeplatform.marketing.dto.baseservice.product.unsale;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

public class NonSelfSellingProductMarketingSearchView {
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
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty("产品图片")
    private String productUrl;
    @ApiModelProperty("组织名称")
    private String organizationName;
    @ApiModelProperty("更新者id")
    private String updateUserId;
    @ApiModelProperty("更新者名称")
    private String updateUserName;

    @ApiModelProperty("sku")
    private String sku;

    @ApiModelProperty("产品营销sku信息")
    private List<NonSelfSellingProductMarketingSkuSingleView> productMarketingSkus;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<NonSelfSellingProductMarketingSkuSingleView> getProductMarketingSkus() {
        return productMarketingSkus;
    }

    public void setProductMarketingSkus(List<NonSelfSellingProductMarketingSkuSingleView> productMarketingSkus) {
        this.productMarketingSkus = productMarketingSkus;
    }
}
