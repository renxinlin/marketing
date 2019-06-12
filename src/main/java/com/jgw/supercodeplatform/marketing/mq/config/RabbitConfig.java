package com.jgw.supercodeplatform.marketing.mq.config;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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


	private static final String PUBLISH_SUBSCRIBE_EXCHANGE_DIRCT = "direct";
	/**
	 * 交换机类型:[主题]
	 * 功能:消息路由
	 * 描述: 相关route队列都可接收
 */
	private static final String PUBLISH_SUBSCRIBE_EXCHANGE_TOPIC= "topic";
	private static final String PUBLISH_SUBSCRIBE_EXCHANGE_HEARDERS= "headers";
	private static final String PUBLISH_SUBSCRIBE_EXCHANGE_FANOUT = "fanout";

}
