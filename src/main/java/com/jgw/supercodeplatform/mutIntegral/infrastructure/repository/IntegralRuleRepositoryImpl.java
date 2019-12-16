package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.IntegralRuleTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardCommonDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralRuleRewardCommonService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleRewardCommonMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRule;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleRewardCommon;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.IntegralRulePojoTransfer;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class IntegralRuleRepositoryImpl implements IntegralRuleRepository {
    @Autowired
    private IntegralRuleMapper mapper;

    @Autowired
    private IntegralRuleRewardCommonMapper commonMapper;

    @Autowired
    private IntegralRuleRewardCommonService commonService;


    @Autowired
    private IntegralRulePojoTransfer integralRulePojoTransfer;

    @Override
    public void deleteOrganizationOldConfig(String organizationId) {
        LambdaQueryWrapper<IntegralRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IntegralRule::getOrganizationId, organizationId);
        mapper.delete(queryWrapper);


        LambdaQueryWrapper<IntegralRuleRewardCommon> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(IntegralRuleRewardCommon::getOrganizationId, organizationId);
        commonMapper.delete(queryWrapper1);
    }

    @Override
    public void saveNewOrganizationCommonConfig(IntegralRuleDomain integralRuleDomain) {
        IntegralRule integralRule = integralRulePojoTransfer.transferDomainToPojo(integralRuleDomain);
        mapper.insert(integralRule);

        List<IntegralRuleRewardCommonDomain> integralRuleRewardCommonDomains = integralRuleDomain.getIntegralRuleRewardCommonDomains();
        List<IntegralRuleRewardCommon> commonRules = integralRulePojoTransfer.transferCommonDomainsToPojos(integralRuleRewardCommonDomains);
        commonService.saveBatch(commonRules);
    }

    @Override
    public IntegralRuleDomain commonIntegralRewardDetail(String organizationId) {
        LambdaQueryWrapper<IntegralRule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IntegralRule::getOrganizationId, organizationId);
        IntegralRule integralRule = mapper.selectOne(queryWrapper);


        LambdaQueryWrapper<IntegralRuleRewardCommon> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(IntegralRuleRewardCommon::getOrganizationId, organizationId);
        List<IntegralRuleRewardCommon> commonsRules = commonMapper.selectList(queryWrapper1);

        IntegralRuleDomain integralRuleDomain = integralRulePojoTransfer.transferPojoToDomain(integralRule, commonsRules);
        return integralRuleDomain;
    }

    @Override
    public void deleteTermById(Integer id) {
        int i = commonMapper.deleteById(id);
        Asserts.check(i==1, MutiIntegralCommonConstants.deleteSqlError);

    }
}
