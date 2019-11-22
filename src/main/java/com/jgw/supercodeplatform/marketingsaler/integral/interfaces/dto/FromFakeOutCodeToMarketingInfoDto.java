package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("码和码制")
@AllArgsConstructor
@NoArgsConstructor
public class FromFakeOutCodeToMarketingInfoDto {
    @ApiModelProperty("码")
    private String outerCodeId;
    @ApiModelProperty("码制")
    private  String codeTypeId;

    @ApiModelProperty("需要的码制")
    private List<String> needCodeTypeIds;
}
