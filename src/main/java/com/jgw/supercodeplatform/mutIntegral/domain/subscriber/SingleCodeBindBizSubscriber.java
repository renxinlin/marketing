package com.jgw.supercodeplatform.mutIntegral.domain.subscriber;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import org.springframework.stereotype.Service;

@Service
public interface SingleCodeBindBizSubscriber {
     void handle(SingleCodeBindBizEvent singleCodeBindBizEvents);
}
