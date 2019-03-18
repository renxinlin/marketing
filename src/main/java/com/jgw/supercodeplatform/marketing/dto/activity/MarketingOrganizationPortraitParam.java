package com.jgw.supercodeplatform.marketing.dto.activity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "企业画像设置model")
public class MarketingOrganizationPortraitParam {
	@ApiModelProperty(name = "unitCodeId", value = "编码表主键id", example = "1")
    private Long unitCodeId;//画像编码主键id
	
	@ApiModelProperty(name = "fieldWeight", value = "权重", example = "1")
    private Integer fieldWeight;//字段权重 用于控制页面显示顺序

    public MarketingOrganizationPortraitParam() {
    }

    public Long getUnitCodeId() {
		return unitCodeId;
	}

	public void setUnitCodeId(Long unitCodeId) {
		this.unitCodeId = unitCodeId;
	}


	public Integer getFieldWeight() {
        return fieldWeight;
    }

    public void setFieldWeight(Integer fieldWeight) {
        this.fieldWeight = fieldWeight;
    }
}
