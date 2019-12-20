package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.MutiProductTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.ProductBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.MutiProductBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.service.MutiProductDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.MutiProductRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.MutiProductBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralProductAggDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class IntegralProductApplication {
    // 领域服务
    @Autowired
    private MutiProductDomainService mutiProductDomainService;

    @Autowired
    private MutiProductBindBizPublisher mutiProductBindBizPublisher;

    @Autowired
    private MutiProductBindBizSubscriber mutiProductBindBizSubscriber;

    // 翻译
    @Autowired
    private MutiProductTransfer mutiProductTransfer;


    // 基础设施
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MutiProductRepository mutiProductRepository;


    public void setProductForIntegralRule(IntegralProductAggDto productAggDto) {
        //  根据营销码删除这批码相关的码
        mutiProductRepository.deleteOldSetting();

        // 哨兵校验
        // 数据获取与转换
        String organizationId = commonUtil.getOrganizationId();
        String organizationName = commonUtil.getOrganizationName();
        List<IntegralProductDomain> productDomains = mutiProductTransfer.transferDtoToDomain(productAggDto,organizationId,organizationName);

        if(CollectionUtils.isEmpty(productDomains)){
            //  码管理校验: 校验号段码
            mutiProductDomainService.judgeCodeCanbeSettingByIntegralFromCodeManager(productDomains);
            // 持久化配置信息
            mutiProductRepository.saveNewSetting(productDomains);

        }
        // 发送单码业务事件 该事件用于码管理接口获取业务
        mutiProductBindBizPublisher.addSubscriber(mutiProductBindBizSubscriber);
        mutiProductBindBizPublisher.publish(new ProductBindBizEvent(productDomains));

    }
}


