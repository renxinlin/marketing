package com.jgw.supercodeplatform.marketingsaler.integral.service;

import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.dto.SalerRuleExchangeAutoUndercarriageEvent;
import com.jgw.supercodeplatform.marketingsaler.integral.mapper.SalerRuleExchangeMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
import org.springframework.stereotype.Service;

/**
 * 导购兑换红包自动下架
 */
@Service
public class AutoUndercarriageService  extends SalerCommonService<SalerRuleExchangeMapper, SalerRuleExchange> {
    public void listenAutoUnder(SalerRuleExchangeAutoUndercarriageEvent event) {
        SalerRuleExchange entity = new SalerRuleExchange();
        entity.setId(event.getId());
        entity.setStatus(event.getStatus());
        baseMapper.updateById(entity);
    }
}
