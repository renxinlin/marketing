package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import org.springframework.stereotype.Repository;

/**
 * 通用积分奖励配置
 */
@Repository
public interface IntegralRuleRepository        {
    /**
     * :删除old 通用配置
     * @param organizationId
     */
     void deleteOrganizationOldConfig(String organizationId);

    /**
     * 保存新的配置
     * @param integralRuleDomain
     */
    void saveNewOrganizationCommonConfig(IntegralRuleDomain integralRuleDomain);

    /**
     * 查看现有配置
     * @param organizationId
     * @return
     */
    IntegralRuleDomain commonIntegralRewardDetail(String organizationId);

    void deleteTermById(Integer id);
}
