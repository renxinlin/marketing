package com.jgw.supercodeplatform.prizewheels.infrastructure.domainserviceimpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRewardCdkMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardCdk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CdkEventSubscriberImpl implements CdkEventSubscriber {
    @Autowired
    private WheelsRewardCdkMapper wheelsRewardCdkMapper;

    @Override
    public void handle(CdkEvent cdkEvent) {
        WheelsRewardCdk entity = new WheelsRewardCdk();
        entity.setPrizeRewardId(cdkEvent.getPrizeRewardId());

        QueryWrapper<WheelsRewardCdk> query = new QueryWrapper<>();
        query.eq("cdkKey",cdkEvent.getCdkUuid());
        wheelsRewardCdkMapper.update(entity,query);
    }
}
