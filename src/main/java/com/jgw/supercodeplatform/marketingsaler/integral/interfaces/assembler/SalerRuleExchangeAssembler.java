package com.jgw.supercodeplatform.marketingsaler.integral.interfaces.assembler;

import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.SalerRuleExchangeDto;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.PrizeRulesVo;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.vo.SalerRuleExchangeVo;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleExchange;
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
         SalerRuleExchangeDto dto = modelMapper.map(vo, SalerRuleExchangeDto.class);
         dto.setIsRrandomMoney(vo.getPrizeRulesVo().getIsRrandomMoney());
         dto.setHighRand(vo.getPrizeRulesVo().getHighRand());
         dto.setLowRand(vo.getPrizeRulesVo().getLowRand());
         dto.setPrizeAmount(vo.getPrizeRulesVo().getPrizeAmount());
         if(vo.getExchangeStock() ==null){
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
        prizeRulesVo.setPrizeAmount(prizeRulesVo.getPrizeAmount());
        vo.setPrizeRulesVo(prizeRulesVo);
        return vo;
    }
}
