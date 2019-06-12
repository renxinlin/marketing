package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class MarketingCoupon {
    private Long id;

    private Long activitySetId;

    private String organizationId;

    private String organizationName;
    /**
     * 优惠券金额
     */
    private Double couponAmount;
    /**
     * 领取截止时间
     */
    private Date deductionDate;

    private Byte deductionProductType;

    private Byte deductionChannelType;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivitySetId() {
        return activitySetId;
    }

    public void setActivitySetId(Long activitySetId) {
        this.activitySetId = activitySetId;
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

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Date getDeductionDate() {
        return deductionDate;
    }

    public void setDeductionDate(Date deductionDate) {
        this.deductionDate = deductionDate;
    }

    public Byte getDeductionProductType() {
        return deductionProductType;
    }

    public void setDeductionProductType(Byte deductionProductType) {
        this.deductionProductType = deductionProductType;
    }

    public Byte getDeductionChannelType() {
        return deductionChannelType;
    }

    public void setDeductionChannelType(Byte deductionChannelType) {
        this.deductionChannelType = deductionChannelType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}