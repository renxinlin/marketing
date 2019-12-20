package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.MutiProductTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.ProductBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.publisher.MutiProductBindBizPublisher;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.MutiProductRepository;
import com.jgw.supercodeplatform.mutIntegral.domain.service.MutiProductDomainService;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.MutiProductBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRecord;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.repository.MutiIntegralRecordRepository;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralProductAggDto;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class MutiIntegralRecordApplication {

    // 翻译
    @Autowired
    private MutiProductTransfer mutiProductTransfer;


    // 基础设施
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private MutiIntegralRecordRepository recordRepository;

    public AbstractPageService.PageResults<List<IntegralRecord>> getMemberMutiIntegralRecordPage(DaoSearch daoSearch){
        // 跳过领域层直接查询数据
        return  recordRepository.getMemberMutiIntegralRecordPage(daoSearch);
    };


}


