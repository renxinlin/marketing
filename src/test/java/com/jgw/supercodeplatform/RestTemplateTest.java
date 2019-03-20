package com.jgw.supercodeplatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class RestTemplateTest {
@Autowired
private WXPayTradeOrderMapper dao;
@Autowired
private MarketingMembersWinRecordMapper mWinRecordMapper;

@Autowired
private RestTemplateUtil restTemplateUtil;

@Autowired
private RestTemplate restTemplate;

@Value("${rest.user.url}")
private String restUserUrl;

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
	
	@Test
	public void test() throws IOException {
		String url = restUserUrl+"/file/upload";
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		File file=new File("H:\\test\\gg.jpg");

		FileInputStream fis = new FileInputStream(file);
		byte[] bytesArray = new byte[fis.available()];
		fis.read(bytesArray); //read file into bytes[]
		fis.close();

		ByteArrayResource contentsAsResource = new ByteArrayResource(bytesArray) {
		    @Override
		    public String getFilename() {
		        return "img";
		    }
		};
		paramMap.add("file", contentsAsResource);
		paramMap.add("name", "ss.jpg");
		HttpHeaders headers = new HttpHeaders();
		headers.set("super-token", "d6303ff76b6743fc88ee25e4d74ead25");
		headers.set("content-type", "multipart/form-data");
        
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, headers);
		ResponseEntity<String> data=restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
//		JSONObject json = restTemplate.postForObject(httpMethod, paramMap, JSONObject.class);
		System.out.println("post json : " + data);
	}
	
}
