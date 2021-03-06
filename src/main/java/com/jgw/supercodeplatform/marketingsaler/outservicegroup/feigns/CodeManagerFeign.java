package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${platform.codemanager.mircosoft.name:platform-codemanager-supercode-dev}",fallback = CodeManagerFallbackFeign.class )
public interface CodeManagerFeign {
    // object 会返回2种类型
    @RequestMapping(value = "/code/query/product",method = RequestMethod.GET)
    RestResult<Object> getProductByCode(OutCodeInfoDto outCodeInfoDto);

}
