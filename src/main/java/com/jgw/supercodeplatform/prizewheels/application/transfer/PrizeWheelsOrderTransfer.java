package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.PrizeWheelsOrder;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrizeWheelsOrderTransfer {
    @Autowired
    private ModelMapper modelMapper;
    public PrizeWheelsOrder tranferToDomain(PrizeWheelsOrderDto prizeWheelsOrderDto) {
        return modelMapper.map(prizeWheelsOrderDto,PrizeWheelsOrder.class);
    }
}
