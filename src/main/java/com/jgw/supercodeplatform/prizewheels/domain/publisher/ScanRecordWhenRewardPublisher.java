package com.jgw.supercodeplatform.prizewheels.domain.publisher;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.event.ScanRecordWhenRewardEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.ScanRecordWhenRewardSubscriber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ScanRecordWhenRewardPublisher {

    private List<ScanRecordWhenRewardSubscriber> scanRecordWhenRewardSubscribers;
    public void addSubscriber(ScanRecordWhenRewardSubscriber... scanRecordWhenRewardSubscriber ) {
        scanRecordWhenRewardSubscribers = new ArrayList<>();
        scanRecordWhenRewardSubscribers.addAll(Arrays.asList(scanRecordWhenRewardSubscriber));
    }

    public void commitAsyncEvent(ScanRecordWhenRewardEvent scanRecordWhenRewardEvent) {
        if(scanRecordWhenRewardSubscribers == null){
            return;
        }
        for(ScanRecordWhenRewardSubscriber scanRecordWhenRewardSubscriber : scanRecordWhenRewardSubscribers){
            scanRecordWhenRewardSubscriber.handleAynsc(scanRecordWhenRewardEvent);
        }
    }
}
