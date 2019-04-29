package com.jgw.supercodeplatform.es;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class EseTest {
	@Autowired
	private CodeEsService cService;
	
	@Test
	public void add() throws SuperCodeException, ParseException {
//		cService.addScanCodeRecord("oeVn5sq-wk7_MH4jN2BUQ_fSRv-A", "0067a46e580e4b6cbc79b8c55576f617", "38fc70f6ed984c24b090a2421ba72375", "codeId", "codeTypeId", 2l, "2019-03-11");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(new Date());
		Date d=format.parse(time); 
		long scantime=d.getTime();
		Long count=cService.countIntegralByUserIdAndDate(83l, scantime,"86ff1c47b5204e88918cb89bbd739f12");
		System.out.println(count);
	}
	
	@Test
	public void test2() throws SuperCodeException {
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date d=format.parse("2019-04-27"); 
		
		long time=d.getTime();
		System.out.println(time);
	}
	
	@Test
	public void test3() throws ParseException, SuperCodeException {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(new Date());
		Date d=format.parse(time);
		String openId="oeVn5sq-wk7_MH4jN2BUQ_fSRv-A".replaceAll("-", "").replaceAll("_", "");
		cService.addScanCodeRecord(openId, "59547d98e7984f3da3fd157b15d7bcf4", "59547d98e7984f3da3fd157b15d7bcf4", "20268964098434485L", "12", 245L, d.getTime());
		
		 Map<String, Object> addParam=new HashMap<String, Object>();
//		 addParam.put("userId.keyword", "oeVn5sgnslZcCSrigGXb3HE5A");
//     	  addParam.put("scanCodeTime", d.getTime());
//		  addParam.put("activitySetId", 245l);
		  
		  addParam.put("codeId.keyword", "20268969617396421");
		  addParam.put("codeType.keyword", "12");
		  EsSearch eSearch=new EsSearch();
		  eSearch.setIndex(EsIndex.MARKETING);
		  eSearch.setType(EsType.INFO);
		  eSearch.setParam(addParam);
		  Long count2= cService.getCount(eSearch);	
//		  cService.delete(eSearch);
//		List<SearchHit> count=cService.selectScanCodeRecord("codeId", "codeTypeId");
		System.out.println(count2);
	}
}
