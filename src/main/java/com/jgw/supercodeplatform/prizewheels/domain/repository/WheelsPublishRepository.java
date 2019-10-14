package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import org.springframework.stereotype.Repository;


/**
 * 六边形架构模式:实现解耦放置于基础设施
 */
@Repository
public interface WheelsPublishRepository {

    void publish(Wheels wheels);

    int deletePrizeWheelsById(Long id);

    int updatePrizeWheel(Wheels wheels);


}
