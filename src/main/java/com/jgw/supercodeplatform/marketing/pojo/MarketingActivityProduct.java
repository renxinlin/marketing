package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingActivityProduct {

    private Long id;//Id
    private Long activitySetId;//活动设置主键Id
    private String codeType;//类型
    private String productBatchId;//批次号
    private String productBatchName;//产品批次名称
    private String productId;//活动产品Id
    private String productName;//活动产品名称
    private String createDate;//建立日期
    private String UpdateDate;//修改日期
    

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
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
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

    
}
