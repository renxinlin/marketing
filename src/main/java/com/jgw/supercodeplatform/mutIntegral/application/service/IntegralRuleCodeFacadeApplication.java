package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.ReadSettingInfoRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralRewardSettingAggDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 积分设置按产品按号段按批次按单码 门面
 */
@Service
@Slf4j
public class IntegralRuleCodeFacadeApplication {


    @Autowired
    private IntegralSingleCodeApplication singleCodeApplication;


    @Autowired
    private IntegralSegmentCodeApplication segmentCodeApplication;



    @Autowired
    private IntegralSBatchApplication sBatchApplication;



    @Autowired
    private IntegralProductApplication productApplication;

    @Autowired
    private ReadSettingInfoRepository readSettingInfoRepository;

    /**
     * 获取快照信息
     * @return
     */
    public IntegralRewardSettingAggDto getIntegralRewardSettingAgg(){
        return readSettingInfoRepository.getSnapshotInfo();
    }

    /**
     * 设置积分产品号段码单码批次信息
     * 要求码关联产品
     * 无须码关联单码 号段码等
     * @param integralRewardSettingAggDto
     */
    @Transactional
    public void setSettingInfo(IntegralRewardSettingAggDto integralRewardSettingAggDto) {
        /**
         *
         */
        // TODO 转到领域服务业务校验
        // todo 为null的时候不设置但进行删除[业务，业务元信息]
        checkCanBeenSetting(integralRewardSettingAggDto);
        singleCodeApplication.setsingleCodeForIntegralRule(integralRewardSettingAggDto.getSingleCodeDtos());
        segmentCodeApplication.setsegmentCodeForIntegralRule(integralRewardSettingAggDto.getSegmentCodeDtos());
        sBatchApplication.setsBatchForIntegralRule(integralRewardSettingAggDto.getSbatchDtos());
        productApplication.setProductForIntegralRule(integralRewardSettingAggDto.getProductAggDto());




        // 发送最新的业务给码管理
        // TODO 删除旧的码管理跳转信息

        // TODO 需要发送码管理新的跳转信息

        // TODO 读请求快照
        readSettingInfoRepository.updateSnapshotInfo(integralRewardSettingAggDto);




    }

    private void checkCanBeenSetting(IntegralRewardSettingAggDto integralRewardSettingAggDto) {
        Asserts.check(integralRewardSettingAggDto != null, MutiIntegralCommonConstants.nullError);
        if(
                integralRewardSettingAggDto.getProductAggDto() == null
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSingleCodeDtos() )
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSegmentCodeDtos() )
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSbatchDtos() )
        ){
            throw new BizRuntimeException(MutiIntegralCommonConstants.nullError);
        }
    }


}
