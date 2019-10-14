package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.WheelsRewardService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRewardMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.WheelsRewardPojoTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WheelsRewardRepositoryImpl implements WheelsRewardRepository {

    @Autowired
    private WheelsRewardMapper wheelsRewardMapper;

    @Autowired
    private WheelsRewardService wheelsRewardBatchService;

    @Autowired
    private WheelsRewardRepository wheelsRewardRepository;

    @Autowired
    private WheelsRewardPojoTransfer wheelsRewardPojoTransfer;

    @Override
    public int deleteByPrizeWheelsId(Long PrizeWheelId) {
        QueryWrapper<WheelsRewardPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("PrizeWheelId",PrizeWheelId);
       return wheelsRewardMapper.delete(wrapper);
    }

    @Override
    public void batchSave(List<WheelsReward> wheelsRewards) {
        List<WheelsRewardPojo> lists = wheelsRewardPojoTransfer.tranferDomainsToPojos(wheelsRewards);
        wheelsRewardBatchService.saveBatch(lists);
    }

    @Override
    public List<WheelsRewardPojo> getByPrizeWheelsId(Long id) {
        QueryWrapper<WheelsRewardPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("PrizeWheelId",id);
        return wheelsRewardMapper.selectList(wrapper);
    }


}
