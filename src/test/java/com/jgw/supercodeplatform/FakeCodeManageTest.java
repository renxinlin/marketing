package com.jgw.supercodeplatform;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class FakeCodeManageTest {
	
	/**
	 * 测试获取token		用户名:18268932378_8757	  密码：123456m
	 * http://localhost:7075/code/manage/api/getToken
	 * @return
	 */
	@Test
	public String getToken(){
		RestTemplate restTemplate = new RestTemplate();
		String getTokenUrl = "http://192.168.2.222:7777/permission";
		String accountId = "18268932378_8757";	//18268932378   liu   
		String password = "123456m";		//123456m		123456
		Map<String, Object> param = new HashMap<>();
		param.put("accountId", accountId);
		param.put("password", password);
		RestResult result = restTemplate.postForObject(getTokenUrl, param, RestResult.class);	//获取token返回结果
		String token = "";
		if(result != null){
			LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) result.getResults();
			token = resultMap.get("token").toString();
		}
		return token;
	}
	
	/**
	 * 获取用户生码信息
	 * @param params
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	public Object getUserInfo(@RequestBody Map<String, Object> params){
		String generateCodeUrl = "http://192.168.2.222:7777/domain";
		RestTemplate template = new RestTemplate();
		String jsonObj = JSONObject.toJSONString(params);
		String token = getUserToken();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		//headers.set("Authorization", token);
		headers.set("token", token);
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObj, headers);
		ResponseEntity<RestResult> response = template.exchange(generateCodeUrl, HttpMethod.POST, requestEntity, RestResult.class);
		
		response.getBody().getResults();
		
		JSONObject obj = (JSONObject) JSONObject.toJSON(response.getBody().getResults());
		
		return obj;
	}
	
	/**
	 * 根据用户名和密码获取token
	 * @return
	 */
	public static String getUserToken(){
		RestTemplate restTemplate = new RestTemplate();
		String getTokenUrl = "http://192.168.2.222:7777/permission";
		String userToken = "";
		String accountId = "18268932378_8757";	//用户名     
		String password = "123456m";		//密码
		Map<String, Object> param = new HashMap<>();
		param.put("accountId", accountId);
		param.put("password", password);
		RestResult result = restTemplate.postForObject(getTokenUrl, param, RestResult.class);	//获取token返回结果
		if(result != null && result.getState().equals(200)){
			LinkedHashMap<String, Object> resultMap = (LinkedHashMap<String, Object>) result.getResults();
			userToken = resultMap.get("token").toString();
			return userToken;
		}else{
			return userToken;
		}
	}
	
	
}
