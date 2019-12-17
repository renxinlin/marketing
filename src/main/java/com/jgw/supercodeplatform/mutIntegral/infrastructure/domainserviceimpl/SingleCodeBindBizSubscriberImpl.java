package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSingleCodeDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SingleCodeBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.BizRoutePriorityConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.BizRouteService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.BizRouteMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.BizRoute;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.BizRouteTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SingleCodeBindBizSubscriberImpl implements SingleCodeBindBizSubscriber {
    @Autowired
    private BizRouteMapper mapper;
    @Autowired
    private BizRouteService batchMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BizRouteTransfer transfer;


    @Override
    public void handle(SingleCodeBindBizEvent singleCodeBindBizEvents) {
        List<IntegralSingleCodeDomain> singleCodeDomains = singleCodeBindBizEvents.getSingleCodeDomains();
        List<BizRoute> bizRoutes = transfer.transferSingleCodeToBizRoutePojo(singleCodeDomains);
        String organizationId = commonUtil.getOrganizationId();
        LambdaQueryWrapper<BizRoute> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(BizRoute::getOrganizationId, organizationId);
        deleteWrapper.eq(BizRoute::getBizType, BizRoutePriorityConstants.SingleCode);

        mapper.delete(deleteWrapper);
        batchMapper.saveBatch(bizRoutes);
    }
}
