package com.jgw.supercodeplatform.marketing.constants;

/**
 */
public interface RabbitMqQueueName {


	/**
	 * 推送批次数据队列
	 */
	String PUSH_BATCH_DATA_QUEUE = "push_batch_data_queue";

	/**
	 * 解除绑定队列
	 */
	String UN_PUSH_BATCH_DATA_QUEUE = "un_push_batch_data_queue";


	/**
	 * 解除绑定交换机：发布订阅
	 */
   String PUB_SUB_EXCHANGE ="product_unbinding_code" ;

}
