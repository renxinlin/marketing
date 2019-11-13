package com.jgw.supercodeplatform.prizewheels.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.exception.BizRuntimeException;
import com.jgw.supercodeplatform.marketing.exception.PrizeWheelsWeixinPayException;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.MoneyTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.RewardTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.event.CdkEvent;
import com.jgw.supercodeplatform.prizewheels.domain.model.*;
import com.jgw.supercodeplatform.prizewheels.domain.publisher.CdkEventPublisher;
import com.jgw.supercodeplatform.prizewheels.domain.repository.RecordRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardCdkRepository;
import com.jgw.supercodeplatform.prizewheels.domain.repository.WheelsRewardRepository;
import com.jgw.supercodeplatform.prizewheels.domain.subscribers.CdkEventSubscriber;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 领域服务 : 仅处理WheelsReward 直接实现类
 */
@Service
@Slf4j
public class WheelsRewardDomainService {
    @Autowired
    private CdkEventPublisher cdkEventPublisher;

    @Autowired
    @Qualifier("cdkEventSubscriberImpl")
    private CdkEventSubscriber cdkEventSubscriber;

    @Autowired
    @Qualifier("cdkEventSubscriberImplV2")
    private CdkEventSubscriber cdkEventSubscriberV2;
    @Autowired
    private WheelsRewardCdkRepository wheelsRewardCdkRepository;

    @Autowired
    private RecordRepository recordRepository;


    @Autowired
    private WXPayService wxPayService;

    @Autowired
    private WheelsRewardRepository wheelsRewardRepository;


    public void checkWhenUpdate(List<WheelsReward> wheelsRewards) {
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());

        for(WheelsReward wheelsReward : wheelsRewards){
            // Asserts.check(wheelsReward.getId()!= null && wheelsReward.getId() > 0,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
            Asserts.check(wheelsReward.getPrizeWheelId()  != null && wheelsReward.getPrizeWheelId() > 0
                    ,ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        }

        double pro = 0D;
        for(WheelsReward wheelsReward : wheelsRewards){
            if(wheelsReward.getType().byteValue() == RewardTypeConstant.real){
                Asserts.check(wheelsReward.getSendDay() != null && wheelsReward.getSendDay() > 0,"实物发货时间必填");
                Asserts.check(wheelsReward.getStock() != null && wheelsReward.getStock() >= 0,"实物库存必填");
            }

            if(wheelsReward.getType().byteValue() == RewardTypeConstant.money){
                Asserts.check(wheelsReward.getMoneyType() !=null,"未指定固定金额或随机金额");
                if(wheelsReward.getMoneyType() == MoneyTypeConstant.fixed){
                    Asserts.check(wheelsReward.getFixedMoney()!=null,"请填写固定金额");
                }

                if(wheelsReward.getMoneyType() == MoneyTypeConstant.random){
                    Asserts.check(wheelsReward.getRandLowMoney()!=null,"请填写下限金额");
                    Asserts.check(wheelsReward.getRandHighMoney()!=null,"请填写上限金额");
                    Asserts.check(
                            wheelsReward.getRandHighMoney().doubleValue()
                                    >= wheelsReward.getRandLowMoney()
                            ,"上限金额应该大于下限");
                }

            }

            pro = pro + wheelsReward.getProbability();
        };
        Asserts.check(pro == 100D,"概率总和100%");
    }


    public void initPrizeWheelsid(List<WheelsReward> wheelsRewards, Long prizeWheelsid) {
        for(WheelsReward wheelsReward : wheelsRewards){
            wheelsReward.setPrizeWheelId(prizeWheelsid);
        }
    }

    public void checkWhenAdd(List<WheelsReward> wheelsRewards) {
        Asserts.check(!CollectionUtils.isEmpty(wheelsRewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        double pro = 0D;

        for(WheelsReward wheelsReward : wheelsRewards){
            pro = pro + wheelsReward.getProbability();
            if(wheelsReward.getType().byteValue() == RewardTypeConstant.real){
                Asserts.check(wheelsReward.getSendDay() != null && wheelsReward.getSendDay() > 0,"实物收货时间必填");
                Asserts.check(wheelsReward.getStock() != null && wheelsReward.getStock() >= 0,"实物库存必填");
            }

            if(wheelsReward.getType().byteValue() == RewardTypeConstant.money){
                Asserts.check(wheelsReward.getMoneyType() !=null,"未指定固定金额或随机金额");
                if(wheelsReward.getMoneyType() == MoneyTypeConstant.fixed){
                    Asserts.check(wheelsReward.getFixedMoney()!=null,"请填写固定金额");
                }

                if(wheelsReward.getMoneyType() == MoneyTypeConstant.random){
                    Asserts.check(wheelsReward.getRandLowMoney()!=null,"请填写下限金额");
                    Asserts.check(wheelsReward.getRandHighMoney()!=null,"请填写上限金额");
                    Asserts.check(
                            wheelsReward.getRandHighMoney().doubleValue()
                            >= wheelsReward.getRandLowMoney()
                            ,"上限金额应该大于下限");
                }

            }
        };
        Asserts.check(pro == 100D,"概率总和100%");
    }


    public void cdkEventCommitedWhenNecessary(List<WheelsReward> wheelsRewards) {
        for(WheelsReward wheelsReward : wheelsRewards){
            Asserts.check(wheelsReward.getId()!= null, ErrorCodeEnum.NULL_ERROR.getErrorMessage());
            if(!StringUtils.isEmpty(wheelsReward.getCdkUuid())){
                CdkEvent cdkEvent = new CdkEvent(wheelsReward.getId(), wheelsReward.getCdkUuid());
//                cdkEventPublisher.addSubscriber(cdkEventSubscriber);   // excel 已经导入,关联wheelsRewards主键即可  前端组件不支持我也是醉了
                cdkEventPublisher.addSubscriber(cdkEventSubscriberV2); // excel导入到七牛云,此时读excel
                log.info("======================cdk{}",JSONObject.toJSONString(cdkEvent));
                cdkEventPublisher.publish(cdkEvent);

            }
        }

    }

    /**
     * c端用户领取这个奖项
     * @param finalReward
     * @param user
     * @param outerCodeId
     * @param codeTypeId
     */
    public H5RewardInfo getReward(WheelsReward finalReward, H5LoginVO user, String outerCodeId, String codeTypeId,Long prizeWheelsId) {
        // 领取成功 cdk - 1 领取记录
        if(finalReward.getType().intValue() == RewardTypeConstant.virtual){
            // TODO Repository做了业务，提到上层
            WheelsRewardCdk cdkWhenH5Reward = wheelsRewardCdkRepository.getCdkWhenH5Reward(finalReward.getId());


            WheelsRecord wheelsRecord = new WheelsRecord();
            wheelsRecord.initvirtualInfo(
                    user.getMobile()
                    ,finalReward.getName()
                    ,user.getMemberId().toString()
                    ,user.getMemberName()
                    ,prizeWheelsId,finalReward.getId()
                    ,user.getOrganizationName()
                    ,user.getOrganizationId()
                    ,cdkWhenH5Reward.getCdk());

            recordRepository.newRecordWhenH5Reward(wheelsRecord);

            H5RewardInfo rewardInfo = new H5RewardInfo();
            rewardInfo.initVirtualRewardInfo(finalReward.getId(),finalReward.getPicture(),cdkWhenH5Reward.getCdk());
            return rewardInfo;

        }

        if(finalReward.getType().intValue() == RewardTypeConstant.real){
            int success = wheelsRewardRepository.reduceStockForReal(finalReward);
            Asserts.check(success == 1,"您好，库存不足啦");

            WheelsRecord wheelsRecord = new WheelsRecord();
            //  TODO 记录领取金额
            wheelsRecord.initrealInfo(
                    user.getMobile()
                    ,finalReward.getName()
                    ,user.getMemberId().toString()
                    ,user.getMemberName()
                    ,prizeWheelsId,finalReward.getId()
                    ,user.getOrganizationName()
                    ,user.getOrganizationId());


            recordRepository.newRecordWhenH5Reward(wheelsRecord);

            //

            H5RewardInfo rewardInfo = new H5RewardInfo();
            rewardInfo.initRealReward(finalReward.getId(),finalReward.getPicture(),finalReward.getName(),finalReward.getSendDay());
            return rewardInfo;


        }



        if(finalReward.getType().intValue() == RewardTypeConstant.money){
            int success = wheelsRewardRepository.reduceStockForMoney(finalReward);
            Asserts.check(success == 1,"您好,奖池领完啦");
            double rewardMoneyForUser = MoneyCalculator.buildRewardMoney(finalReward);
            try {
                // (int)(rewardMoneyForUser*100)微信精度分 奖项精度元
                wxPayService.qiyePaySync(user.getOpenid(), CallBackConstant.serverIp,(int)(rewardMoneyForUser*100), UUID.randomUUID().toString().replaceAll("-",""),user.getOrganizationId());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("大转盘红包支付失败.........................finalReward{},user{}",finalReward,JSONObject.toJSONString(user));
//                throw new BizRuntimeException("微信支付，支付失败啦！");
                // restresult结果给前端打标 异常内容产品提供
                throw new PrizeWheelsWeixinPayException("支付失败=未中奖");
            }
            WheelsRecord wheelsRecord = new WheelsRecord();
            // TODO money 奖励记录等产品提出需求，那些字段
            wheelsRecord.initrealInfo(
                    user.getMobile()
                    ,finalReward.getName()
                    ,user.getMemberId().toString()
                    ,user.getMemberName()
                    ,prizeWheelsId,finalReward.getId()
                    ,user.getOrganizationName()
                    ,user.getOrganizationId());


            recordRepository.newRecordWhenH5Reward(wheelsRecord);


            H5RewardInfo rewardInfo = new H5RewardInfo();
            rewardInfo. initMoneyReward(finalReward.getId(),finalReward.getPicture(),finalReward.getName(),rewardMoneyForUser);
            return rewardInfo;


        }

        throw new BizRuntimeException("奖励类型暂不支持...");
    }

    /**
     * 初始化初始库存
     * @param wheelsRewards
     */
    public void initInitialStock(List<WheelsReward> wheelsRewards) {
        wheelsRewards.forEach(wheelsReward->wheelsReward.initInitialStock());
    }
}
