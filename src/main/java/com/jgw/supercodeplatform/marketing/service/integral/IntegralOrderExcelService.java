package com.jgw.supercodeplatform.marketing.service.integral;

import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.util.SerialNumberGenerator;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingDeliveryAddressParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单分页查询优化
 */
@Service
public class IntegralOrderExcelService extends AbstractPageService<IntegralOrder> {


    private static Logger logger = LoggerFactory.getLogger(IntegralExchangeService.class);

    @Autowired
    private IntegralOrderMapperExt mapper;

    @Override
    protected List<IntegralOrderPageParam> searchResult(IntegralOrder searchParams) throws Exception {
        // 返回格式修改
        return mapper.listWithExcel(searchParams);
    }

    @Override
    protected int count(IntegralOrder searchParams) throws Exception {
        return mapper.count(searchParams);
    }
    
    public void addPrizeOrder(MarketingDeliveryAddressParam marketingDeliveryAddressParam,IntegralOrder integralOrder) {
    	integralOrder.setOrderId(SerialNumberGenerator.generatorDateAndFifteenNumber());
    	integralOrder.setAddress(marketingDeliveryAddressParam.getProvince()+marketingDeliveryAddressParam.getCity()+marketingDeliveryAddressParam.getCountry()+marketingDeliveryAddressParam.getDetail());
    	integralOrder.setCreateDate(new Date());
    	integralOrder.setMobile(marketingDeliveryAddressParam.getMobile());
    	integralOrder.setName(marketingDeliveryAddressParam.getName());
    	integralOrder.setPrizeId(marketingDeliveryAddressParam.getPrizeId());
    	integralOrder.setPrizeName(marketingDeliveryAddressParam.getPrizeName());
    	integralOrder.setStatus((byte)0);
    	mapper.insertSelective(integralOrder);
    }
}
