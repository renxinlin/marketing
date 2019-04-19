package com.jgw.supercodeplatform.marketing.dto.baseservice.product.unsale;

import io.swagger.annotations.ApiModelProperty;

public class NonSelfSellingProductMarketingSkuSingleView {
    @ApiModelProperty("自增id")
    private Long id;
    @ApiModelProperty("产品营销唯一id")
    private String productMarketId;
    @ApiModelProperty("产品id")
    private String productId;
    @ApiModelProperty("组织id")
    private String organizationId;
    @ApiModelProperty("sku信息")
    private String sku;
    @ApiModelProperty("sku对应图片id")
    private String pic;



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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

}
