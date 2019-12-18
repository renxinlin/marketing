package com.jgw.supercodeplatform.mutIntegral.infrastructure.repository;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.ReadSettingInfoRepository;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.*;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReadSettingInfoRepositoryImpl implements ReadSettingInfoRepository {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonUtil commonUtil;

    @Override
    public void updateSnapshotInfo(IntegralRewardSettingAggDto integralRewardSettingAggDto) {
        // todo 持久化
        redisUtil.set(integralRewardSettingAggDtoPrefix+commonUtil.getOrganizationId(),JSONObject.toJSONString(integralRewardSettingAggDto));
    }

    @Override
    public IntegralRewardSettingAggDto getSnapshotInfo() {
        String integralRewardSettingAggDtoStr = redisUtil.get(integralRewardSettingAggDtoPrefix + commonUtil.getOrganizationId());

        if(StringUtils.isEmpty(integralRewardSettingAggDtoStr)){
            return new IntegralRewardSettingAggDto(new ArrayList<>()
                    ,new ArrayList<>()
                    ,new ArrayList<>()
                    ,new IntegralProductAggDto(null
                                                ,new ArrayList<>()));
        }
        // todo 转换成dto
        // todo 转换成dto
        // todo 转换成dto
        // todo 转换成dto
        IntegralRewardSettingAggDto integralRewardSettingAggDto = JSONObject.parseObject(integralRewardSettingAggDtoStr, IntegralRewardSettingAggDto.class);
        return integralRewardSettingAggDto;
    }
}
