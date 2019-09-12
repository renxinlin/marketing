package com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.SalerRuleExchangeAutoUndercarriageEvent;
import com.jgw.supercodeplatform.marketingsaler.integral.application.group.dto.ProductInfoByCodeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleReward;

public class H5SalerRuleExchangeTransfer {

    public static SalerRuleExchangeAutoUndercarriageEvent buildAutoUndercarriageEvent(Long eventId) {
        SalerRuleExchangeAutoUndercarriageEvent event = new SalerRuleExchangeAutoUndercarriageEvent();
        event.setId(eventId);
        return event;
    }

    public static Wrapper<SalerRuleExchange> reduceStock(SalerRuleExchange updateDo ,SalerRuleExchange params) {
        UpdateWrapper<SalerRuleExchange> updateWrapper = new UpdateWrapper<>(updateDo);
        // 预减一个库存
        updateWrapper.apply("HaveStock = HaveStock - {0} where HaveStock > 0 ",1).set("Id",params.getId());        return updateWrapper;

    }

    public static Wrapper<SalerRuleExchange> reducePreStock(SalerRuleExchange updateDo, SalerRuleExchange params) {
        UpdateWrapper<SalerRuleExchange> updateWrapper = new UpdateWrapper<>(updateDo);
        // 减一个库存
        // update table set x = x -1 where x - 1 > 0 and id  = 1 PreHaveStock = PreHaveStock - {0}
        updateWrapper.apply("  PreHaveStock -{} > 0 and Id = #{1} ",1,params.getId()).set("payWay "," 1 , PreHaveStock = PreHaveStock - 1" );


        return updateWrapper;
    }

    public static SalerRuleReward getRewardValueObject(ProductInfoByCodeDto productByCode) {
        SalerRuleReward reward = new SalerRuleReward();
        reward.setProductId(productByCode.getProductId());
        reward.setProductName(productByCode.getProductName());
        return reward;
    }
}
