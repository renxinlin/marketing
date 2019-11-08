package com.jgw.supercodeplatform.marketing.mq.receiver;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFatchChainCompoment;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.CouponAutoFecthService;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.PrizeWheelsAutoFetchService;
import com.jgw.supercodeplatform.marketing.service.mq.CommonMqTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
