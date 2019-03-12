package com.jgw.supercodeplatform.marketing.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;


/**
 * 消息队列配置文件
 * @author liujianqiang
 * @date 2018年1月16日
 */
@Configuration
public class RabbitConfig {
	
	@Bean(name = "publishBatch")   //SEARCH_TEST_QUEUE  || SEARCH_COUNT_QUEUE
	public Queue publishBatch() {
		return new Queue(RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE);
	}
	
}
