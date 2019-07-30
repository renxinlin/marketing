package com.jgw.supercodeplatform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.IpUtils;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SuperCodeMarketingApplication.class)
public class SupercodeplatformApplicationTests {

	@SuppressWarnings("deprecation")
	@Autowired
	AsyncRestTemplate asyncRestTemplate;
	
	@Test
	public void contextLoads() {
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("judgeType", "3");
    	uriVariables.put("outerCodeId", "15617069648739002741");
    	uriVariables.put("codeTypeId","20");
    	uriVariables.put("ipAddr","61.164.59.252");
        HttpHeaders requestHeaders = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        requestHeaders.setContentType(type);
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
        //body
        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(uriVariables), requestHeaders);
        ListenableFuture<ResponseEntity<JSONObject>> rel = asyncRestTemplate.postForEntity("http://PLATFORM-ANTISMASHINGGOODS-SUPERCODE-DEV"+CommonConstants.JUDGE_FLEE_GOOD, requestEntity, JSONObject.class);
		//ListenableFuture<ResponseEntity<String>> rel = asyncRestTemplate.getForEntity("http://PLATFORM-LOGISTICS-SUPERCODE-TEST/logistics/sweep/out/product?outerCodeIds=[15617069648739002741]", String.class);
		rel.addCallback(new ListenableFutureCallback<ResponseEntity<JSONObject>>() {

			@Override
			public void onFailure(Throwable ex) {
				ex.printStackTrace();
				
			}

			@Override
			public void onSuccess(ResponseEntity<JSONObject> result) {
				// TODO Auto-generated method stub
				System.err.println("----->"+result);
			}

			
		});
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	 public Set<MarketingChannel> getSonByFatherWithAllData(Map<String, MarketingChannel> marketingChannelMap) {
		 Set<MarketingChannel> channelSet = new HashSet<>();
		 Collection<MarketingChannel> channelCollection = marketingChannelMap.values();
		 for(MarketingChannel marketingChannel : channelCollection) {
			 MarketingChannel channel = putChildrenChannel(marketingChannelMap, marketingChannel);
			 if(channel != null)
				 channelSet.add(channel);
		 }
		 return channelSet;
	 }
	 
	private MarketingChannel putChildrenChannel(Map<String, MarketingChannel> marketingChannelMap, MarketingChannel channel) {
		MarketingChannel reChannel = null;
		Set<String> mkSet = marketingChannelMap.keySet();
		if(mkSet.contains(channel.getCustomerSuperior())) {
			MarketingChannel parentChannel = marketingChannelMap.get(channel.getCustomerSuperior());
			List<MarketingChannel> childList = parentChannel.getChildren();
			//如果父级的children为空，则说明第一次添加，需递归调用，如果不为空，则说明不是第一次添加，
			//以前已经递归调用过，父级以上的关系已添加过，不用再次递归，也无需返回实例。
			if(childList == null) {
				childList = new ArrayList<>();
				childList.add(channel);
				parentChannel.setChildren(childList);
				reChannel = putChildrenChannel(marketingChannelMap, parentChannel);
			} else {
				childList.add(channel);
			}
		} else {
			reChannel = channel;
		}
		return reChannel;
	}
		 
		 
	 
	
}
