package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.SbatchTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.SbatchBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SbatchRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SbatchDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SbatchBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSbatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class IntegralSBatchApplication {
    // 领域服务
    @Autowired
    private SbatchDomainService sbatchDomainService;

    @Autowired
    private SbatchBindBizPublisher sbatchBindBizPublisher;

    @Autowired
    private SbatchBindBizSubscriber sbatchBindBizSubscriber;

    // 翻译
    @Autowired
    private SbatchTransfer sbatchTransfer;


    // 基础设施
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SbatchRepository sbatchRepository;



    public void setsBatchForIntegralRule(List<IntegralSbatchDto> sbatchDtos) {
        //  根据营销码删除这批码相关的码
        sbatchRepository.deleteOldSetting();

        // 数据获取与转换
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        List<IntegralSbatchDomain> sbatchDomains = sbatchTransfer.transferDtoToDomain(sbatchDtos,organizationId,organizationName);
        if(CollectionUtils.isEmpty(sbatchDomains)){
            //  码管理校验: 校验号段码
            sbatchDomainService.judgeCodeCanbeSettingByIntegralFromCodeManager(sbatchDomains);
            // 持久化配置信息
            sbatchRepository.saveNewSetting(sbatchDomains);
        }


        // 发送单码业务事件 该事件用于码管理接口获取业务
        sbatchBindBizPublisher.addSubscriber(sbatchBindBizSubscriber);
        sbatchBindBizPublisher.publish(new SbatchBindBizEvent(sbatchDomains));

    }
}
