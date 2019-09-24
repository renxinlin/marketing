package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto.CustomerInfoView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BaseCustomerFeignFallbaclService implements BaseCustomerFeignService {
    @Override
    public RestResult<CustomerInfoView> getCustomerInfo(CustomerIdDto customerId) {
        log.error("获取门店信息失败{}",customerId);
        return null;
    }
}
