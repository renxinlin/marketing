package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.FromFakeOutCodeToMarketingInfoDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CodeManagerFromFadeToMarketingFallbackFeign  implements CodeManagerFromFadeToMarketingFeign{
    @Override
    public RestResult<Object> getCodeManagerFromFadeToMarketingByCode(FromFakeOutCodeToMarketingInfoDto outCodeInfoDto) {
        log.error("调用码平台=》防伪转营销码失败{}", JSONObject.toJSONString(outCodeInfoDto));
        return null;
    }
}
