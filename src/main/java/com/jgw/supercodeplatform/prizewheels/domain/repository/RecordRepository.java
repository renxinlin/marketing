package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRecord;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRecordPojo;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.PrizeWheelsOrderDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository {

    IPage<WheelsRecordPojo> selectPage(IPage<WheelsRecordPojo> page, Wrapper<WheelsRecordPojo> pageParam);

    WheelsRecord newRecordWhenH5Reward(WheelsRecord wheelsRecord);

    void updateRecordInfoWhenReal(PrizeWheelsOrderDto prizeWheelsOrderDto);
}
