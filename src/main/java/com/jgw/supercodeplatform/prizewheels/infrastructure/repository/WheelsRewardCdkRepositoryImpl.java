package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CdkStatus;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsReward;
import com.jgw.supercodeplatform.prizewheels.domain.model.WheelsRewardCdk;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardCdkRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.WheelsRewardCdkMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.WheelsRewardCdkPojo;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.apache.http.util.Asserts;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Repository
public class WheelsRewardCdkRepositoryImpl implements WheelsRewardCdkRepository {
    @Autowired
    private WheelsRewardCdkMapper wheelsRewardCdkMapper;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 获取未被领取的cdk
     * 获取成功更新cdk位已经被领取
     * @param prizeRewardId
     * @return
     */
    @Override
    public WheelsRewardCdk getCdkWhenH5Reward(Long prizeRewardId) {
        QueryWrapper<WheelsRewardCdkPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", CdkStatus.BEFORE_REWARD);
        queryWrapper.eq("prizeRewardId", prizeRewardId);
        IPage<WheelsRewardCdkPojo> ipage   = new Page(1,1); ;
        IPage<WheelsRewardCdkPojo> wheelsRewardCdkPojoIPage = wheelsRewardCdkMapper.selectPage(ipage, queryWrapper);
        log.error("wheelsRewardCdkPojoIPage=>{}", JSONObject.toJSONString(wheelsRewardCdkPojoIPage));
        if(wheelsRewardCdkPojoIPage !=null
                && !CollectionUtils.isEmpty(wheelsRewardCdkPojoIPage.getRecords())){
            WheelsRewardCdkPojo wheelsRewardCdkPojo = wheelsRewardCdkPojoIPage.getRecords().get(0);


            // 查询是否还未被领取
            QueryWrapper<WheelsRewardCdkPojo> updateQueryWrapper = new QueryWrapper<>();
            updateQueryWrapper.eq("status", CdkStatus.BEFORE_REWARD); // 解决affectrow maybe not config question
            updateQueryWrapper.eq("id", wheelsRewardCdkPojo.getId());
            //更新被领取
            wheelsRewardCdkPojo.setStatus(CdkStatus.AFTER_REWARD);

            int i = wheelsRewardCdkMapper.update(wheelsRewardCdkPojo, updateQueryWrapper);
            Asserts.check(i==1,"cdk已经被领取");
            return modelMapper.map(wheelsRewardCdkPojo,WheelsRewardCdk.class);
        }

        throw new RuntimeException("活动cdk已经被领完");
    }

    @Override
    public void deleteOldCdk(List<WheelsReward> oldwheelsRewards) {
        QueryWrapper<WheelsRewardCdkPojo> deleteWrapper = new QueryWrapper<>();
        List<Long> rewardIds = oldwheelsRewards.stream().map(WheelsReward::getId).collect(Collectors.toList());
        deleteWrapper.in("prizeRewardId" ,rewardIds );
        wheelsRewardCdkMapper.delete(deleteWrapper);

    }
}
