package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRewardCdk;
import org.springframework.stereotype.Repository;

@Repository
public interface WheelsRewardCdkRepository {
    WheelsRewardCdk getCdkWhenH5Reward(Long prizeRewardId);
}
