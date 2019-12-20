package com.jgw.supercodeplatform.mutIntegral.domain.subscriber;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import org.springframework.stereotype.Service;

@Service
public interface SbatchBindBizSubscriber {
    void handle(SbatchBindBizEvent sbatchBindBizEvent);
}
