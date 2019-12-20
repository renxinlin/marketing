package com.jgw.supercodeplatform.mutIntegral.domain.publisher;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SegmentCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SbatchBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SegmentCodeBindBizSubscriber;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class SbatchBindBizPublisher {
    private List<SbatchBindBizSubscriber> sbatchBindBizSubscribers;

    public void addSubscriber(SbatchBindBizSubscriber sbatchBindBizSubscriber) {
        sbatchBindBizSubscribers = new ArrayList<>();
        sbatchBindBizSubscribers.addAll(Arrays.asList(sbatchBindBizSubscriber));

    }

    public void publish(SbatchBindBizEvent sbatchBindBizEvent) {
        if(CollectionUtils.isEmpty(sbatchBindBizSubscribers)){
            return;
        }
        for(SbatchBindBizSubscriber sbatchBindBizSubscriber: sbatchBindBizSubscribers){
            sbatchBindBizSubscriber.handle(sbatchBindBizEvent);
        }

    }


}
