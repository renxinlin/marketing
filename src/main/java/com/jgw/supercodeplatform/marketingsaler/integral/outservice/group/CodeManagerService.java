package com.jgw.supercodeplatform.marketingsaler.integral.outservice.group;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.feigns.CodeManagerFeign;
import com.jgw.supercodeplatform.marketingsaler.integral.outservice.group.dto.ProductInfoByCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CodeManagerService {
    @Autowired
    private CodeManagerFeign codeManagerFeign;

    public ProductInfoByCodeDto getProductByCode(OutCodeInfoDto outCodeInfoDto){
        RestResult<ProductInfoByCodeDto> restResult = codeManagerFeign.getProductByCode(outCodeInfoDto);
        if(restResult == null ){
            return null;
        }
        if(restResult.getResults() == null ){
            return null;
        }
        return restResult.getResults();
    }
}
