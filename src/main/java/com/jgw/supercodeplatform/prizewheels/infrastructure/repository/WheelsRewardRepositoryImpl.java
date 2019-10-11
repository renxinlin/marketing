package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRewardMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WheelsRewardRepositoryImpl implements WheelsRewardRepository {

    @Autowired
    private WheelsRewardMapper wheelsRewardMapper;

    @Override
    public int deleteByPrizeWheelsId(Long PrizeWheelId) {
        QueryWrapper<WheelsRewardPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("PrizeWheelId",PrizeWheelId);
       return wheelsRewardMapper.delete(wrapper);
    }
}
