package com.jgw.supercodeplatform.mutIntegral.domain.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleRewardCommonMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleRewardMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRule;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleReward;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleRewardCommon;
import org.springframework.beans.factory.annotation.Autowired;
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
