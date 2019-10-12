package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WheelsRewardRepository {

    int deleteByPrizeWheelsId(Long id);

    void batchSave(List<WheelsReward> wheelsRewards);
}
