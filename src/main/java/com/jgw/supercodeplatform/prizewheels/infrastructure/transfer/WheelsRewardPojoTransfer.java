package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class WheelsRewardPojoTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public List<WheelsRewardPojo> tranferDomainsToPojos(List<WheelsReward> wheelsRewards) {
        List<WheelsRewardPojo> lists = new ArrayList<>();
        if(!CollectionUtils.isEmpty(wheelsRewards)){
            for(WheelsReward wheelsReward : wheelsRewards){
                WheelsRewardPojo wheelsRewardPojo = modelMapper.map(wheelsReward, WheelsRewardPojo.class);
                lists.add(wheelsRewardPojo);
            }
        }
        return lists;
    }


    public  List<WheelsReward>  tranferPojosToDomains(List<WheelsRewardPojo> list) {
        List<WheelsReward> wheelsRewards = new ArrayList<>();

        list.stream().forEach(wheelsRewardPojo -> {
            WheelsReward wheelsReward = modelMapper.map(wheelsRewardPojo, WheelsReward.class);
            wheelsRewards.add(wheelsReward);
        });
        return wheelsRewards;
    }
}
