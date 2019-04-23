package com.jgw.supercodeplatform.mapper;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class IntegralRuleMapperTest {

@Autowired
private IntegralRuleMapperExt ruleDao;

	
	@Test
	public void insert() {
		IntegralRule rule=new IntegralRule();
		ruleDao.insert(rule);
	}
}
