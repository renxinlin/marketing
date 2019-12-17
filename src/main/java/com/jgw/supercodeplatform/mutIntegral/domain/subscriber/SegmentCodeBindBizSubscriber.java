package com.jgw.supercodeplatform.mutIntegral.domain.subscriber;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SegmentCodeBindBizEvent;
import org.springframework.stereotype.Service;

@Service
public interface SegmentCodeBindBizSubscriber {
     void handle(SegmentCodeBindBizEvent segmentCodeBindBizEvent);
}
