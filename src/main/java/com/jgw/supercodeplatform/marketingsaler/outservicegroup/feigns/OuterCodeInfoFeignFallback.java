package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.OutCodeInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@Slf4j
public class OuterCodeInfoFeignFallback implements OuterCodeInfoFeign {

    @Override
    public RestResult<Object> getCurrentLevel(OutCodeInfoDto outCodeInfoDto) {
        log.error("码层级服务失败:调用微服务失败");
        return null;
    }
}
