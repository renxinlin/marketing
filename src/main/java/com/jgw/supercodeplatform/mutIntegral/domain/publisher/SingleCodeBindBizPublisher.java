package com.jgw.supercodeplatform.mutIntegral.domain.publisher;

import com.jgw.supercodeplatform.mutIntegral.domain.event.SingleCodeBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SingleCodeBindBizSubscriber;
import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class SingleCodeBindBizPublisher {
    private List<SingleCodeBindBizSubscriber> singleCodeBindBizSubscribers;
    public void addSubscriber(SingleCodeBindBizSubscriber... cdkEventSubscriber ) {
        singleCodeBindBizSubscribers = new ArrayList<>();
        singleCodeBindBizSubscribers.addAll(Arrays.asList(cdkEventSubscriber));
    }

    public void publish(SingleCodeBindBizEvent singleCodeBindBizEvents) {
        if(singleCodeBindBizSubscribers == null){
            return;
        }
        for(SingleCodeBindBizSubscriber   singleCodeBindBizSubscriber: singleCodeBindBizSubscribers){
            singleCodeBindBizSubscriber.handle(singleCodeBindBizEvents);
        }
    }
}
