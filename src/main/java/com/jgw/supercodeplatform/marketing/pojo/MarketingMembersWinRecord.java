package com.jgw.supercodeplatform.marketing.pojo;
/**
 * 中奖纪录实体
 * @author czm
 *
 */
public class MarketingMembersWinRecord {

    private int id;//序列Id
    private Long activityId;//奖品类型
    private String activityName;//奖品类型名称
    private String openId;//会员微信Id
    private Long prizeTypeId;//中奖奖次id
    private Integer winningAmount;//中奖金额
    private String winningCode;//中奖码
    private String organizationId;//组织id
    private String mobile;//会员手机号
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


	public void setWinningAmount(Integer winningAmount) {
		this.winningAmount = winningAmount;
	}


	public Integer getWinningAmount() {
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

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
    
}
