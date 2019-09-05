package com.jgw.supercodeplatform.marketingsaler.integral.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.OutCodeInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.outservice.group.dto.ProductInfoByCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${platform.user.mircosoft.name:platform-user-supercode-test}",fallback = CodeManagerFallbackFeign.class )
public interface CodeManagerFeign {
    @RequestMapping(value = "/code/query/product",method = RequestMethod.GET)
    RestResult<ProductInfoByCodeDto> getProductByCode(OutCodeInfoDto outCodeInfoDto);

}
