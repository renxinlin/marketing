package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("码和码制")
@AllArgsConstructor
@NoArgsConstructor
public class OutCodeInfoVo {
    @ApiModelProperty("码")
    private String outerCodeId;
    @ApiModelProperty("码制")
    private  String codeTypeId;

    @ApiModelProperty("单码层级1")
    private Long level;

    private Long sBatchId;
}
