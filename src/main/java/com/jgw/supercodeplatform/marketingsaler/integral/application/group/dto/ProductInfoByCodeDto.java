package com.jgw.supercodeplatform.marketingsaler.integral.application.group.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductInfoByCodeDto {
    private String organizationId;
    private String productId;
    private String productBatchId;
    private String organizationFullName;
    private String productBatch;
    private String productName;

}
