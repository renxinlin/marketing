package com.jgw.supercodeplatform.marketingsaler.integral.domain.service;

import com.jgw.supercodeplatform.marketingsaler.base.service.SalerCommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.SalerRuleExchangeAutoUndercarriageEvent;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.mapper.SalerRuleExchangeMapper;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
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
