package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("参与活动的产品含id")
public class ProductUpdateDto {

    @NotEmpty(message = "产品批次id不为空")
    @ApiModelProperty("产品批次")  private String productBatchId;

    @NotEmpty(message = "产品批次不为空")
    @ApiModelProperty("产品批次") private String productBatchName;

    @NotEmpty(message = "产品id不为空")
    @ApiModelProperty("产品id") private String productId;

    @NotEmpty(message = "产品id不为空")
    @ApiModelProperty("产品名称") private String productName;

//
//    @NotEmpty(message = "id不可为空") @Min(0)
//    @ApiModelProperty("id") private Long id;


    @NotNull(message = "活动不可为空") @Min(0)
    @ApiModelProperty("activitySetId") private Long activitySetId;

}
