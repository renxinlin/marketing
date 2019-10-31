package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingPrizewheelsProduct {

    private Long id;//Id
    private Long activitySetId;//活动设置主键Id
    private String codeType;//类型
    private String productBatchId;//批次号
    private String productBatchName;//产品批次名称
    private String productId;//活动产品Id
    private String productName;//活动产品名称
	private Long codeTotalAmount;//该批次关联码总数
	private Byte referenceRole;
    private String createDate;//建立日期
    private String updateDate;//修改日期
	private String sbatchId;//生码批次

	private Integer autoType;// 1自动追加
	private Integer type;// 7大转盘


    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Long getCodeTotalAmount() {
		return codeTotalAmount;
	}

	public void setCodeTotalAmount(Long codeTotalAmount) {
		this.codeTotalAmount = codeTotalAmount;
	}

	public Byte getReferenceRole() {
		return referenceRole;
	}

	public void setReferenceRole(Byte referenceRole) {
		this.referenceRole = referenceRole;
	}

	public String getSbatchId() {
		return sbatchId;
	}

	public void setSbatchId(String sbatchId) {
		this.sbatchId = sbatchId;
	}
	
	@Override
	public boolean equals(Object obj) {
		MarketingPrizewheelsProduct productObj = (MarketingPrizewheelsProduct)obj;
		if(productId != null && productBatchId != null && productObj != null) {
			return productId.equals(productObj.getProductId()) && productBatchId.equals(productObj.getProductBatchId());
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		if(productId != null && productBatchId != null) {
			return (productId+":"+productBatchId).hashCode();
		}
		return super.hashCode();
	}
	
}
