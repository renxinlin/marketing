package com.jgw.supercodeplatform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupercodeplatformApplicationTests {

	@Test
	public void contextLoads() {
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
