package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.IntegralRuleTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRepository;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class IntegralRuleApplication {


    // 翻译
    @Autowired
    private IntegralRuleTransfer integralRuleTransfer;


    // 领域

    // 基础设置
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private IntegralRuleRepository ruleCommonRepository;


    /**
     * 通用积分奖励设置
     *
     * @param integralRuleDto
     */
    @Transactional
    public void commonIntegralRewardSetting(IntegralRuleDto integralRuleDto) {
        // 转换数据
        String organizationId = commonUtil.getOrganizationId();
        IntegralRuleDomain integralRuleDomain = integralRuleTransfer
                .tranferDtoToDomain(integralRuleDto
                        , organizationId
                        , commonUtil.getOrganizationName()
                        , commonUtil.getUserLoginCache().getAccountId()
                        , commonUtil.getUserLoginCache().getUserName());
        log.info("积分设置{}", integralRuleDomain);

        // 执行业务1： 数据校验
        integralRuleDomain.checkWhenSetting();

        // 执行业务2： 刪除old配置
        ruleCommonRepository.deleteOrganizationOldConfig(organizationId);

        // 持久化新的配置
        ruleCommonRepository.saveNewOrganizationCommonConfig(integralRuleDomain);


    }

    /**
     * 获取持久化的配置信息
     * @return
     */
    public IntegralRuleDto commonIntegralRewardDetail() {
        String organizationId = commonUtil.getOrganizationId();
        IntegralRuleDomain domain = ruleCommonRepository.commonIntegralRewardDetail(organizationId);
        return integralRuleTransfer.tranferDomainToDto(domain);
    }

    /**
     * 获取持久化的配置信息
     * @return
     */
    public List<IntegralRuleRewardCommonVo> commonIntegralRewardList() {
        IntegralRuleDto integralRuleDto = commonIntegralRewardDetail();
        return integralRuleTransfer.tranferDtoToView(integralRuleDto);
    }

    public void deleteById(Integer id) {
         ruleCommonRepository.deleteTermById(id);
    }
}
