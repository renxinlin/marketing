package com.jgw.supercodeplatform.marketingsaler.integral.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.outservice.group.dto.ProductInfoByCodeDto;

import java.util.Map;

public class CodeManagerFallbackFeign implements CodeManagerFeign{
    @Override
    public RestResult<ProductInfoByCodeDto> getProductByCode(OutCodeInfoDto outCodeInfoDto) {
        return null;
    }
}
