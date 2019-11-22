package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.FromFakeOutCodeToMarketingInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${platform.code.mircoservice.name:platform-ms-codetest}",fallback = CodeManagerFromFadeToMarketingFallbackFeign.class )
public interface CodeManagerFromFadeToMarketingFeign {
    // object 会返回2种类型
    @RequestMapping(value = "/outer/info/inner/need",method = RequestMethod.POST)
    RestResult<Object> getCodeManagerFromFadeToMarketingByCode(FromFakeOutCodeToMarketingInfoDto outCodeInfoDto);

}
