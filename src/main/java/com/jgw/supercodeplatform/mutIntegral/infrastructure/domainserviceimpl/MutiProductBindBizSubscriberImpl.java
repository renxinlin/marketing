package com.jgw.supercodeplatform.mutIntegral.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralProductDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralSbatchDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.event.ProductBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.MutiProductBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.BizRoutePriorityConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.BizRouteService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.BizRouteMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.BizRoute;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.BizRouteTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Component
public class MutiProductBindBizSubscriberImpl implements MutiProductBindBizSubscriber {
    @Autowired
    private BizRouteMapper mapper;
    @Autowired
    private BizRouteService batchMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BizRouteTransfer transfer;
    @Override
    public void handle(ProductBindBizEvent productBindBizEvent) {
        if(CollectionUtils.isEmpty(productBindBizEvent.getProductDomains())){
            String organizationId = commonUtil.getOrganizationId();
            LambdaQueryWrapper<BizRoute> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.eq(BizRoute::getOrganizationId, organizationId);
            deleteWrapper.eq(BizRoute::getBizType, BizRoutePriorityConstants.product);
            mapper.delete(deleteWrapper);
            return;
        }
        List<IntegralProductDomain> productDomins = productBindBizEvent.getProductDomains();
        List<BizRoute> bizRoutes = transfer.transferProductToBizRoutePojo(productDomins);
        batchMapper.saveBatch(bizRoutes);
    }






}
