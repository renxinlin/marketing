package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.SingleCodeTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.SingleCodeBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.SingleCodeRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.SingleCodeDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SingleCodeBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralSingleCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class IntegralSingleCodeApplication {
    // 领域服务
    @Autowired
    private SingleCodeDomainService singleCodeDomainService;

    @Autowired
    SingleCodeBindBizPublisher singleCodeBindBizPublisher;

    @Autowired
    SingleCodeBindBizSubscriber  singleCodeBindBizSubscriber;

    // 翻译
    @Autowired
    private SingleCodeTransfer singleCodeTransfer;


    // 基础设施
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SingleCodeRepository singleCodeRepository;

    public void setsingleCodeForIntegralRule(List<IntegralSingleCodeDto> singleCodeDtos) {
        //  根据营销码删除这批码相关的码
        singleCodeRepository.deleteOldSetting();

        // 数据获取与转换
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        List<IntegralSingleCodeDomain> singledomains = singleCodeTransfer.transferDtoToDomain(singleCodeDtos,organizationId,organizationName);
        if(CollectionUtils.isEmpty(singledomains)){
            //  码管理校验:  返回这批码具体为防伪码还是营销码
            singledomains = singleCodeDomainService.judgeCodeCanbeSettingByIntegralFromCodeManager(singledomains);
            // 持久化配置信息
            singleCodeRepository.saveNewSetting(singledomains);
        }

        // 发送单码业务事件 该事件用于码管理接口获取业务
        singleCodeBindBizPublisher.addSubscriber(singleCodeBindBizSubscriber);
        singleCodeBindBizPublisher.publish(new SingleCodeBindBizEvent(singledomains));


    }
}
