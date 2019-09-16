package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;


import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.SalerExchangeNumMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerExchangeNum;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;
import org.apache.commons.lang.StringUtils;
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


    public void canExchange(User user, SalerRuleExchange salerRuleExchange) {
        Asserts.check(user !=null,"用户数据获取失败");

        if(salerRuleExchange!=null && salerRuleExchange.getCustomerLimitNum() !=null ){
            int exchangeNum = baseMapper.selectCount(query().eq("OrganizationId", salerRuleExchange.getOrganizationId())
                    .eq("UserId", user.getId())
                    .eq("exchangeId", salerRuleExchange.getId()).getWrapper());
            Asserts.check(salerRuleExchange.getCustomerLimitNum().intValue() > exchangeNum ,"你已达到兑换上限");
        }

    }
}
