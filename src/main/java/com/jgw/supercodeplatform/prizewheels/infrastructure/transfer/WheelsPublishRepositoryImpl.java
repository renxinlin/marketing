package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import org.springframework.stereotype.Repository;


/**
 * 六边形架构模式:实现解耦放置于基础设施
 */
@Repository
public class WheelsPublishRepositoryImpl implements WheelsPublishRepository {

    @Override
    public void publish(Wheels wheels) {

    }
}
