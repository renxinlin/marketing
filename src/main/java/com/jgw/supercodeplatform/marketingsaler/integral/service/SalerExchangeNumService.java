package com.jgw.supercodeplatform.marketingsaler.integral.service;


import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerExchangeNumMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerExchangeNum;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import org.apache.http.util.Asserts;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author renxinlin
 * @since 2019-09-04
 */
@Service
public class SalerExchangeNumService extends SalerCommonService<SalerExchangeNumMapper, SalerExchangeNum> {


    public void canExchange(H5LoginVO user, SalerRuleExchange salerRuleExchange) {
        if(salerRuleExchange!=null && salerRuleExchange.getCustomerLimitNum() !=null ){
            int exchangeNum = baseMapper.selectCount(query().eq("OrganizationId", salerRuleExchange.getOrganizationId())
                    .eq("UserId", user.getMemberId())
                    .eq("exchangeId", salerRuleExchange.getOrganizationId()));
            Asserts.check(salerRuleExchange.getCustomerLimitNum().intValue() > exchangeNum ,"你已达到兑换上限");
        }

    }
}
