package com.jgw.supercodeplatform.marketingsaler.base.task;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.marketing.common.util.DateUtil;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.service.SalerRuleExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时下架:TODO 1优化采用scan 2设置status 而不是全部数据
 */
@Component
public class SalerAutoUnderTask {
    private static Logger logger= LoggerFactory.getLogger(SalerAutoUnderTask.class);
    @Autowired
    private SalerRuleExchangeService salerRuleExchangeService;
    /**
     * 每天凌晨00：00下架，1点确认
     */
//    @Scheduled(cron = "0 0 0,1 * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")
    public void shelf(){
        if(logger.isInfoEnabled()){
            logger.info(" =======================================导购积分兑换红包定时任务=======================================");
        }
        List<SalerRuleExchange> readingToDb = new ArrayList<>();

        Wrapper<SalerRuleExchange> queryWrapper = new QueryWrapper<>();
        List<SalerRuleExchange> needOffExchanges = salerRuleExchangeService.list(((QueryWrapper<SalerRuleExchange>) queryWrapper).eq("Status",3));
        for(SalerRuleExchange needOffExchange:needOffExchanges){
            if(needOffExchange.getUndercarriageSetWay() == (byte) 0){
                // 库存为0自动下架
                if(0 == needOffExchange.getHaveStock()){
                    // 需要下架
                    readingToDb.add(needOffExchange);
                }
            }
            if(needOffExchange.getUndercarriageSetWay() == (byte) 1){
                // 时间到了自动下架
                Date underCarriage = needOffExchange.getUnderCarriage();
                if(new Date().getTime() > underCarriage.getTime()){
                    // 需要下架
                    readingToDb.add(needOffExchange);
                }
            }
        }
        if(!CollectionUtils.isEmpty(readingToDb)){
            readingToDb.forEach(data->data.setStatus((byte)2));
            logger.error(" 导购积分兑换红包=======================================update size is "+readingToDb.size()+" =======================================");
            try {
                salerRuleExchangeService.updateBatchById(readingToDb);
            } catch (Exception e) {
                logger.error("自动下架出错"+e.getMessage());
                e.printStackTrace();
            }
        }else{
            logger.error(" 导购积分兑换红包=======================================update size is zero =======================================");
        }
        if(logger.isInfoEnabled()){
            logger.info(" 导购积分兑换红包=======================================end timing off shelf=======================================");
        }
    }

}
