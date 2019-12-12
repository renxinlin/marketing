package com.jgw.supercodeplatform.marketing.scheduled;


import com.jgw.supercodeplatform.marketing.dao.integral.IntegralExchangeMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定时下架
 */
@Component
@Slf4j
public class TimingOffShelf {
     @Autowired
    private IntegralExchangeMapperExt mapper;
    /**
     * 每天凌晨00：00下架，1点确认
     */
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "*/5 * * * * ?")

    public void shelf(){
        if(log.isInfoEnabled()){
            log.info(" =======================================start timing off shelf=======================================");
        }
        List<IntegralExchange> readingToDb = new ArrayList<IntegralExchange>();
        List<IntegralExchange> needOffExchanges = mapper.getNeedOffExchange();
        for(IntegralExchange needOffExchange:needOffExchanges){
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
                if(new Date().getTime() >= underCarriage.getTime()){
                    // 需要下架
                    readingToDb.add(needOffExchange);
                }
            }
        }
        if(!CollectionUtils.isEmpty(readingToDb)){
            log.error(" =======================================update size is "+readingToDb.size()+" =======================================");
            try {
                mapper.undercarriage(readingToDb);
            } catch (Exception e) {
                log.error("自动下架出错"+e.getMessage());
                e.printStackTrace();
            }
        }else{
            log.error(" =======================================update size is zero =======================================");
        }
        if(log.isInfoEnabled()){
            log.info(" =======================================end timing off shelf=======================================");
        }
    }
}
