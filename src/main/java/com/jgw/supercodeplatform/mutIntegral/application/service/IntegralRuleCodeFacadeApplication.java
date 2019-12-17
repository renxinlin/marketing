package com.jgw.supercodeplatform.mutIntegral.application.service;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.mutIntegral.application.transfer.IntegralRuleTransfer;
import com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.integralconfig.agg.IntegralRuleDomain;
import com.jgw.supercodeplatform.mutIntegral.domain.repository.IntegralRuleRepository;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.ErrorConstants;
import com.jgw.supercodeplatform.mutIntegral.infrastructure.mysql.pojo.IntegralSegmentCode;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.IntegralRuleDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.dto.rulerewardproduct.IntegralRewardSettingAggDto;
import com.jgw.supercodeplatform.mutIntegral.interfaces.view.IntegralRuleRewardCommonVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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


    public IntegralRewardSettingAggDto getIntegralRewardSettingAgg(){
        return null;
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
        checkHaveSetting(integralRewardSettingAggDto);
        singleCodeApplication.setsingleCodeForIntegralRule(integralRewardSettingAggDto.getSingleCodeDtos());
        segmentCodeApplication.setsegmentCodeForIntegralRule(integralRewardSettingAggDto.getSegmentCodeDtos());
        sBatchApplication.setsBatchForIntegralRule(integralRewardSettingAggDto.getSbatchDtos());
        productApplication.setProductForIntegralRule(integralRewardSettingAggDto.getProductAggDto());




        // 发送最新的业务给码管理
        // TODO 需要发送吗


    }

    private void checkHaveSetting(IntegralRewardSettingAggDto integralRewardSettingAggDto) {
        Asserts.check(integralRewardSettingAggDto != null, ErrorConstants.nullError);
        if(
                integralRewardSettingAggDto.getProductAggDto() == null
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSingleCodeDtos() )
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSegmentCodeDtos() )
                        && CollectionUtils.isEmpty(integralRewardSettingAggDto.getSbatchDtos() )
        ){
            throw new BizRuntimeException(ErrorConstants.nullError);
        }
    }


}
