package com.jgw.supercodeplatform.marketing.pojo;
/**
 * 中奖纪录实体
 * @author czm
 *
 */
public class MarketingMembersWinRecord {

    private int id;//序列Id
    private Long activityId;//活动id
    private Long activitySetId;//活动设置id
    private String activityName;//奖品类型名称
    private String openid;//会员微信Id
    private Long prizeTypeId;//中奖奖次id
    private Float winningAmount;//中奖金额
    private String winningCode;//中奖码
    private String organizationId;//组织id
    private String mobile;//会员手机号
    private String prizeName;//奖项名称
    private String productId;//奖项名称
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


	public void setWinningAmount(Float winningAmount) {
		this.winningAmount = winningAmount;
	}


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Float getWinningAmount() {
		return winningAmount;
	}

	public String getWinningCode() {
        return winningCode;
    }

    public void setWinningCode(String winningCode) {
        this.winningCode = winningCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Long getPrizeTypeId() {
		return prizeTypeId;
	}

	public void setPrizeTypeId(Long prizeTypeId) {
		this.prizeTypeId = prizeTypeId;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public Long getActivitySetId() {
		return activitySetId;
	}

	public void setActivitySetId(Long activitySetId) {
		this.activitySetId = activitySetId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
    
}
