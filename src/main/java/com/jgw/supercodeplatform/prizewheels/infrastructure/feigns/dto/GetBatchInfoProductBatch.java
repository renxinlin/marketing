package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author by Tracy
 * @Classname GetBatchInfoProductBatch
 * @Description 获取批次信息的产品批次条件
 * @Date 2019/3/1 14:09
 */
@Data
public class GetBatchInfoProductBatch {

    @ApiModelProperty(value = "产品批次")
    private String productBatchId;

}
