package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class ProductUnsale {
    /** 主键 */
    private Long id;

    /** 主键，与基础信息保持一致 */
    private String productid;

    /** 产品名称 */
    private String unsaleproductname;

    /** 图片 */
    private String unsaleproductpic;

    /** sku数量 */
    private Integer unsaleproductskunum;

    /** 【json格式：【{"name":"小白星","[前缀可以不存储www.aaa.com]/a.jpg":""},{"":"","":""}】 */
    private String unsaleproductskuinfo;

    /** 展示价 */
    private Float showprice;

    /** 售价 */
    private Float realprice;

    /**  */
    private Integer updateuserid;

    /**  */
    private String updateusername;

    /**  */
    private Date updatedate;

    /**  */
    private Integer createuserid;

    /**  */
    private String createusername;

    /**  */
    private Date createdate;

    /**  */
    private Integer organizationid;

    /** 组织名称 */
    private String organizationname;

    /** 详情 */
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUnsaleproductname() {
        return unsaleproductname;
    }

    public void setUnsaleproductname(String unsaleproductname) {
        this.unsaleproductname = unsaleproductname;
    }

    public String getUnsaleproductpic() {
        return unsaleproductpic;
    }

    public void setUnsaleproductpic(String unsaleproductpic) {
        this.unsaleproductpic = unsaleproductpic;
    }

    public Integer getUnsaleproductskunum() {
        return unsaleproductskunum;
    }

    public void setUnsaleproductskunum(Integer unsaleproductskunum) {
        this.unsaleproductskunum = unsaleproductskunum;
    }

    public String getUnsaleproductskuinfo() {
        return unsaleproductskuinfo;
    }

    public void setUnsaleproductskuinfo(String unsaleproductskuinfo) {
        this.unsaleproductskuinfo = unsaleproductskuinfo;
    }

    public Float getShowprice() {
        return showprice;
    }

    public void setShowprice(Float showprice) {
        this.showprice = showprice;
    }

    public Float getRealprice() {
        return realprice;
    }

    public void setRealprice(Float realprice) {
        this.realprice = realprice;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}