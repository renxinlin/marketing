package com.jgw.supercodeplatform.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class MarketingActivitySetMapperTest {
@Autowired
private MarketingActivitySetMapper maSetMapper;

	@Test
	public void test() {
		maSetMapper.addCodeTotalNum(1L, 2L);
	}

}
