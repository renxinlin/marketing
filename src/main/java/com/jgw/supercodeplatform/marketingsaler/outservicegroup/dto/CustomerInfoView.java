package com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerInfoView {
    private Long id;
    @ApiModelProperty("客户id")
    private String customerId;
    @ApiModelProperty("社会统一代码")
    private String socUnifiedCreditCode;
    @ApiModelProperty("客户名")
    private String customerName;
    @ApiModelProperty("客户类型：0-渠道经销；1-门店；2-个人")
    private Integer customerType;
    private String customerTypeName;
    @ApiModelProperty("客户编码")
    private String customerCode;
    @ApiModelProperty("上级客户id")
    private String customerSuperior;
    @ApiModelProperty("上级客户名称")
    private String customerSuperiorName;
    @ApiModelProperty("上级客户类型:0-经销商1-门店；2--个人；3-公司本部；")
    private String customerSuperiorType;
    @ApiModelProperty("所在地")
    private String location;
    @ApiModelProperty("省")
    private String provinceName;
    @ApiModelProperty("市")
    private String cityName;
    @ApiModelProperty("县、区")
    private String countyName;
    @ApiModelProperty("乡镇")
    private String townShipName;
    @ApiModelProperty("详细地址")
    private String detailedAddress;
    @ApiModelProperty("联系人")
    private String contacts;
    @ApiModelProperty("联系方式")
    private String contactDetails;
    @ApiModelProperty("禁用启用")
    private Integer disableFlag;
    private String disableFlagName;
    @ApiModelProperty("分销级别")
    private String distributionLevel;
    @ApiModelProperty("组织id")
    private String organizationId;
    @ApiModelProperty("组织名")
    private String organizationName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    private String firstCustomerId;
    @ApiModelProperty("经销区域")
    List<String> distributionArea;
    private Integer mkwarehousing;

}
