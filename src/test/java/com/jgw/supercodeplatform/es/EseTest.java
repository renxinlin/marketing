package com.jgw.supercodeplatform.es;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class EseTest {
	@Autowired
	private CodeEsService cService;
	
	@Test
	public void add() throws SuperCodeException {
		cService.addScanCodeRecord("oeVn5sq-wk7_MH4jN2BUQ_fSRv-A", "0067a46e580e4b6cbc79b8c55576f617", "38fc70f6ed984c24b090a2421ba72375", "codeId", "codeTypeId", 2l, "2019-03-11");
	}
	
	@Test
	public void test2() throws SuperCodeException {
		String openId="oeVn5sq-wk7_MH4jN2BUQ_fSRv-A".replaceAll("-", "").replaceAll("_", "");
		cService.addScanCodeRecord(openId, "0067a46e580e4b6cbc79b8c55576f617", "38fc70f6ed984c24b090a2421ba72375", "codeId", "codeTypeId", 2l, "2019-03-11");
		Long count=cService.countByUserAndActivityQuantum(openId, 2l, "2019-03-11");
		System.out.println(count);
	}
	
	@Test
	public void test3() {
		 Map<String, Object> addParam=new HashMap<String, Object>();
//		 addParam.put("userId.keyword", "oeVn5sqwk7MH4jN2BUQfSRvA");
     	  addParam.put("scanCodeTime", "2019-03-11");
////		  addParam.put("activitySetId", 2l);
		  
		  EsSearch eSearch=new EsSearch();
		  eSearch.setIndex(EsIndex.MARKETING);
		  eSearch.setType(EsType.INFO);
		  eSearch.setParam(addParam);
		  Long count2= cService.getCount(eSearch);	
		  cService.delete(eSearch);
//		List<SearchHit> count=cService.selectScanCodeRecord("codeId", "codeTypeId");
		System.out.println(count2);
	}
}
