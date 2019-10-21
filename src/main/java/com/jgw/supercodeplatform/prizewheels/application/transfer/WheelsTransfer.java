package com.jgw.supercodeplatform.prizewheels.application.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsUpdateDto;
import com.jgw.supercodeplatform.prizewheels.interfaces.vo.WheelsDetailsVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WheelsTransfer {
    @Autowired
    private ModelMapper modelMapper;

    public Wheels tranferToDomain(WheelsUpdateDto wheelsUpdateDto) {
        // TODO 属性添加
        return modelMapper.map(wheelsUpdateDto,Wheels.class);
    }

    public Wheels tranferToDomainWhenAdd(WheelsDto wheelsDto) {
        return modelMapper.map(wheelsDto,Wheels.class);

    }
    public WheelsDetailsVo tranferWheelsPojoToDomain(WheelsPojo wheelsPojo){
        return modelMapper.map(wheelsPojo,WheelsDetailsVo.class);
    }
}
