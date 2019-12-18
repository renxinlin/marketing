package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardAggDto;

import java.util.List;

public interface IntegralRuleRewardRepository {
     String ruleRewardAggDtos = "marketing:mutiintegralreward-snapshot:";

    /**
     * 积分奖励设置 会员导购门店 的写请求快照
     * @param ruleRewardAggDtos
     */
    void updateSnapshotInfo(List<IntegralRuleRewardAggDto> ruleRewardAggDtos);

    /**
     * 积分奖励设置 会员导购门店 的读请求快照
     * @param
     */
    List<IntegralRuleRewardAggDto>  getSnapshotInfo();
}
