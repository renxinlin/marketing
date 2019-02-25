package com.jgw.supercodeplatform.marketing.pojo;
/**
 * 中奖纪录实体
 * @author czm
 *
 */
public class MarketingMembersWinRecord {

    private int id;//序列Id
    private Byte activityType;//奖品类型
    private String prizeTypeName;//奖品类型名称
    private String memberName;//会员姓名
    private String nickName;
    private String openid;//会员微信Id
    private Long prizeTypeId;//中奖奖次id
    private Integer winningAmount;//中奖金额
    private String winningCode;//中奖码
    private String organizationId;//组织id
    private String mobile;//会员手机号
    private String dealer;//经销商
    private String store;//门店
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getActivityType() {
		return activityType;
	}

	public void setActivityType(Byte activityType) {
		this.activityType = activityType;
	}

	public void setWinningAmount(Integer winningAmount) {
		this.winningAmount = winningAmount;
	}

	public String getPrizeTypeName() {
        return prizeTypeName;
    }

    public void setPrizeTypeName(String prizeTypeName) {
        this.prizeTypeName = prizeTypeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
