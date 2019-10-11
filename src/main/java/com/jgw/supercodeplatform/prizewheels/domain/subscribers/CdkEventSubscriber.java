package com.jgw.supercodeplatform.prizewheels.domain.subscribers;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import org.springframework.stereotype.Component;

@Component
public interface CdkEventSubscriber {
    void handle(CdkEvent cdkEvent);
}
