package com.jgw.supercodeplatform.marketing.scheduled;

import com.jgw.supercodeplatform.marketing.config.redis.RedisLockUtil;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingWxTradeOrderService;
import com.jgw.supercodeplatform.marketing.service.weixin.WXPayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Slf4j
@Component
public class PlatformPaySchedule {

    public static final String SEND_FAIL_WX_ORDER = "PlatformPaySchedule:sendFailWxOrder";

    @Autowired
    private MarketingWxTradeOrderService tradeOrderService;

    @Autowired
    private WXPayService payService;

    @Autowired
    private RedisLockUtil lock;

    /**
     * 每天早晨八点钟处理全网运营活动红包雨失败的微信订单，只处理气七天之内的
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void sendFailWxOrder(){
        boolean acquireLock = false;
        try {
            acquireLock = lock.lock(SEND_FAIL_WX_ORDER, 60000, 1, 100);
            if (!acquireLock) {
                log.info("未获取到{}锁", SEND_FAIL_WX_ORDER);
                return;
            }
            //只查找50天之内的
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -50);
            List<WXPayTradeOrder> orderList = tradeOrderService.searchFailOrder(5L, cal.getTime());
            if (CollectionUtils.isNotEmpty(orderList)) {
                for (WXPayTradeOrder order : orderList) {
                    String  openid = order.getOpenId();
                    String  spbill_create_ip = order.getRemoteAddr();
                    int amount = order.getAmount().intValue();
                    String  partner_trade_no = order.getPartnerTradeNo();
                    String organizationId = order.getOrganizationId();
                    payService.qiyePayAsycPlatform(openid, spbill_create_ip, amount, partner_trade_no, organizationId);
                }
            }

        } catch (Exception e){
            log.error("定时处理出错微信订单获取锁出错", e);
        } finally {
            if(acquireLock){
                lock.releaseLock(SEND_FAIL_WX_ORDER);
            }
        }
    }
}
