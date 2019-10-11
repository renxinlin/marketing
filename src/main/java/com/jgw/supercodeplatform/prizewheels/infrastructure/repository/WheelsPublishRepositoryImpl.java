package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsPublishRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.WheelsPojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 六边形架构模式:实现解耦放置于基础设施
 */
@Repository
public class WheelsPublishRepositoryImpl implements WheelsPublishRepository {

    @Autowired
    private WheelsMapper wheelsMapper;

    @Autowired
    private WheelsPojoTransfer wheelsPojoTransfer;


    @Override
    public void publish(Wheels wheels) {
        WheelsPojo wheelsPojo =  wheelsPojoTransfer.tranferDomainToPojo(wheels);
        wheelsMapper.insert(wheelsPojo);
    }

    @Override
    public int deletePrizeWheelsById(Long id) {
        return wheelsMapper.deleteById(id);
    }

    @Override
    public int updatePrizeWheel(Wheels wheels) {
        WheelsPojo wheelsPojo =  wheelsPojoTransfer.tranferDomainToPojoWhenUpdate(wheels);
        return wheelsMapper.updateById(wheelsPojo);
    }
}
