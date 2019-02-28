package com.jgw.supercodeplatform.es;

import java.util.List;

import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class EseTest {
	@Autowired
	private CodeEsService cService;
	
	@Test
	public void test1() {
	}
	
	@Test
	public void test2() {
		Long count=cService.countByCode("133", "144");
		System.out.println(count);
	}
	
	@Test
	public void test3() {
		List<SearchHit> count=cService.selectScanCodeRecord("133", "144");
		System.out.println(count);
	}
}
