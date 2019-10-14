package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.publisher.CdkEventPublisher;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 领域服务 : 仅处理WheelsReward 直接实现类
 */
@Service
public class WheelsRewardDomainService {
    @Autowired
    private CdkEventPublisher cdkEventPublisher;

    @Autowired
    private CdkEventSubscriber cdkEventSubscriber;
    public void checkWhenUpdate(List<WheelsReward> wheelsRewards) {
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        for(WheelsReward wheelsReward : wheelsRewards){
            Asserts.check(wheelsReward.getId()!= null && wheelsReward.getId() > 0,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
            Asserts.check(wheelsReward.getPrizeWheelId()  != null && wheelsReward.getPrizeWheelId() > 0
                    ,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        }
    }


    public void checkWhenAdd(List<WheelsReward> wheelsRewards) {
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());


    }


    public void cdkEventCommitedWhenNecessary(List<WheelsReward> wheelsRewards) {
        for(WheelsReward wheelsReward : wheelsRewards){
            Asserts.check(wheelsReward.getId()!= null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
            if(!StringUtils.isEmpty(wheelsReward.getCdkKey())){
                CdkEvent cdkEvent = new CdkEvent(wheelsReward.getId(), wheelsReward.getCdkKey());
                cdkEventPublisher.addSubscriber(cdkEventSubscriber);
                cdkEventPublisher.publish(cdkEvent);

            }
        }

    }
}
