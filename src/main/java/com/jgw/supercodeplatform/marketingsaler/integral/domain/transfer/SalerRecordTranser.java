package com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer;

import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.common.UserConstants;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleReward;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.User;

import java.math.BigDecimal;
import java.util.Date;

public class SalerRecordTranser {
    public static SalerRecord getSalerRecord(String outCodeId, SalerRuleReward reward, H5LoginVO user, User userPojo, SalerRuleReward rewardPojo) {
        SalerRecord salerRecord = new SalerRecord();
        salerRecord.setCodeTypeId(UserConstants.MARKETING_CODE_TYPE);
        salerRecord.setOuterCodeId(outCodeId);
        salerRecord.setCreateDate(new Date());
        salerRecord.setCustomerId(userPojo.getCustomerId());
        salerRecord.setCustomerName(userPojo.getCustomerName());
        salerRecord.setSalerId(userPojo.getId());
        salerRecord.setSalerName(userPojo.getUserName());
        salerRecord.setSalerMobile(userPojo.getMobile());
        salerRecord.setIntegralNum(rewardPojo.getRewardIntegral());
        salerRecord.setOrganizationId(user.getOrganizationId());
        salerRecord.setOrganizationName(user.getOrganizationName());
        salerRecord.setProductId(reward.getProductId());
        salerRecord.setProductName(reward.getProductName());
        salerRecord.setProductPrice(rewardPojo.getProductPrice() ==null ? null:new BigDecimal(rewardPojo.getProductPrice().toString()));
        salerRecord.setIntegralReason(IntegralReasonEnum.SALER_GET_INTEGREL.getIntegralReason());
        salerRecord.setIntegralReasonCode(IntegralReasonEnum.SALER_GET_INTEGREL.getIntegralReasonCode());
        return salerRecord;
    }


}
