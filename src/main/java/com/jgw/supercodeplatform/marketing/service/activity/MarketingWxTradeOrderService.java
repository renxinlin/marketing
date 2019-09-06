package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.CommandAPDU;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketingWxTradeOrderService {

    @Autowired
    private WXPayService payService;

    private CommonUtil commonUtil;

    @Autowired
    private WXPayTradeOrderMapper payTradeOrderMapper;

    /**
     * 发送红包
     * @param openId 用户的微信ID
     * @param winningCode 中奖码
     * @return
     * @throws Exception
     */
    public String sendPayTradeOrder(String openId, String winningCode) throws Exception {
        Map<String, Object> resMap = new HashMap<>();
        String organizationId = commonUtil.getOrganizationId();
        WXPayTradeOrder payTradeOrder = payTradeOrderMapper.selectByCodeId(winningCode, openId);
        if(payTradeOrder == null){
            return "找不到该码对应的订单";
        }
        if(!organizationId.equals(payTradeOrder.getOrganizationId())){
            return  "当前登录组织非该订单创建对应的组织";
        }
        payService.qiyePay(payTradeOrder.getOpenId(),payTradeOrder.getRemoteAddr(),payTradeOrder.getAmount().intValue(),payTradeOrder.getPartnerTradeNo(), organizationId);
        return null;
    }

}
