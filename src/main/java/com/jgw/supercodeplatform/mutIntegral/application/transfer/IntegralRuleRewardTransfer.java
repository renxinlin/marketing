package com.jgw.supercodeplatform.mutIntegral.application.transfer;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.ChooseedIntegralConsants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.RewardTypeConstants;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardAggDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class IntegralRuleRewardTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public List<List<IntegralRuleRewardDomian>> transferDtoToDomain(List<IntegralRuleRewardAggDto> ruleRewardAggDtos, String organizationId, String organizationName) {
        log.info("IntegralRuleRewardTransfer transferDtoToDomain ruleRewardAggDtos=>{} ", JSONObject.toJSONString(ruleRewardAggDtos));
        if(CollectionUtils.isEmpty(ruleRewardAggDtos)){
            return new ArrayList<>();
        }
        List<IntegralRuleRewardDomian> ruleRewardDomians = new ArrayList<>(100);
        for(IntegralRuleRewardAggDto ruleRewardAggDto : ruleRewardAggDtos){
            // 积分信息
            Integer chooseedIntegral = ruleRewardAggDto.getChooseedIntegral();
            Integer sendIntegral = ruleRewardAggDto.getSendIntegral();
            Integer integralRuleType = ruleRewardAggDto.getIntegralRuleType();
            addIntegral(ruleRewardDomians,chooseedIntegral,sendIntegral,integralRuleType,organizationId,organizationName);

            // 红包信息
            List<IntegralRuleRewardDto> integralRuleRewardDtos = ruleRewardAggDto.getIntegralRuleRewardDtos();
            for(IntegralRuleRewardDto integralRuleRewardDto:integralRuleRewardDtos){
                IntegralRuleRewardDomian domain = modelMapper.map(integralRuleRewardDto, IntegralRuleRewardDomian.class);
                domain.setOrganizationName(organizationName);
                domain.setOrganizationId(organizationId);
                domain.setUnRewardFlag(RewardTypeConstants.rewardFlag);
                domain.setRewardType(RewardTypeConstants.reward_money);
                // todo 添加其他屬性
                ruleRewardDomians.add(domain);

            }
        }


         return null;

    }

    /**
     * 添加积分信息
     * @param ruleRewardDomians
     * @param chooseedIntegral
     * @param sendIntegral
     */
    private void addIntegral(List<IntegralRuleRewardDomian> ruleRewardDomians
            , Integer chooseedIntegral, Integer sendIntegral,Integer integralRuleType
            , String organizationId, String organizationName) {
        IntegralRuleRewardDomian domain = new IntegralRuleRewardDomian();
        domain.setOrganizationId(organizationId);
        domain.setOrganizationName(organizationName);
        domain.setChooseedIntegral(chooseedIntegral ==null ? ChooseedIntegralConsants.noChoose : chooseedIntegral);
        // 如果选择了积分送，则积分送的数值必须存在
        // todo 做了一步业务,方便处理
        if(domain.getChooseedIntegral() != ChooseedIntegralConsants.noChoose
        && sendIntegral == null ){
            throw new BizRuntimeException(MutiIntegralCommonConstants.nullError);
        }
        domain.setSendIntegral(sendIntegral);
        domain.setIntegralRuleType(integralRuleType);
        domain.setRewardType(RewardTypeConstants.reward_integral);
        ruleRewardDomians.add(domain);
    }
}
