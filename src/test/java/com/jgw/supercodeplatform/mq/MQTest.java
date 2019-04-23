package com.jgw.supercodeplatform.mq;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.mq.sender.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class MQTest {

	@Autowired
	private RabbitMqSender sender;
	
	@Test
	public void send() {
		List<Map<String, Object>> data=new ArrayList<Map<String,Object>>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("key", 1);
		data.add(map);
		sender.sendUniqSeq(data);
		
		
	}
}
