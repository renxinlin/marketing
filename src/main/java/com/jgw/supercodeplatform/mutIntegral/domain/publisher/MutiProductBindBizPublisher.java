package com.jgw.supercodeplatform.mutIntegral.domain.publisher;

import com.jgw.supercodeplatform.mutIntegral.domain.event.ProductBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.event.SbatchBindBizEvent;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.MutiProductBindBizSubscriber;
import com.jgw.supercodeplatform.mutIntegral.domain.subscriber.SbatchBindBizSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
@Slf4j
public class MutiProductBindBizPublisher {
    private List<MutiProductBindBizSubscriber> mutiProductBindBizSubscribers;
    public void addSubscriber(MutiProductBindBizSubscriber... mutiProductBindBizSubscriber) {
        mutiProductBindBizSubscribers = new ArrayList<>();
        mutiProductBindBizSubscribers.addAll(Arrays.asList(mutiProductBindBizSubscriber));
    }

    public void publish(ProductBindBizEvent productBindBizEvent) {
        if(CollectionUtils.isEmpty(mutiProductBindBizSubscribers)){
            return;
        }
        for(MutiProductBindBizSubscriber mutiProductBindBizSubscriber: mutiProductBindBizSubscribers){
            mutiProductBindBizSubscriber.handle(productBindBizEvent);
        }

    }


}
