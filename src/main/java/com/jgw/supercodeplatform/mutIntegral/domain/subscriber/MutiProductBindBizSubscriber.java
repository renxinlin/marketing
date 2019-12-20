package com.jgw.supercodeplatform.mutIntegral.domain.subscriber;

import com.jgw.supercodeplatform.mutIntegral.domain.event.ProductBindBizEvent;
import org.springframework.stereotype.Component;

@Component
public interface MutiProductBindBizSubscriber {
     void handle(ProductBindBizEvent productBindBizEvent);
}
