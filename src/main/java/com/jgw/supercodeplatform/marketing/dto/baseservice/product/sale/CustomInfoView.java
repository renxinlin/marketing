package com.jgw.supercodeplatform.marketing.dto.baseservice.product.sale;

import io.swagger.annotations.ApiModelProperty;

public class CustomInfoView {
    @ApiModelProperty(value = "自定义信息唯一标识")
    private String customInfoId;
    @ApiModelProperty(value = "字段名称")
    private String filedName;
    @ApiModelProperty( value = "字段内容")
    private String filedText;


    public String getCustomInfoId() {
        return customInfoId;
    }

    public void setCustomInfoId(String customInfoId) {
        this.customInfoId = customInfoId;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledText() {
        return filedText;
    }

    public void setFiledText(String filedText) {
        this.filedText = filedText;
    }
}
