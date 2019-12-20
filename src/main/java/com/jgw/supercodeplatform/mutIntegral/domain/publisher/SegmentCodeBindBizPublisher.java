package com.jgw.supercodeplatform.mutIntegral.domain.publisher;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SegmentCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SegmentCodeBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SingleCodeBindBizSubscriber;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class SegmentCodeBindBizPublisher {




    private List<SegmentCodeBindBizSubscriber> segmentCodeBindBizSubscribers;
    public void addSubscriber(SegmentCodeBindBizSubscriber... segmentCodeBindBizSubscriber ) {
        segmentCodeBindBizSubscribers = new ArrayList<>();
        segmentCodeBindBizSubscribers.addAll(Arrays.asList(segmentCodeBindBizSubscriber));
    }

    public void publish(SegmentCodeBindBizEvent segmentCodeBindBizEvent) {
        if(segmentCodeBindBizSubscribers == null){
            return;
        }
        for(SegmentCodeBindBizSubscriber segmentCodeBindBizSubscriber: segmentCodeBindBizSubscribers){
            segmentCodeBindBizSubscriber.handle(segmentCodeBindBizEvent);
        }
    }
}
