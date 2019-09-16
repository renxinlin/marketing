package com.jgw.supercodeplatform.marketingsaler.integral.application.group;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.dto.CustomerInfoView;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.BaseCustomerFeignService;
import com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns.CustomerIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 门店基础服务接口
 */
@Service
public class BaseCustomerService {
    @Autowired
    private BaseCustomerFeignService baseCustomerFeignService;

    /**
     * customerId
     * @param customerId
     * @return
     */
    public CustomerInfoView getCustomerInfo(String customerId){
        RestResult<CustomerInfoView> customerInfo = baseCustomerFeignService.getCustomerInfo(new CustomerIdDto(customerId));
        if(customerInfo!=null && customerInfo.getState() ==200){
            return customerInfo.getResults();
        }
        return null;
    }

}
