package com.jgw.supercodeplatform.marketing.mq.sender;


import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RabbitMqSender {
	private static Logger logger = Logger.getLogger(RabbitMqSender.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;
    
    public void sendUniqSeq(List<Map<String,Object>> data) {
        this.rabbitTemplate.convertAndSend(RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE, data);
    }
    
}

