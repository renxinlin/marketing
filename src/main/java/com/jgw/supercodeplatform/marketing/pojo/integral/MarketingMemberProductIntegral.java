package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

public class MarketingMemberProductIntegral {
    private Long id;

    private Long memberId;

    private String productId;

    private String productBatchId;

    private String sbatchId;

    private Long accrueIntegral;

    private String organizationId;

    private String organizationName;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductBatchId() {
        return productBatchId;
    }

    public void setProductBatchId(String productBatchId) {
        this.productBatchId = productBatchId;
    }

    public String getSbatchId() {
        return sbatchId;
    }

    public void setSbatchId(String sbatchId) {
        this.sbatchId = sbatchId;
    }

    public Long getAccrueIntegral() {
        return accrueIntegral;
    }

    public void setAccrueIntegral(Long accrueIntegral) {
        this.accrueIntegral = accrueIntegral;
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