package com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleReward;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
public class MutiRewardTransfer  extends MutiIntegralCommonTransfer{

    public List<IntegralRuleReward> transferRewardDomiansToPojos(List<List<IntegralRuleRewardDomian>> ruleRewardDomainWithTypes) {
        List<IntegralRuleReward> pojos =  new ArrayList<>();
        if(CollectionUtils.isEmpty(ruleRewardDomainWithTypes)){
            return pojos;
        }
        for (List<IntegralRuleRewardDomian> ruleRewardDomains: ruleRewardDomainWithTypes){
            if(CollectionUtils.isEmpty(ruleRewardDomains)) {continue;}
            for (IntegralRuleRewardDomian ruleRewardDomain:ruleRewardDomains){
                IntegralRuleReward  rewardPojo =  modelMapper.map(ruleRewardDomain,IntegralRuleReward.class);
                // TODO 其他属性补充
                pojos.add(rewardPojo);

            }
        }
        return pojos;
    }
}
