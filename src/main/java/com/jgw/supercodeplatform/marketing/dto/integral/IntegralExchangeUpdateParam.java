package com.jgw.supercodeplatform.marketing.dto.integral;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 新增的兑换参数
 */
@ApiModel("新增兑换规则")
public class IntegralExchangeUpdateParam {

    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("对象类型，0会员")
    private Byte memberType;
    @ApiModelProperty("兑换资源0非自卖1自卖产品")
    private Byte exchangeResource;
    @ApiModelProperty("兑换积分")
    private Integer exchangeIntegral;
    @ApiModelProperty("兑换库存")
    private Integer exchangeStock;
    @ApiModelProperty("剩余库存")
    private Integer haveStock;
    @ApiModelProperty("每人限兑")
    private Integer customerLimitNum;
    @ApiModelProperty("兑换活动状态0上架1手动下架2自动下架")
    private Byte status;
    @ApiModelProperty("支付手段：0积分")
    private Byte payWay;
    @ApiModelProperty("自动下架方式0库存为零，1时间范围")
    private Byte undercarriageSetWay;
    @ApiModelProperty("下架时间：精确度：年月日")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date underCarriage;
    @ApiModelProperty("库存预警数量")
    private Integer stockWarningNum;
    @ApiModelProperty("兑换产品")
    private List<ProductAddParam> products;
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

    public Integer getStockWarningNum() {
        return stockWarningNum;
    }

    public void setStockWarningNum(Integer stockWarningNum) {
        this.stockWarningNum = stockWarningNum;
    }

    public List<ProductAddParam> getProducts() {
        return products;
    }

    public void setProducts(List<ProductAddParam> products) {
        this.products = products;
    }

    public Integer getHaveStock() {
        return haveStock;
    }

    public void setHaveStock(Integer haveStock) {
        this.haveStock = haveStock;
    }
}
