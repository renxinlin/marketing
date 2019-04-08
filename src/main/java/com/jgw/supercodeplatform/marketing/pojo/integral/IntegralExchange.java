package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class IntegralExchange {
    /** 主键 */
    private Long id;

    /** 兑换对象0会员 */
    private Boolean exchangeobject;

    /** 兑换资源0非自卖1自卖产品 */
    private Boolean exchangeresource;

    /** 兑换积分 */
    private Integer exchangeintegral;

    /** 兑换库存[活动总共参与数量] */
    private Integer exchangestock;

    /** 剩余库存 */
    private Integer havestock;

    /** 每人限兑 */
    private Integer customerlimitnum;

    /** 兑换活动状态0上架1下架 */
    private Boolean status;

    /** 支付手段：0积分 */
    private Boolean payway;

    /** 自动下架设置0库存为0，1时间范围 */
    private Boolean undercarriagesetway;

    /** 自动下架时间 */
    private Date undercarriage;

    /** 库存预警0不发出警告1发出警告 */
    private Boolean stockwarning;

    /** 库存预警数量 */
    private Integer stockwarningnum;

    /**  */
    private Integer createuserid;

    /**  */
    private String createusername;

    /**  */
    private Date createdate;

    /**  */
    private Integer updateuserid;

    /**  */
    private String updateusername;

    /**  */
    private Date updatedate;

    /**  */
    private Integer organizationid;

    /**  */
    private String organizationname;

    /** 产品id【业务主键，兼容基础平台】 */
    private String productid;

    /** 产品名称 */
    private String productname;

    /** sku信息 */
    private String skuname;

    /** 图片 */
    private String skuurl;

    /** 0无sku,1有sku,决定库存增对产品还是sku */
    private String skustatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExchangeobject() {
        return exchangeobject;
    }

    public void setExchangeobject(Boolean exchangeobject) {
        this.exchangeobject = exchangeobject;
    }

    public Boolean getExchangeresource() {
        return exchangeresource;
    }

    public void setExchangeresource(Boolean exchangeresource) {
        this.exchangeresource = exchangeresource;
    }

    public Integer getExchangeintegral() {
        return exchangeintegral;
    }

    public void setExchangeintegral(Integer exchangeintegral) {
        this.exchangeintegral = exchangeintegral;
    }

    public Integer getExchangestock() {
        return exchangestock;
    }

    public void setExchangestock(Integer exchangestock) {
        this.exchangestock = exchangestock;
    }

    public Integer getHavestock() {
        return havestock;
    }

    public void setHavestock(Integer havestock) {
        this.havestock = havestock;
    }

    public Integer getCustomerlimitnum() {
        return customerlimitnum;
    }

    public void setCustomerlimitnum(Integer customerlimitnum) {
        this.customerlimitnum = customerlimitnum;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getPayway() {
        return payway;
    }

    public void setPayway(Boolean payway) {
        this.payway = payway;
    }

    public Boolean getUndercarriagesetway() {
        return undercarriagesetway;
    }

    public void setUndercarriagesetway(Boolean undercarriagesetway) {
        this.undercarriagesetway = undercarriagesetway;
    }

    public Date getUndercarriage() {
        return undercarriage;
    }

    public void setUndercarriage(Date undercarriage) {
        this.undercarriage = undercarriage;
    }

    public Boolean getStockwarning() {
        return stockwarning;
    }

    public void setStockwarning(Boolean stockwarning) {
        this.stockwarning = stockwarning;
    }

    public Integer getStockwarningnum() {
        return stockwarningnum;
    }

    public void setStockwarningnum(Integer stockwarningnum) {
        this.stockwarningnum = stockwarningnum;
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public String getCreateusername() {
        return createusername;
    }

    public void setCreateusername(String createusername) {
        this.createusername = createusername;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(Integer updateuserid) {
        this.updateuserid = updateuserid;
    }

    public String getUpdateusername() {
        return updateusername;
    }

    public void setUpdateusername(String updateusername) {
        this.updateusername = updateusername;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Integer getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(Integer organizationid) {
        this.organizationid = organizationid;
    }

    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSkuname() {
        return skuname;
    }

    public void setSkuname(String skuname) {
        this.skuname = skuname;
    }

    public String getSkuurl() {
        return skuurl;
    }

    public void setSkuurl(String skuurl) {
        this.skuurl = skuurl;
    }

    public String getSkustatus() {
        return skustatus;
    }

    public void setSkustatus(String skustatus) {
        this.skustatus = skustatus;
    }
}