package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRewardCdk;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WheelsRewardCdkRepository {
    WheelsRewardCdk getCdkWhenH5Reward(Long prizeRewardId);

    void deleteOldCdk(List<WheelsReward> oldwheelsRewards);
}
