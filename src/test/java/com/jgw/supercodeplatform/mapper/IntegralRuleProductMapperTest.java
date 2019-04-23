package com.jgw.supercodeplatform.mapper;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleProductMapperExt;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class IntegralRuleProductMapperTest {

@Autowired
private IntegralRuleProductMapperExt ruleDao;

	
	@Test
	public void test() {
		List<String> productIds=new ArrayList<String>();
		productIds.add("1");
//		productIds.add("");
		
		List<IntegralRuleProduct> products=ruleDao.selectByProductIdsAndOrgId(productIds, "86ff1c47b5204e88918cb89bbd739f12");
		System.out.println(products);
	}
}
