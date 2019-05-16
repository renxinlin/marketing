package com.jgw.supercodeplatform.mapper;

import com.jgw.supercodeplatform.SuperCodeMarketingApplication;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class MarketingActivitySetMapperTest {
@Autowired
private MarketingActivitySetMapper maSetMapper;

@Autowired
private MarketingActivityProductMapper mProductMapper;
	@Test
	public void test() {
		MarketingActivitySet marketingActivitySet=new MarketingActivitySet();
		marketingActivitySet.setActivityEndDate("");
		marketingActivitySet.setActivityId(14l);
		maSetMapper.insert(marketingActivitySet);
		
//		maSetMapper.addCodeTotalNum(1L, 2L);
	}

	@Test
	public void batchDelete() {
		MarketingActivityProduct marketingActivitySet1=new MarketingActivityProduct();
		marketingActivitySet1.setProductBatchId("49df91ad1d7c420b823dd48f425a9148");
		marketingActivitySet1.setProductId("6ddcdfa718314dbeba04e297ba064bd2");
		
		MarketingActivityProduct marketingActivitySet2=new MarketingActivityProduct();
		marketingActivitySet2.setProductBatchId("5c155b23c6974b5db74a75b73f05082c");
		marketingActivitySet2.setProductId("afcf7384963e4b5c914296e15113c500");
		
		List<MarketingActivityProduct> list=new ArrayList<MarketingActivityProduct>();
		list.add(marketingActivitySet1);
		list.add(marketingActivitySet2);
		
//		maSetMapper.addCodeTotalNum(1L, 2L);
	}
}
