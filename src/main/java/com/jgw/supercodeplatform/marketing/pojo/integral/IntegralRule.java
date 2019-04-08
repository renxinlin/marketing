package com.jgw.supercodeplatform.marketing.pojo.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(value = "通用积分规则")
public class IntegralRule {
    /** 积分活动主表 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 积分有效期状态位0永久有效1存在有效期 */
    @ApiModelProperty(value = "积分有效期状态位0永久有效1存在有效期")
    private Boolean timeLimitStatus;

    /** 从奖励开始多久后过期 */
    @ApiModelProperty(value = "从奖励开始多久后过期")
    private Date timeLimit;

    /** 积分上限状态位0无上限，1每人每日最多获得的积分上限目前0有效 */
    @ApiModelProperty(value = "积分上限状态位0无上限，1每人每日最多获得的积分上限目前0有效")
    private Boolean integralLimitStatus;

    /** 每人每日最多获得的积分上限 */
    @ApiModelProperty(value = "每人每日最多获得的积分上限")
    private Integer integralLimit;

    /** 每人每【2年1月0日】获取积分上限 */
    @ApiModelProperty(value = "每人每【2年1月0日】获取积分上限")
    private Boolean integralLimitAge;

    /** 额外送:注册 */
    @ApiModelProperty(value = "额外送:注册")
    private Integer integralByRegister;

    /** 额外送:生日 */
    @ApiModelProperty(value = "额外送:生日")
    private Integer integralByBirthday;

    /** 历史首次 */
    @ApiModelProperty(value = "历史首次")
    private Integer integralByFirstTime;

    /** 额外送注册状态：0勾选有效1无效 */
    @ApiModelProperty(value = "额外送注册状态：0勾选有效1无效")
    private Boolean integralByRegisterStatus;

    /** 额外送生日状态：0勾选有效1无效 */
    @ApiModelProperty(value = "额外送生日状态：0勾选有效1无效")
    private Boolean integralByBirthdayStatus;

    /** 额外历史首次送状态：0勾选有效1无效 */
    @ApiModelProperty(value = "额外历史首次送状态：0勾选有效1无效")
    private Boolean integralByFirstTimeStatus;

    /**  */
    @ApiModelProperty(value = "创建人id")
    private Integer createUserId;

    /**  */
    @ApiModelProperty(value = "创建人")
    private String createUserName;

    /**  */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /**  */
    @ApiModelProperty(value = "更新人ID")
    private Integer updateUserId;

    /**  */
    @ApiModelProperty(value = "更新人")
    private String updateUserName;

    /**  */
    @ApiModelProperty(value = "更新日期")
    private Date updateUserDate;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    /** 积分通用规则0有效1无效 */
    @ApiModelProperty(value = " 积分通用规则0有效1无效 ")
    private Byte isEffective;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTimeLimitStatus() {
        return timeLimitStatus;
    }

    public void setTimeLimitStatus(Boolean timeLimitStatus) {
        this.timeLimitStatus = timeLimitStatus;
    }

    public Date getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Date timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Boolean getIntegralLimitStatus() {
        return integralLimitStatus;
    }

    public void setIntegralLimitStatus(Boolean integralLimitStatus) {
        this.integralLimitStatus = integralLimitStatus;
    }

    public Integer getIntegralLimit() {
        return integralLimit;
    }

    public void setIntegralLimit(Integer integralLimit) {
        this.integralLimit = integralLimit;
    }

    public Boolean getIntegralLimitAge() {
        return integralLimitAge;
    }

    public void setIntegralLimitAge(Boolean integralLimitAge) {
        this.integralLimitAge = integralLimitAge;
    }

    public Integer getIntegralByRegister() {
        return integralByRegister;
    }

    public void setIntegralByRegister(Integer integralByRegister) {
        this.integralByRegister = integralByRegister;
    }

    public Integer getIntegralByBirthday() {
        return integralByBirthday;
    }

    public void setIntegralByBirthday(Integer integralByBirthday) {
        this.integralByBirthday = integralByBirthday;
    }

    public Integer getIntegralByFirstTime() {
        return integralByFirstTime;
    }

    public void setIntegralByFirstTime(Integer integralByFirstTime) {
        this.integralByFirstTime = integralByFirstTime;
    }

    public Boolean getIntegralByRegisterStatus() {
        return integralByRegisterStatus;
    }

    public void setIntegralByRegisterStatus(Boolean integralByRegisterStatus) {
        this.integralByRegisterStatus = integralByRegisterStatus;
    }

    public Boolean getIntegralByBirthdayStatus() {
        return integralByBirthdayStatus;
    }

    public void setIntegralByBirthdayStatus(Boolean integralByBirthdayStatus) {
        this.integralByBirthdayStatus = integralByBirthdayStatus;
    }

    public Boolean getIntegralByFirstTimeStatus() {
        return integralByFirstTimeStatus;
    }

    public void setIntegralByFirstTimeStatus(Boolean integralByFirstTimeStatus) {
        this.integralByFirstTimeStatus = integralByFirstTimeStatus;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateUserDate() {
        return updateUserDate;
    }

    public void setUpdateUserDate(Date updateUserDate) {
        this.updateUserDate = updateUserDate;
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

    public Byte getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Byte isEffective) {
        this.isEffective = isEffective;
    }
}