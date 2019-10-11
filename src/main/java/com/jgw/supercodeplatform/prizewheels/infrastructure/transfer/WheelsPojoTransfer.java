package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WheelsPojoTransfer {
    @Autowired private ModelMapper modelMapper;
    public WheelsPojo tranferDomainToPojo(Wheels wheels) {
       return modelMapper.map(wheels ,WheelsPojo.class);
    }
}
