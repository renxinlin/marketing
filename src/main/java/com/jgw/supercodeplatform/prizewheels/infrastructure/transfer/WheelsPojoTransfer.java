package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WheelsPojoTransfer {
    @Autowired private ModelMapper modelMapper;
    public WheelsPojo tranferDomainToPojo(Wheels wheels) {
        WheelsPojo wheelsPojo = modelMapper.map(wheels, WheelsPojo.class);
        Publisher publisher = wheels.getPublisher();
        wheelsPojo.setCreateDate(publisher.getCreateDate());
        wheelsPojo.setCreateUser(publisher.getCreateUser());
        wheelsPojo.setCreateUserId(publisher.getCreateUserId());
        return wheelsPojo;
    }

    public WheelsPojo tranferDomainToPojoWhenUpdate(Wheels wheels) {
        WheelsPojo wheelsPojo = modelMapper.map(wheels, WheelsPojo.class);
        Publisher publisher = wheels.getPublisher();
        wheelsPojo.setUpdateDate(publisher.getUpdateDate());
        wheelsPojo.setUpdateUserId(publisher.getUpdateUserId());
        wheelsPojo.setUpdateUserName(publisher.getUpdateUserName());
        return wheelsPojo;

    }
}
