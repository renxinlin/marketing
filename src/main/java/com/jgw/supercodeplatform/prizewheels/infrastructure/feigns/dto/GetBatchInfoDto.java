package com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author by Tracy
 * @Classname GetBatchInfoDto
 * @Description 获取产品批次dto
 * @Date 2019/3/1 14:08
 */
@Data
public class GetBatchInfoDto {

    @ApiModelProperty(value = "产品id")
    @NotNull(message = "产品id不为空")
    private String productId;

    @ApiModelProperty(value = "产品批次id集合")
    private List<GetBatchInfoProductBatch> productBatchList;

}
