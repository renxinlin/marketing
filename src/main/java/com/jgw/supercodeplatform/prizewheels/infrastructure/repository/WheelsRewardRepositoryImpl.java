package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.batch.WheelsRewardService;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRewardMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.WheelsRewardPojoTransfer;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class WheelsRewardRepositoryImpl implements WheelsRewardRepository {

    @Autowired
    private WheelsRewardMapper wheelsRewardMapper;

    @Autowired
    private WheelsRewardService wheelsRewardBatchService;

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
        for(int i = 0;i<lists.size();i++){
            wheelsRewards.get(i).setId(lists.get(i).getId());
        }

    }

    @Override
    public List<WheelsRewardPojo> getByPrizeWheelsId(Long id) {
        QueryWrapper<WheelsRewardPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("PrizeWheelId",id);
        return wheelsRewardMapper.selectList(wrapper);
    }

    @Override
    public List<WheelsReward> getDomainByPrizeWheelsId(Long id) {
        QueryWrapper<WheelsRewardPojo> wrapper = new QueryWrapper<>();
        wrapper.eq("PrizeWheelId",id);
        List<WheelsRewardPojo> list = wheelsRewardMapper.selectList(wrapper);
        Asserts.check(!CollectionUtils.isEmpty(list), ErrorCodeEnum.NOT_EXITS_ERROR.getErrorMessage());
        return wheelsRewardPojoTransfer.tranferPojosToDomains(list);

    }

    @Override
    public int reduceStockForReal(WheelsReward finalReward) {
        int success = wheelsRewardMapper.reduceStock(finalReward.getId());
        return success;
    }

    @Override
    public int reduceStockForMoney(WheelsReward finalReward) {
        int success = wheelsRewardMapper.reduceStock(finalReward.getId());
        return success;    }


}
