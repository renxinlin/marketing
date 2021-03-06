package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.assembler;

import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.PrizeRulesVo;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.SalerRuleExchangeVo;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * VO,DTO 构建者
 * 并不细化DTO ,Vo的职责！ 默认VO为特殊的DTO
 */
@Component
public class SalerRuleExchangeAssembler {
    @Autowired
    private ModelMapper modelMapper;
     public SalerRuleExchangeDto fromWeb(SalerRuleExchangeVo vo) {
         if(vo.getHaveStock() == null ){
             vo.setHaveStock(UserConstants.defaultStock);
         }
         if(vo.getCustomerLimitNum() == null){
             vo.setCustomerLimitNum(UserConstants.defaulttCustomerLimitNum);

         }
         Asserts.check(vo.getHaveStock()>= 0,"库存必须大于0");
         Asserts.check(vo.getCustomerLimitNum()>= 0,"限兑次数大于0");
         SalerRuleExchangeDto dto = modelMapper.map(vo, SalerRuleExchangeDto.class);

         dto.setIsRrandomMoney(vo.getPrizeRulesVo().getIsRrandomMoney());
         dto.setHighRand(vo.getPrizeRulesVo().getHighRand());
         dto.setLowRand(vo.getPrizeRulesVo().getLowRand());
         dto.setPrizeAmount(vo.getPrizeRulesVo().getPrizeAmount());
         if(vo.getHaveStock() ==null){
             dto.setHaveStock(UserConstants.defaultStock);
         }
         return dto;
    }

    public SalerRuleExchangeVo toWeb(SalerRuleExchange pojo) {
        SalerRuleExchangeVo vo = modelMapper.map(pojo, SalerRuleExchangeVo.class);
        PrizeRulesVo prizeRulesVo = new PrizeRulesVo();
        prizeRulesVo.setHighRand(pojo.getHighRand());
        prizeRulesVo.setLowRand(pojo.getLowRand());
        prizeRulesVo.setIsRrandomMoney(pojo.getIsRrandomMoney());
        prizeRulesVo.setPrizeAmount(pojo.getPrizeAmount());
        vo.setPrizeRulesVo(prizeRulesVo);
        return vo;
    }
}
