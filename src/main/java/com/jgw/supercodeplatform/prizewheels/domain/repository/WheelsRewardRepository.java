package com.jgw.supercodeplatform.prizewheels.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface WheelsRewardRepository {

    int deleteByPrizeWheelsId(Long id);
}
