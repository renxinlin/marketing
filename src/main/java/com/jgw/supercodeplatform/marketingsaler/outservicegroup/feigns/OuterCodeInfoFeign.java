package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${platform.code.mircoservice.name:platform-ms-code-dev}",fallback = CodeManagerFallbackFeign.class )
public interface OuterCodeInfoFeign {
    @RequestMapping(value = "/outer/info/getCurrentLevel",method = RequestMethod.GET)
    RestResult<Object> getCurrentLevel(OutCodeInfoDto outCodeInfoDto);

}
