package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.domain.IntegralRuleRewardDomian;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRewardRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.batchdao.IntegralRuleRewardService;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.dao.IntegralRuleRewardMapper;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralRuleReward;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.transfer.MutiRewardTransfer;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleRewardAggDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
public class IntegralRuleRewardRepositoryImpl implements IntegralRuleRewardRepository {
    @Autowired
    private MutiRewardTransfer transfer;


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private IntegralRuleRewardMapper ruleRewardMapper;


    @Autowired
    private IntegralRuleRewardService ruleRewardService;


    @Override
    public void updateSnapshotInfo(List<IntegralRuleRewardAggDto> ruleRewardAggDtos) {

        redisUtil.set(ruleRewardAggDtosPrefix+commonUtil.getOrganizationId(), JSONObject.toJSONString(ruleRewardAggDtos));
    }


    @Override
    public List<IntegralRuleRewardAggDto>  getSnapshotInfo() {
        String integralRuleRewardAggDtoStr = redisUtil.get(ruleRewardAggDtosPrefix+commonUtil.getOrganizationId());
        if(StringUtils.isEmpty(integralRuleRewardAggDtoStr)){
            return  new ArrayList<>();
        }
        // TODO 转换成dto
        // TODO 转换成dto
        // TODO 转换成dto // TODO 转换成dto
        // TODO 转换成dto
        List<IntegralRuleRewardAggDto> integralRuleRewardAggDtos = JSONObject.parseArray(integralRuleRewardAggDtoStr, IntegralRuleRewardAggDto.class);
        return  integralRuleRewardAggDtos;
    }

    @Override
    public void saveintegralRewardInfo(List<List<IntegralRuleRewardDomian>> ruleRewardDomains) {
        List<IntegralRuleReward> pojos = transfer.transferRewardDomiansToPojos(ruleRewardDomains);
        ruleRewardService.saveBatch(pojos);

    }

    @Override
    public void deleteOldByOrganization() {
        LambdaQueryWrapper<IntegralRuleReward> deleteOldByOrganization = new LambdaQueryWrapper<>();
        deleteOldByOrganization.eq(IntegralRuleReward::getOrganizationId,commonUtil.getOrganizationId());
        ruleRewardMapper.delete(deleteOldByOrganization);
    }
}
