package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.IntegralRuleRewardTransfer;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.IntegralRuleTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.IntegralRuleRewardDomianServie;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRewardRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.forkjoin.ParallelMessageTask;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.pojo.IntegralMessageInfo;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardAggDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Service
@Slf4j
public class IntegralRuleApplication {


    // 翻译
    @Autowired
    private IntegralRuleTransfer integralRuleTransfer;

    @Autowired
    private IntegralRuleRewardTransfer ruleRewardTransfer;

    // 领域
    @Autowired
    private IntegralRuleRewardDomianServie rewardDomianServie;

    // 基础设置
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private IntegralRuleRepository ruleCommonRepository;



    @Autowired
    private IntegralRuleRewardRepository rewardRepository;






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

    /**
     * 保存并设置积分奖励;包括积分红包
     * @param ruleRewardAggDtos
     */
    @Transactional
    public void saveIntegral(List<IntegralRuleRewardAggDto> ruleRewardAggDtos) {
        Asserts.check(CollectionUtils.isEmpty(ruleRewardAggDtos), MutiIntegralCommonConstants.nullError);
        // 需要产生一份未中奖信息
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        //  外层的list是 会员 门店 渠道 经销商[1~n] 内层是配置的积分红包列表信息
        List<List<IntegralRuleRewardDomian>>  ruleRewardDomains = ruleRewardTransfer.transferDtoToDomain(ruleRewardAggDtos,organizationId,organizationName);
        rewardDomianServie.checkMoney(ruleRewardDomains);
        rewardDomianServie.checkdealer(ruleRewardDomains);
        rewardDomianServie.buildUnRewardInfo(ruleRewardDomains);


        rewardRepository.deleteOldByOrganization();
        rewardRepository.saveintegralRewardInfo(ruleRewardDomains);


        // TODO 产生读请求快照
        rewardRepository.updateSnapshotInfo(ruleRewardAggDtos);

    }

    public List<IntegralRuleRewardAggDto> getSaved(){
        List<IntegralRuleRewardAggDto> snapshotInfo = rewardRepository.getSnapshotInfo();
        return snapshotInfo;
    }



    // todo 领域层完成
    @Autowired
    private ForkJoinPool forkJoinPool;

    public void sendMessageAfterConfig() {
        // 开启异步

        // 获取配置信息

        // 如果发送
        // 发送全部导购员
        // 发送全部渠道
        List<IntegralMessageInfo> list = new ArrayList();
        ParallelMessageTask parallelMessageTask = new ParallelMessageTask(list);
        forkJoinPool.execute(parallelMessageTask);
    }
}
