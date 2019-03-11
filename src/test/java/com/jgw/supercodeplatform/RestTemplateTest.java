package com.jgw.supercodeplatform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembersWinRecord;
import com.jgw.supercodeplatform.marketing.pojo.pay.WXPayTradeOrder;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class RestTemplateTest {
@Autowired
private WXPayTradeOrderMapper dao;
@Autowired
private MarketingMembersWinRecordMapper mWinRecordMapper;

@Autowired
private RestTemplateUtil restTemplateUtil;
@Value("${rest.codemanager.url}")
private String codeManagerUrl;
	@Test
	public void selectByNo() throws SuperCodeException {
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs=new ArrayList<ProductAndBatchGetCodeMO>();
		ProductAndBatchGetCodeMO pGetCodeMO=new ProductAndBatchGetCodeMO();
		
		List<Map<String,String>> productBatchList=new ArrayList<Map<String,String>>();
		Map<String,String> map=new HashMap<String, String>();
		map.put("productBatchId", "dbdd589a370c47c5bfbde4f1b6419b44");
		productBatchList.add(map);
		
		pGetCodeMO.setProductBatchList(productBatchList);
		pGetCodeMO.setProductId("dbdd589a370c47c5bfbde4f1b6419b44");
		
		productAndBatchGetCodeMOs.add(pGetCodeMO);
		String jsonData=JSONObject.toJSONString(productAndBatchGetCodeMOs);
		Map<String,String> headerMap=new HashMap<String, String>();
		headerMap.put("super-token", "9b6d023cd4ed495a82aaf8ed4bf32b6f");
		ResponseEntity<String> resp=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+"/code/relation/getBatchInfo", jsonData, headerMap);
		String body=resp.getBody();
		System.out.println(body);
	}
	
	
}
