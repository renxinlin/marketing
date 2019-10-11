package com.jgw.supercodeplatform.prizewheels.domain.publisher;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class CdkEventPublisher {

    private List<CdkEventSubscriber> cdkEventSubscribers;
    public void addSubscriber(CdkEventSubscriber... cdkEventSubscriber ) {
        if(cdkEventSubscribers == null ){
            cdkEventSubscribers = new ArrayList<>();
        }
        cdkEventSubscribers.addAll(cdkEventSubscribers);
    }

    public void publish(CdkEvent cdkEvent) {
        if(cdkEventSubscribers == null){
            return;
        }
        for(CdkEventSubscriber cdkEventSubscriber : cdkEventSubscribers){
            cdkEventSubscriber.handle(cdkEvent);
        }
    }
}
