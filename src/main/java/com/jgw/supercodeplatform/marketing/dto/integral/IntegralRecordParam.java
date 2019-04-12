package com.jgw.supercodeplatform.marketing.dto.integral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "积分记录")
public class IntegralRecordParam {
    /** 主键 */
    @ApiModelProperty(value = "主键")
    private Long id;

    /** 0会员 */
    @ApiModelProperty(value = "会员类型0会员")
    private Byte memberType;

    /** 会员id */
    @ApiModelProperty(value = "会员id")
    private Long memberId;

    /** 会员名称 */
    @ApiModelProperty(value = "会员名称")
    private String memberName;

    /** 手机 */
    @ApiModelProperty(value = "手机")
    private String mobile;

    /** 奖品增减原因编码 */
    @ApiModelProperty(value = "奖品增减原因编码")
    private Integer integralReasonCode;

    /** 奖品增减原因 */
    @ApiModelProperty(value = "奖品增减原因")
    private String integralReason;

    /** 产品id */
    @ApiModelProperty(value = "产品id")
    private String productId;

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /** 码信息 */
    @ApiModelProperty(value = "码信息")
    private String outerCodeId;

    /** 码信息 */
    @ApiModelProperty(value = "码信息")
    private String codeTypeId;

    /** 注册门店【h5注册的门店信息】 */
    @ApiModelProperty(value = "注册门店【h5注册的门店信息】")
    private String customerName;

    /** 注册门店id */
    @ApiModelProperty(value = "注册门店id")
    private String customerId;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    /** 组织id */
    @ApiModelProperty(value = "组织id")
    private String organizationId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    /** 积分增减数值 */
    @ApiModelProperty(value = "积分增减数值")
    private Integer integralNum;


    /** 积分类型 */
    @ApiModelProperty(value = "积分类型|null所有,0奖励,1消耗")
    private Integer integralType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getMemberType() {
        return memberType;
    }

    public void setMemberType(Byte memberType) {
        this.memberType = memberType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getIntegralReasonCode() {
        return integralReasonCode;
    }

    public void setIntegralReasonCode(Integer integralReasonCode) {
        this.integralReasonCode = integralReasonCode;
    }

    public String getIntegralReason() {
        return integralReason;
    }

    public void setIntegralReason(String integralReason) {
        this.integralReason = integralReason;
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

    public String getOuterCodeId() {
        return outerCodeId;
    }

    public void setOuterCodeId(String outerCodeId) {
        this.outerCodeId = outerCodeId;
    }

    public String getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(String codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Integer getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }

    public Integer getIntegralType() {
        return integralType;
    }

    public void setIntegralType(Integer integralType) {
        this.integralType = integralType;
    }
}
