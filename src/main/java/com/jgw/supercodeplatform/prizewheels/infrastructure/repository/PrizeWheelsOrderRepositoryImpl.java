package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.PrizeWheelsOrder;
import com.jgw.supercodeplatform.prizewheels.domain.repository.PrizeWheelsOrderRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.PrizeWheelsOrderMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.PrizeWheelsOrderPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PrizeWheelsOrderRepositoryImpl implements PrizeWheelsOrderRepository {
    @Autowired private ModelMapper modelMapper;
    @Autowired private PrizeWheelsOrderMapper mapper;
    @Override
    public void addOrder(PrizeWheelsOrder prizeWheelsOrder) {
        mapper.insert(modelMapper.map(prizeWheelsOrder, PrizeWheelsOrderPojo.class));
    }
}
