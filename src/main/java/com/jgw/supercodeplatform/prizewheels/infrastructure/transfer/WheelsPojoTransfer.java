package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Publisher;
import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import org.apache.commons.lang.StringUtils;
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

    public Wheels tranferPojoToDomain(WheelsPojo wheelsPojo) {
        Wheels wheels = modelMapper.map(wheelsPojo , Wheels.class);
        Publisher publisher = new Publisher();
        if(StringUtils.isEmpty(wheelsPojo.getUpdateUserId())){

        }else {
            publisher.setCreateDate(wheelsPojo.getCreateDate());
            publisher.setCreateUserId(wheelsPojo.getCreateUserId());
            publisher.setCreateUser(wheelsPojo.getCreateUser());
        }
        publisher.setUpdateDate(wheelsPojo.getUpdateDate());
        publisher.setUpdateUserId(wheelsPojo.getUpdateUserId());
        publisher.setUpdateUserName(wheelsPojo.getUpdateUserName());
        wheels.setPublisher(publisher);
        return wheels;
    }
}
