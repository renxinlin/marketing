package com.jgw.supercodeplatform.prizewheels.domain.subscribers;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.event.ScanRecordWhenRewardEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface ScanRecordWhenRewardSubscriber {

    void handleAynsc(ScanRecordWhenRewardEvent scanRecordWhenRewardEvent);

}
