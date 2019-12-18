package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRewardRepository;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardAggDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class IntegralRuleRewardRepositoryImpl implements IntegralRuleRewardRepository {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonUtil commonUtil;
    @Override
    public void updateSnapshotInfo(List<IntegralRuleRewardAggDto> ruleRewardAggDtos) {

        redisUtil.set(ruleRewardAggDtos+commonUtil.getOrganizationId(), JSONObject.toJSONString(ruleRewardAggDtos));
    }


    @Override
    public List<IntegralRuleRewardAggDto>  getSnapshotInfo() {
        String integralRuleRewardAggDtoStr = redisUtil.get(ruleRewardAggDtos+commonUtil.getOrganizationId());
        if(StringUtils.isEmpty(integralRuleRewardAggDtoStr)){
            return  new ArrayList<>();
        }
        // TODO 转换成dto
        // TODO 转换成dto
        // TODO 转换成dto // TODO 转换成dto
        // TODO 转换成dto
        List<IntegralRuleRewardAggDto> integralRuleRewardAggDtos = JSONObject.parseArray(integralRuleRewardAggDtoStr, IntegralRuleRewardAggDto.class);
        return  integralRuleRewardAggDtos;
    }
}
