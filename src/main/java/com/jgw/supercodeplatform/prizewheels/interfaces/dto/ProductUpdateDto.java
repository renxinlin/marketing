package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("参与活动的产品含id")
public class ProductUpdateDto {


    @NotEmpty(message = "产品id不为空")
    @ApiModelProperty("产品id") private String productId;

    @NotEmpty(message = "产品id不为空")
    @ApiModelProperty("产品名称") private String productName;

    @Valid
    @ApiModelProperty("产品批次") List<ProductBatchDto> productBatchParams;

    @NotNull(message = "活动不可为空") @Min(0)
    @ApiModelProperty("活动Id,大转盘取id1,activityId为7") private Long id;

}
