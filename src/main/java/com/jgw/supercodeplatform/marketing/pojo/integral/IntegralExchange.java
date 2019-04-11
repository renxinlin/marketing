package com.jgw.supercodeplatform.marketing.pojo.integral;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
@ApiModel(value = "积分兑换")
public class IntegralExchange extends DaoSearch {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 兑换对象0会员 */
    @ApiModelProperty(value = "兑换对象0会员")
    private Byte memberType;

    /** 兑换资源0非自卖1自卖产品 */
    @ApiModelProperty(value = "兑换资源0非自卖1自卖产品")
    private Byte exchangeResource;

    /** 兑换积分 */
    @ApiModelProperty(value = "每个产品兑换积分")
    private Integer exchangeIntegral;

    /** 兑换库存[活动总共参与数量] */
    @ApiModelProperty(value = "兑换库存[活动总共参与数量]")
    private Integer exchangeStock;

    /** 剩余库存 */
    @ApiModelProperty(value = "剩余库存")
    private Integer haveStock;

    /** 每人限兑 */
    @ApiModelProperty(value = "每人限兑产品数量")
    private Integer customerLimitNum;

    /** 兑换活动状态0上架1下架 */
    @ApiModelProperty(value = "兑换活动状态0上架1手动下架2自动下架")
    private Byte status;

    /** 支付手段：0积分 */
    @ApiModelProperty(value = "支付手段：0积分")
    private Byte payWay;

    /** 自动下架设置0库存为0，1时间范围 */
    @ApiModelProperty(value = "自动下架设置0库存为0，1时间范围")
    private Byte undercarriageSetWay;

    /** 自动下架时间 */
    @ApiModelProperty(value = "自动下架时间")
    private Date underCarriage;

    /** 库存预警0不发出警告1发出警告 */
    @ApiModelProperty(value = "库存预警0不发出警告1发出警告")
    private Byte stockWarning;

    /** 库存预警数量 */
    @ApiModelProperty(value = "库存预警数量")
    private Integer stockWarningNum;

    /**  */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /**  */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    /** 产品id【业务主键，兼容基础平台】 */
    @ApiModelProperty(value = "产品id【业务主键，兼容基础平台】")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;
    /** 产品图片 */
    @ApiModelProperty(value = "产品图片")
    private String productPic;
    /** sku信息 */
    @ApiModelProperty(value = "sku信息")
    private String skuName;

    /** 图片 */
    @ApiModelProperty(value = "图片")
    private String skuUrl;

    /** 0无sku,1有sku,决定库存增对产品还是sku */
    @ApiModelProperty(value = "0无sku,1有sku,决定库存增对产品还是sku")
    private Byte skuStatus;

    /** 展示价 */
    private Float showPrice;

    /** 展示价Str */
    @ApiModelProperty(value = "展示价")
    private String showPriceStr;

    public String getProductPic() {
        return productPic;
    }

    public Float getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Float showPrice) {
        this.showPrice = showPrice;
    }

    public String getShowPriceStr() {
        return showPriceStr;
    }

    public void setShowPriceStr(String showPriceStr) {
        this.showPriceStr = showPriceStr;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Byte getExchangeResource() {
        return exchangeResource;
    }

    public void setExchangeResource(Byte exchangeResource) {
        this.exchangeResource = exchangeResource;
    }

    public Integer getExchangeIntegral() {
        return exchangeIntegral;
    }

    public void setExchangeIntegral(Integer exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    public Integer getExchangeStock() {
        return exchangeStock;
    }

    public void setExchangeStock(Integer exchangeStock) {
        this.exchangeStock = exchangeStock;
    }

    public Integer getHaveStock() {
        return haveStock;
    }

    public void setHaveStock(Integer haveStock) {
        this.haveStock = haveStock;
    }

    public Integer getCustomerLimitNum() {
        return customerLimitNum;
    }

    public void setCustomerLimitNum(Integer customerLimitNum) {
        this.customerLimitNum = customerLimitNum;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getPayWay() {
        return payWay;
    }

    public void setPayWay(Byte payWay) {
        this.payWay = payWay;
    }

    public Byte getUndercarriageSetWay() {
        return undercarriageSetWay;
    }

    public void setUndercarriageSetWay(Byte undercarriageSetWay) {
        this.undercarriageSetWay = undercarriageSetWay;
    }

    public Date getUnderCarriage() {
        return underCarriage;
    }

    public void setUnderCarriage(Date underCarriage) {
        this.underCarriage = underCarriage;
    }

    public Byte getStockWarning() {
        return stockWarning;
    }

    public void setStockWarning(Byte stockWarning) {
        this.stockWarning = stockWarning;
    }

    public Integer getStockWarningNum() {
        return stockWarningNum;
    }

    public void setStockWarningNum(Integer stockWarningNum) {
        this.stockWarningNum = stockWarningNum;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public Byte getSkuStatus() {
        return skuStatus;
    }

    public void setSkuStatus(Byte skuStatus) {
        this.skuStatus = skuStatus;
    }
}