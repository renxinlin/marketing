package com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 功能描述：来自基础信息
 *
 * @Author corbett
 * @Description
 * @Date 9:28 2018/10/30
 **/
@ApiModel("产品:自卖产品")
public class ProductView {
    /**
     * 功能描述：产品编码
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品编码")
    private String productId;
    /**
     * 功能描述：产品规格编码
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品规格编码")
    private String productSpecificationsCode;
    /**
     * 功能描述：产品规格名称
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品规格名称")
    private String productSpecificationsName;
    /**
     * 功能描述：产品名
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品名")
    private String productName;
    /**
     * 功能描述：产品类目（大类）
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品类目（大类）")
    private String producLargeCategory;
    /**
     * 功能描述：产品条形码
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品条形码")
    private String productBarcode;
    /**
     * 功能描述：品牌编码
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("品牌编码")
    private String brandCode;
    /**
     * 功能描述：品牌名
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("品牌名")
    private String brandName;
    /**
     * 功能描述：产品价格
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品价格")
    private BigDecimal producPrice;
    /**
     * 功能描述：组织id
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("组织id")
    private String organizationId;
    /**
     * 功能描述：产品类目（第二级）
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品类目（第二级）")
    private String producMiddleCategory;
    /**
     * 功能描述：产品类目（第三极）
     *
     * @Author corbett
     * @Description //TODO
     * @Date 15:42 2018/10/29
     **/
    @ApiModelProperty("产品类目（第三极）")
    private String producSmallCategory;
    /**
     * 功能描述：禁用启用：0-启用；1-禁用
     *
     * @Author corbett
     * @Description //TODO
     * @Date 9:33 2018/10/30
     **/
    @ApiModelProperty("禁用启用：1-启用；0-禁用")
    private Integer disableFlag;

    @ApiModelProperty("启用禁用；1-启用；0-禁用")
    private String disableFlagName;



    /**
     * 功能描述：组织全称
     *
     * @Author corbett
     * @Description //TODO
     * @Date 14:00 2018/10/30
     **/
    @ApiModelProperty("组织全称")
    private String organizationFullName;
    /**
     * 功能描述：最大级的类目名
     *
     * @Author corbett
     * @Description //TODO
     * @Date 14:00 2018/10/30
     **/
    @ApiModelProperty("最大级的类目名")
    private String producLargeCategoryName;
    /**
     * 功能描述：第二级的类目名
     *
     * @Author corbett
     * @Description //TODO
     * @Date 14:00 2018/10/30
     **/
    @ApiModelProperty("第二级的类目名")
    private String producMiddleCategoryName;
    /**
     * 功能描述：第三级类目名
     *
     * @Author corbett
     * @Description //TODO
     * @Date 14:00 2018/10/30
     **/
    @ApiModelProperty("第三级类目名")
    private String producSmallCategoryName;
    /**
     * 功能描述：产品型号
     *
     * @Author corbett
     * @Description //TODO
     * @Date 14:00 2018/10/30
     **/
    @ApiModelProperty("产品型号")
    private String productModel;

    @ApiModelProperty("产品url，可能多个url拼接而成")
    private String productUrl;
    @ApiModelProperty("产品编码")
    private String productCode;
    @ApiModelProperty("产品分类链名称")
    private String productSortLink;
    @ApiModelProperty("产品分类")
    private String productSortId;
    @ApiModelProperty("产品二维码")
    private String qrCode;
    private Integer serialNumber;
    @ApiModelProperty("产品分类名称")
    private String productSortName;

    private ProductWareHouseView productWareHouse;

    private ProductMarketingSearchView productMarketing;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSpecificationsCode() {
        return productSpecificationsCode;
    }

    public void setProductSpecificationsCode(String productSpecificationsCode) {
        this.productSpecificationsCode = productSpecificationsCode;
    }

    public String getProductSpecificationsName() {
        return productSpecificationsName;
    }

    public void setProductSpecificationsName(String productSpecificationsName) {
        this.productSpecificationsName = productSpecificationsName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProducLargeCategory() {
        return producLargeCategory;
    }

    public void setProducLargeCategory(String producLargeCategory) {
        this.producLargeCategory = producLargeCategory;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public BigDecimal getProducPrice() {
        return producPrice;
    }

    public void setProducPrice(BigDecimal producPrice) {
        this.producPrice = producPrice;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getProducMiddleCategory() {
        return producMiddleCategory;
    }

    public void setProducMiddleCategory(String producMiddleCategory) {
        this.producMiddleCategory = producMiddleCategory;
    }

    public String getProducSmallCategory() {
        return producSmallCategory;
    }

    public void setProducSmallCategory(String producSmallCategory) {
        this.producSmallCategory = producSmallCategory;
    }

    public Integer getDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getDisableFlagName() {
        return disableFlagName;
    }

    public void setDisableFlagName(String disableFlagName) {
        this.disableFlagName = disableFlagName;
    }

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public String getProducLargeCategoryName() {
        return producLargeCategoryName;
    }

    public void setProducLargeCategoryName(String producLargeCategoryName) {
        this.producLargeCategoryName = producLargeCategoryName;
    }

    public String getProducMiddleCategoryName() {
        return producMiddleCategoryName;
    }

    public void setProducMiddleCategoryName(String producMiddleCategoryName) {
        this.producMiddleCategoryName = producMiddleCategoryName;
    }

    public String getProducSmallCategoryName() {
        return producSmallCategoryName;
    }

    public void setProducSmallCategoryName(String producSmallCategoryName) {
        this.producSmallCategoryName = producSmallCategoryName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductSortLink() {
        return productSortLink;
    }

    public void setProductSortLink(String productSortLink) {
        this.productSortLink = productSortLink;
    }

    public String getProductSortId() {
        return productSortId;
    }

    public void setProductSortId(String productSortId) {
        this.productSortId = productSortId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getProductSortName() {
        return productSortName;
    }

    public void setProductSortName(String productSortName) {
        this.productSortName = productSortName;
    }

    public ProductWareHouseView getProductWareHouse() {
        return productWareHouse;
    }

    public void setProductWareHouse(ProductWareHouseView productWareHouse) {
        this.productWareHouse = productWareHouse;
    }

    public ProductMarketingSearchView getProductMarketing() {
        return productMarketing;
    }

    public void setProductMarketing(ProductMarketingSearchView productMarketing) {
        this.productMarketing = productMarketing;
    }
}
