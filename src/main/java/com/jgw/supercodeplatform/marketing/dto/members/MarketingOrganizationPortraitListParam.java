package com.jgw.supercodeplatform.marketing.dto.members;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "会员画像")
public class MarketingOrganizationPortraitListParam {

    @ApiModelProperty(value = "Id")
    private int id;//序列
    @ApiModelProperty(value = "组织Id")
    private String organizationId;//组织Id
    @ApiModelProperty(value = "组织")
    private String organizationFullName;//组织
    @ApiModelProperty(value = "画像编码")
    private String portraitCode;//画像编码
    @ApiModelProperty(value = "画像名称")
    private String portraitName;//画像名称
    @ApiModelProperty(value = "字段权重 用于控制页面显示顺序")
    private int fieldWeight;//字段权重 用于控制页面显示顺序
    @ApiModelProperty(value = "类型")
    private int typeId;//类型

    public MarketingOrganizationPortraitListParam() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPortraitCode() {
        return portraitCode;
    }

    public void setPortraitCode(String portraitCode) {
        this.portraitCode = portraitCode;
    }

    public String getPortraitName() {
        return portraitName;
    }

    public void setPortraitName(String portraitName) {
        this.portraitName = portraitName;
    }

    public int getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(int fieldWeight) {
        this.fieldWeight = fieldWeight;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
