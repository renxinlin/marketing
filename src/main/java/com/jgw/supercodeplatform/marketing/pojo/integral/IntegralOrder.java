package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class IntegralOrder {
    /** 主键 */
    private Long id;

    /** 订单业务主键 */
    private String orderid;

    /** 兑换资源0非自卖1自卖产品 */
    private Boolean exchangeresource;

    /** 产品id|UUID */
    private String productid;

    /** 产品名称 */
    private String productname;

    /** sku信息 */
    private String skuname;

    /** sku图片url */
    private String skuurl;

    /** 兑换积分【单积分*数量】 */
    private Integer exchangeintegralnum;

    /** 兑换数量 */
    private Integer exchangenum;

    /** 收货名 */
    private String name;

    /** 收货手机 */
    private String mobile;

    /** 收货地址 */
    private String address;

    /** 物流状态0待发货1已发货 */
    private String status;

    /** 会员id */
    private Integer memberid;

    /** 会员名称 */
    private String membername;

    /** 创建时间 */
    private Date createdate;

    /** 更新人ID */
    private Integer updateuserid;

    /** 更新人 */
    private String updateusername;

    /** 更新时间 */
    private Date updateDate;

    /** 组织id */
    private Integer organizationId;

    /** 组织名称 */
    private String organizationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Boolean getExchangeresource() {
        return exchangeresource;
    }

    public void setExchangeresource(Boolean exchangeresource) {
        this.exchangeresource = exchangeresource;
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

    public Integer getExchangeintegralnum() {
        return exchangeintegralnum;
    }

    public void setExchangeintegralnum(Integer exchangeintegralnum) {
        this.exchangeintegralnum = exchangeintegralnum;
    }

    public Integer getExchangenum() {
        return exchangenum;
    }

    public void setExchangenum(Integer exchangenum) {
        this.exchangenum = exchangenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}