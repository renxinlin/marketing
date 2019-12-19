package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRecord;
import com.jgw.supercodeplatform.prizewheels.domain.repository.RecordRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRecordMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsOrderDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecordRepositoryImpl implements RecordRepository {
    @Autowired
    private WheelsRecordMapper recordMapper;


    @Autowired
    private ModelMapper modelMapper;
    @Override
    public IPage<WheelsRecordPojo> selectPage(IPage<WheelsRecordPojo> page, Wrapper<WheelsRecordPojo> pageParam) {
        return recordMapper.selectPage(page,pageParam);
    }

    @Override
    public WheelsRecord newRecordWhenH5Reward(WheelsRecord wheelsRecord) {
        WheelsRecordPojo wheelsRecordPojo =modelMapper.map(wheelsRecord,WheelsRecordPojo.class);
        int insert = recordMapper.insert(wheelsRecordPojo);
        wheelsRecord.setId(wheelsRecordPojo.getId());
        return wheelsRecord;
    }

    @Override
    public void updateRecordInfoWhenReal(PrizeWheelsOrderDto prizeWheelsOrderDto) {
        WheelsRecordPojo recordPojo = new WheelsRecordPojo();
        recordPojo.setId(prizeWheelsOrderDto.getRecordId());
        recordPojo.setAddress(prizeWheelsOrderDto.getAddress());
        recordPojo.setRevicerMobile(prizeWheelsOrderDto.getReceiverMobile());
        recordPojo.setRevicerName(prizeWheelsOrderDto.getReceiverName());
        recordMapper.updateById(recordPojo);
    }
}
