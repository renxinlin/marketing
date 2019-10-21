package com.jgw.supercodeplatform.prizewheels.domain.publisher;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 发布者尽量不要在领域服务而在应用层
 */
@Component
public class CdkEventPublisher {

    private List<CdkEventSubscriber> cdkEventSubscribers;
    public void addSubscriber(CdkEventSubscriber... cdkEventSubscriber ) {
        if(cdkEventSubscribers == null ){
            cdkEventSubscribers = new ArrayList<>();
        }
        cdkEventSubscribers.addAll(Arrays.asList(cdkEventSubscriber));
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
