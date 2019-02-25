package com.jgw.supercodeplatform.marketing.common.model.activity;

public class MarketingActivityListMO {
	 private Long id;
	    private Long activityId;//活动主键Id
	    private String organizationId;//组织Id
	    private String organizatioIdlName;//组织
	    private String activityTitle;//活动标题
	    private String activityStartDate;//活动开始时间
	    private String activityEndDate;//活动结束时间
	    private String updateUserName;//更新用户名称
	    private String updateDate;//更新时间
	    private Integer activityStatus;//活动状态(1、表示上架进展，0 表示下架)
	    
	    private String activityName;// 活动名称
	    
	    private String codeType;//类型
	    private String productBatchId;//批次号
	    private String productBatchName;//产品批次名称
	    private String productId;//活动产品Id
	    private String productName;//活动产品名称
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getActivityId() {
			return activityId;
		}

		public void setActivityId(Long activityId) {
			this.activityId = activityId;
		}

		public String getOrganizationId() {
	        return organizationId;
	    }

	    public void setOrganizationId(String organizationId) {
	        this.organizationId = organizationId;
	    }

	    public String getOrganizatioIdlName() {
	        return organizatioIdlName;
	    }

	    public void setOrganizatioIdlName(String organizatioIdlName) {
	        this.organizatioIdlName = organizatioIdlName;
	    }

		public String getActivityTitle() {
	        return activityTitle;
	    }

	    public void setActivityTitle(String activityTitle) {
	        this.activityTitle = activityTitle;
	    }

	    public String getActivityStartDate() {
	        return activityStartDate;
	    }

	    public void setActivityStartDate(String activityStartDate) {
	        this.activityStartDate = activityStartDate;
	    }

	    public String getActivityEndDate() {
	        return activityEndDate;
	    }

	    public void setActivityEndDate(String activityEndDate) {
	        this.activityEndDate = activityEndDate;
	    }

	    public String getUpdateUserName() {
	        return updateUserName;
	    }

	    public void setUpdateUserName(String updateUserName) {
	        this.updateUserName = updateUserName;
	    }

	    public String getUpdateDate() {
	        return updateDate;
	    }

	    public void setUpdateDate(String updateDate) {
	        this.updateDate = updateDate;
	    }

	    public Integer getActivityStatus() {
	        return activityStatus;
	    }

	    public void setActivityStatus(Integer activityStatus) {
	        this.activityStatus = activityStatus;
	    }

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

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

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}
	    
}
