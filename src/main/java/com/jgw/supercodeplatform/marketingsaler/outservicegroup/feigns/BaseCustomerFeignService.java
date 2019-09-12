package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto.CustomerInfoView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 门店
 */
@FeignClient(name = "${platform.user.mircosoft.name:platform-user-supercode-dev}",fallback = BaseCustomerFeignFallbaclService.class )
public interface BaseCustomerFeignService {
    /**
     *
     * @param customerId
     * @param0 phoneNumber
     * @param1 code
     * @return
     */
    @RequestMapping(value = "/customer/no-filter" ,method = RequestMethod.GET)
    RestResult<CustomerInfoView> getCustomerInfo(CustomerIdDto customerId);

}
