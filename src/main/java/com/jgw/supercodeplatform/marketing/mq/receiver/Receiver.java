package com.jgw.supercodeplatform.marketing.mq.receiver;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFatchChainCompoment;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.CouponAutoFecthService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.mq.CommonMqTaskService;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public class Receiver {

	private static Logger logger = Logger.getLogger(Receiver.class);

	@Autowired
	private CommonMqTaskService service;
	@Autowired
	private AutoFatchChainCompoment autoFecthProcess;

	@Autowired
	private CouponAutoFecthService couponAutoFecthService;

    @RabbitListener(queues = RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE)
    public void doMessage(List<Map<String, Object>> batchList) {
        logger.info("mq开始消费--------------->>>>>>>>>>接收到数据data="+batchList);
       if (null!=batchList && !batchList.isEmpty()) {
    	   service.handleNewBindBatch(batchList);
    	   // 业务解耦
		   autoFecthProcess.initchains(couponAutoFecthService);
		   autoFecthProcess.fireBiz(batchList);
	   }
        logger.info("mq消息消费完成");
    }

}
