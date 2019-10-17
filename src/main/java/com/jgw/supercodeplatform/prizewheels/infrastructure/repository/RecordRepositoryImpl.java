package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.prizewheels.domain.repository.RecordRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRecordMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RecordRepositoryImpl implements RecordRepository {
    @Autowired
    private WheelsRecordMapper recordMapper;
    @Override
    public IPage<WheelsRecordPojo> selectPage(IPage<WheelsRecordPojo> page, Wrapper<WheelsRecordPojo> pageParam) {
        return recordMapper.selectPage(page,pageParam);
    }
}
