package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.SegmentCodeTransfer;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.SingleCodeTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SegmentCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.SegmentCodeBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.SingleCodeBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SegmentCodeRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SingleCodeRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SegmentCodeDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SingleCodeDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SegmentCodeBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SingleCodeBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSegmentCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class IntegralSegmentCodeApplication {
    // 领域服务
    @Autowired
    private SegmentCodeDomainService segmentCodeDomainService;

    @Autowired
    SegmentCodeBindBizPublisher segmentCodeBindBizPublisher;

    @Autowired
    SegmentCodeBindBizSubscriber segmentCodeBindBizSubscriber;

    // 翻译
    @Autowired
    private SegmentCodeTransfer segmentCodeTransfer;


    // 基础设施
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SegmentCodeRepository segmentCodeRepository;

    public void setsegmentCodeForIntegralRule(List<IntegralSegmentCodeDto> segmentCodeDtos) {
        // 哨兵校验
        if(CollectionUtils.isEmpty(segmentCodeDtos)){
            return;
        }
        // 数据获取与转换
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        List<IntegralSegmentCodeDomain> segmentCodeDomains = segmentCodeTransfer.transferDtoToDomain(segmentCodeDtos,organizationId,organizationName);

        //  码管理校验: 校验号段码
        segmentCodeDomainService.judgeCodeCanbeSettingByIntegralFromCodeManager(segmentCodeDomains);

        //  根据营销码删除这批码相关的码
        segmentCodeRepository.deleteOldSetting(segmentCodeDomains);
        // 持久化配置信息
        segmentCodeRepository.saveNewSetting(segmentCodeDomains);

        // 发送单码业务事件 该事件用于码管理接口获取业务
        segmentCodeBindBizPublisher.addSubscriber(segmentCodeBindBizSubscriber);
        segmentCodeBindBizPublisher.publish(new SegmentCodeBindBizEvent(segmentCodeDomains));
    }
}
