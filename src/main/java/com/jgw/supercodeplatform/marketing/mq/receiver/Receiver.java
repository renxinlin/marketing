package com.jgw.supercodeplatform.marketing.mq.receiver;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFatchChainCompoment;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.CouponAutoFecthService;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.IntegralAutoFecthService;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl.PrizeWheelsAutoFetchService;
import com.jgw.supercodeplatform.marketing.service.activity.MarketingActivitySetService;
import com.jgw.supercodeplatform.marketing.service.mq.CommonMqTaskService;

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
public class Receiver  implements InitializingBean {

	private static Logger logger = Logger.getLogger(Receiver.class);

	@Autowired
	private CommonMqTaskService service;
	@Autowired
	private AutoFatchChainCompoment<List<Map<String, Object>>> autoFecthProcess;
	/**
	 * 抵扣券自动追加处理
	 */
	@Autowired
	private CouponAutoFecthService couponAutoFecthService;
	/**
	 * 大转盘自动追加处理
	 */
	@Autowired
	private PrizeWheelsAutoFetchService prizeWheelsAutoFetchService;

	/**
	 * 积分绑定追加
	 */
	@Autowired
	private IntegralAutoFecthService integralAutoFecthService;

	public  void test(){

		String productId = "cab7cccdb80842b884b3223fa67a10b5";
				String productBatchId ="60502";
		String codeBatch = "91fa75c8a6614d68b9fef6d00d8551c0";
		Map map = new HashedMap();
		map.put("productId",productId);
		map.put("productBatchId",productBatchId);
		map.put("codeBatch",codeBatch);
		List<Map<String, Object>> batchList  = new ArrayList<>();
		batchList.add(map);
		doMessage(batchList);
	}
    @RabbitListener(queues = RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE)
    public void doMessage(List<Map<String, Object>> batchList) {
        logger.info("mq开始消费--------------->>>>>>>>>>接收到数据data="+batchList);
       if (null!=batchList && !batchList.isEmpty()) {
    	   service.handleNewBindBatch(batchList);
    	   // 业务解耦:处理抵扣券 大转盘
		   autoFecthProcess.fireBiz(batchList);
	   }
        logger.info("mq消息消费完成");
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		//  注意： a p a i
		// autowired > afterPropertiesSet
        // 执行链 执行顺序 SystemChainService >  couponAutoFecthService > prizeWheelsAutoFetchService
		autoFecthProcess.initchains(couponAutoFecthService,prizeWheelsAutoFetchService, integralAutoFecthService);
	}
}
