package com.jgw.supercodeplatform.marketing.mq.receiver;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
@Slf4j
public class ReceiverUnbinding     {


	/**
	 * 解绑队列
	 * @param batchList
	 */
	@RabbitListener(queues = RabbitMqQueueName.UN_PUSH_BATCH_DATA_QUEUE)
	public void doMessage(List<Map<String, Object>> batchList) {

	}





}
