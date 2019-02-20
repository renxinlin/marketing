package com.jgw.supercodeplatform.marketing.pojo;

public class MarketingMembersWin {

    private int id;//序列Id
    private String prizeTypeCode;//奖品类型编码
    private String prizeTypeName;//奖品类型名称
    private String memberName;//会员姓名
    private String memberId;//会员Id
    private int winningNumber;//中奖奖次
    private String winningAmount;//中奖金额
    private String winningCode;//中奖码
    private String organizationId;//组织id
    private String organizationFullName;//组织全名(门店）
    private String mobile;//会员手机号
    private String organizationFlag;//组织标志(1  、表示门店 2、 表示经销商)

    public MarketingMembersWin() {
    }

    public MarketingMembersWin(int id, String prizeTypeCode, String prizeTypeName, String memberName, String memberId, int winningNumber, String winningAmount, String winningCode, String organizationId, String organizationFullName, String mobile, String organizationFlag) {
        this.id = id;
        this.prizeTypeCode = prizeTypeCode;
        this.prizeTypeName = prizeTypeName;
        this.memberName = memberName;
        this.memberId = memberId;
        this.winningNumber = winningNumber;
        this.winningAmount = winningAmount;
        this.winningCode = winningCode;
        this.organizationId = organizationId;
        this.organizationFullName = organizationFullName;
        this.mobile = mobile;
        this.organizationFlag = organizationFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrizeTypeCode() {
        return prizeTypeCode;
    }

    public void setPrizeTypeCode(String prizeTypeCode) {
        this.prizeTypeCode = prizeTypeCode;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(int winningNumber) {
        this.winningNumber = winningNumber;
    }

    public String getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(String winningAmount) {
        this.winningAmount = winningAmount;
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

    public String getOrganizationFullName() {
        return organizationFullName;
    }

    public void setOrganizationFullName(String organizationFullName) {
        this.organizationFullName = organizationFullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrganizationFlag() {
        return organizationFlag;
    }

    public void setOrganizationFlag(String organizationFlag) {
        this.organizationFlag = organizationFlag;
    }
}
