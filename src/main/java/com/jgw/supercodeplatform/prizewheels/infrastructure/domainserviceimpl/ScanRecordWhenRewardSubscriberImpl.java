package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.jgw.supercodeplatform.prizewheels.domain.event.ScanRecordWhenRewardEvent;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ScanRecordRepository;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.ScanRecordWhenRewardSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ScanRecordWhenRewardSubscriberImpl implements ScanRecordWhenRewardSubscriber {

    @Autowired
    private TaskExecutor taskExecutor;


    @Autowired
    private ScanRecordRepository scanRecordRepository;

    @Override
    public void handleAynsc(ScanRecordWhenRewardEvent scanRecordWhenRewardEvent) {
        taskExecutor.execute(() -> {
            // 持久化
            scanRecordRepository.saveScanRecord(scanRecordWhenRewardEvent.getScanRecord());
        });
    }
}
