package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WheelsRewardRepository {

    int deleteByPrizeWheelsId(Long id);

    void batchSave(List<WheelsReward> wheelsRewards);

    List<WheelsRewardPojo> getByPrizeWheelsId(Long id);

    List<WheelsReward> getDomainByPrizeWheelsId(Long id);

    int reduceStockForReal(WheelsReward finalReward);
}
