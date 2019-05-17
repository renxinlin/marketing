package com.jgw.supercodeplatform.marketing.mq.receiver;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
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
	/**
	 * 监听防伪消息队列，发送消息发送到es中心，es执行写入
	 * @param map
	 */
    @RabbitListener(queues = RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE)
    public void doMessage(List<Map<String, Object>> batchList) {
        logger.info("mq开始消费--------------->>>>>>>>>>接收到数据data="+batchList);
       if (null!=batchList && !batchList.isEmpty()) {
    	   service.handleNewBindBatch(batchList);
	   }
        logger.info("mq消息消费完成");
    }
  
}
