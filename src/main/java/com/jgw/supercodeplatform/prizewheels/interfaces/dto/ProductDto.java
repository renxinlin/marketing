package com.jgw.supercodeplatform.prizewheels.interfaces.dto;

import javax.validation.constraints.NotEmpty;

public class ProductDto {
    @NotEmpty(message = "产品批次id不为空")

    private String productBatchId;
    @NotEmpty(message = "产品id不为空")
    private String productId;

}
