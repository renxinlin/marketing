package com.jgw.supercodeplatform.marketing.pojo.integral;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("核销信息")
public class MarketingMemberCoupon {
    @ApiModelProperty(hidden = true)
	private Long id;
    @ApiModelProperty(hidden = true)
    private Long memberId;
    @ApiModelProperty(hidden = true)
    private Long couponId;
    @ApiModelProperty("抵扣券码")
    private String couponCode;
    @ApiModelProperty("抵扣券金额")
    private Double couponAmount;
    @ApiModelProperty("会员手机号")
    private String memberPhone;
    @ApiModelProperty(hidden = true)
    private String productId;
    @ApiModelProperty(hidden = true)
    private String productBatchId;
    @ApiModelProperty(hidden = true)
    private String productBatchName;
    @ApiModelProperty(hidden = true)
    private String sbatchId;
    @ApiModelProperty("产品名称")
    private String productName;
    @ApiModelProperty(hidden = true)
    private String obtainCustomerId;
    @ApiModelProperty("获得渠道名称")
    private String obtainCustmerName;
    @ApiModelProperty(hidden = true)
    private Date deductionStartDate;
    @ApiModelProperty(hidden = true)
    private Date deductionEndDate;
    @ApiModelProperty(value = "获得时间", dataType = "java.lang.String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private String verifyCustomerId;
    @ApiModelProperty("核销渠道名称")
    private String verifyCustomerName;
    @ApiModelProperty(hidden = true)
    private Long verifyMemberId;
    @ApiModelProperty("核销人员姓名")
    private String verifyPersonName;
	@ApiModelProperty("核销人员手机")
    private String verifyPersonPhone;
    @ApiModelProperty(value = "核销时间", dataType = "java.lang.String")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date verifyTime;
    @ApiModelProperty(hidden = true)
    private Byte verifyPersonType;
    @ApiModelProperty(hidden = true)
    private Byte used;
    /***********获得条件，根据活动条件设置获取*************/
    @ApiModelProperty("获得条件")
    private String obtainCondition;
    public Date getDeductionStartDate() {
		return deductionStartDate;
	}

	public void setDeductionStartDate(Date deductionStartDate) {
		this.deductionStartDate = deductionStartDate;
	}

	public Date getDeductionEndDate() {
		return deductionEndDate;
	}

	public void setDeductionEndDate(Date deductionEndDate) {
		this.deductionEndDate = deductionEndDate;
	}

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

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
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

    public String getProductBatchName() {
        return productBatchName;
    }

    public void setProductBatchName(String productBatchName) {
        this.productBatchName = productBatchName;
    }

    public String getSbatchId() {
        return sbatchId;
    }

    public void setSbatchId(String sbatchId) {
        this.sbatchId = sbatchId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getObtainCustomerId() {
        return obtainCustomerId;
    }

    public void setObtainCustomerId(String obtainCustomerId) {
        this.obtainCustomerId = obtainCustomerId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVerifyCustomerId() {
        return verifyCustomerId;
    }

    public void setVerifyCustomerId(String verifyCustomerId) {
        this.verifyCustomerId = verifyCustomerId;
    }

    public String getVerifyCustomerName() {
        return verifyCustomerName;
    }

    public void setVerifyCustomerName(String verifyCustomerName) {
        this.verifyCustomerName = verifyCustomerName;
    }

    public String getVerifyPersonName() {
        return verifyPersonName;
    }

    public void setVerifyPersonName(String verifyPersonName) {
        this.verifyPersonName = verifyPersonName;
    }

    public String getVerifyPersonPhone() {
        return verifyPersonPhone;
    }

    public void setVerifyPersonPhone(String verifyPersonPhone) {
        this.verifyPersonPhone = verifyPersonPhone;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public Byte getVerifyPersonType() {
        return verifyPersonType;
    }

    public void setVerifyPersonType(Byte verifyPersonType) {
        this.verifyPersonType = verifyPersonType;
    }

    public Byte getUsed() {
        return used;
    }

    public void setUsed(Byte used) {
        this.used = used;
    }

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getObtainCustmerName() {
		return obtainCustmerName;
	}

	public void setObtainCustmerName(String obtainCustmerName) {
		this.obtainCustmerName = obtainCustmerName;
	}

	public String getObtainCondition() {
		return obtainCondition;
	}

	public void setObtainCondition(String obtainCondition) {
		this.obtainCondition = obtainCondition;
	}

	public Long getVerifyMemberId() {
		return verifyMemberId;
	}

	public void setVerifyMemberId(Long verifyMemberId) {
		this.verifyMemberId = verifyMemberId;
	}
}