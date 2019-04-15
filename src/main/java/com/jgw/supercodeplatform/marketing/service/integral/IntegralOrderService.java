package com.jgw.supercodeplatform.marketing.service.integral;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralOrderMapperExt;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * web订单管理
 */
@Service
public class IntegralOrderService extends AbstractPageService<IntegralOrder> {

    @Autowired
    private IntegralOrderMapperExt mapper;

    @Override
    public List<IntegralOrderPageParam> searchResult(IntegralOrder searchParams) throws SuperCodeException {

        return mapper.list(searchParams);
    }

    @Override
    public int count(IntegralOrder searchParams) throws SuperCodeException {
        return mapper.count(searchParams);
    }

    /**
     * 发货
     * @param orderId
     * @param organizationId
     */
    public void updateDeliveryStatus(Long orderId, String organizationId) throws SuperCodeException {
        if(StringUtils.isBlank(organizationId)){
            throw new SuperCodeException("组织不存在", 500);
        }
        if(orderId == null || orderId <= 0){
            throw new SuperCodeException("订单不存在", 500);
        }
        IntegralOrder integralOrder = mapper.selectByPrimaryKey(orderId);
        if(organizationId.equals(integralOrder.getOrganizationId())){
            // 发货状态
            integralOrder.setStatus((byte)1);
            int i = mapper.updateByPrimaryKeySelective(integralOrder);
            if(i != 1){
                throw  new SuperCodeException("发货状态修改失败",500);
            }

        }else{
            throw new SuperCodeException("非当前组织订单",500);
        }

    }
}
