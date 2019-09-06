package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;

public class CodeManagerFallbackFeign implements CodeManagerFeign{
    // 返回对象和boolean两种格式
    @Override
    public RestResult<Object> getProductByCode(OutCodeInfoDto outCodeInfoDto) {
        return null;
    }
}
