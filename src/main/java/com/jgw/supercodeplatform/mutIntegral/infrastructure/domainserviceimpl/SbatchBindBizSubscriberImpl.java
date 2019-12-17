package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSegmentCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SegmentCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SbatchBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.BizRoutePriorityConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.BizRouteService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.BizRouteMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.BizRoute;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.BizRouteTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SbatchBindBizSubscriberImpl implements SbatchBindBizSubscriber {

    @Autowired
    private BizRouteMapper mapper;
    @Autowired
    private BizRouteService batchMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BizRouteTransfer transfer;

    @Override
    public void handle(SbatchBindBizEvent sbatchBindBizEvent) {
        List<IntegralSbatchDomain> sbatchDomains = sbatchBindBizEvent.getSbatchDomains();
        List<BizRoute> bizRoutes = transfer.transferSbatchCodeToBizRoutePojo(sbatchDomains);
        String organizationId = commonUtil.getOrganizationId();
        LambdaQueryWrapper<BizRoute> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(BizRoute::getOrganizationId, organizationId);
        deleteWrapper.eq(BizRoute::getBizType, BizRoutePriorityConstants.SbatchId);
        mapper.delete(deleteWrapper);
        batchMapper.saveBatch(bizRoutes);
    }








}
