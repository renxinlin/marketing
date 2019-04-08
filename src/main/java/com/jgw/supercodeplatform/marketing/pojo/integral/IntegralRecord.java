package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class IntegralRecord {
    /** 主键 */
    private Long id;

    /** 0会员 */
    private Boolean customertype;

    /** 会员id */
    private Integer customerid;

    /** 会员名称 */
    private String customername;

    /** 手机 */
    private String mobile;

    /** 奖品增减原因编码 */
    private Integer integralreasoncode;

    /** 奖品增减原因 */
    private String integralreason;

    /** 产品id */
    private String productid;

    /** 产品名称 */
    private String productname;

    /** 码信息 */
    private String outercodeid;

    /** 码信息 */
    private String codetypeid;

    /** 注册门店【h5注册的门店信息】 */
    private String registerstore;

    /** 注册门店id */
    private Integer registerstoreid;

    /** 创建时间 */
    private Date createdate;

    /** 组织id */
    private Integer organizationid;

    /** 组织名称 */
    private String organizationname;

    /** 积分增减数值 */
    private Integer integralnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCustomertype() {
        return customertype;
    }

    public void setCustomertype(Boolean customertype) {
        this.customertype = customertype;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIntegralreasoncode() {
        return integralreasoncode;
    }

    public void setIntegralreasoncode(Integer integralreasoncode) {
        this.integralreasoncode = integralreasoncode;
    }

    public String getIntegralreason() {
        return integralreason;
    }

    public void setIntegralreason(String integralreason) {
        this.integralreason = integralreason;
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

    public String getOutercodeid() {
        return outercodeid;
    }

    public void setOutercodeid(String outercodeid) {
        this.outercodeid = outercodeid;
    }

    public String getCodetypeid() {
        return codetypeid;
    }

    public void setCodetypeid(String codetypeid) {
        this.codetypeid = codetypeid;
    }

    public String getRegisterstore() {
        return registerstore;
    }

    public void setRegisterstore(String registerstore) {
        this.registerstore = registerstore;
    }

    public Integer getRegisterstoreid() {
        return registerstoreid;
    }

    public void setRegisterstoreid(Integer registerstoreid) {
        this.registerstoreid = registerstoreid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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

    public Integer getIntegralnum() {
        return integralnum;
    }

    public void setIntegralnum(Integer integralnum) {
        this.integralnum = integralnum;
    }
}