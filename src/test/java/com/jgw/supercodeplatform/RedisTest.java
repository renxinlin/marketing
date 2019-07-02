package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONArray;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketing.service.es.activity.CodeEsService;

import redis.clients.jedis.JedisCommands;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class RedisTest {
/*	@Autowired
	private MarketingMembersWinRecordMapper mWinRecordMapper;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonService commonService;*/
	@Autowired CodeEsService codeEsService;
	
/*	@Value("${rest.user.url}")
	private String restUserUrl;

	@Value("亲爱的{{user}},恭喜成功注册成为{{organization}}的会员")
	private String registerMsgContent;*/

//	private String codeManagerUrl = "http://PLATFORM-CODEMANAGER-SUPERCODE-ZC";
//
//	@Test
//	public void test2() throws IOException, SuperCodeException, InterruptedException {
//		String organizationId = "18355c8780094ca1b786464fb652f471";
//		if (StringUtils.isBlank(organizationId)) {
//			throw new SuperCodeException("根据组织id请求组织名称时组织id不能为空", 500);
//		}
//		String organizationName = (String) redisUtil.hmGet(RedisKey.organizationId_prefix, organizationId);
//		Long seconds =null;
//		if (null == organizationName) {
//			List<String> orgIds = new ArrayList<String>();
//			orgIds.add(organizationId);
//			JSONArray arr = commonService.getOrgsInfoByOrgIds(orgIds);
//			organizationName = arr.getJSONObject(0).getString("organizationFullName");
//			redisUtil.hmSet(RedisKey.organizationId_prefix, organizationId, organizationName);
//
//			 seconds = redisUtil.leftExpireSeconds(RedisKey.organizationId_prefix);
//            Thread.sleep(10000);
//			if (null == seconds || seconds.intValue()==-1) {
//				redisUtil.expire(RedisKey.organizationId_prefix, 7200, TimeUnit.SECONDS);
//			}
//			
//			seconds = redisUtil.leftExpireSeconds(RedisKey.organizationId_prefix);
//		}
//		System.out.println(seconds);
//	}
//	
//	@Autowired
//	private StringRedisTemplate redisTemplate;
//	
	@Test
	public void test11() throws Exception {
		int i = codeEsService.countCodeIdScanNum("9000000216793083", "14");
		System.out.println("--->"+i);
	}
	
	
	/*public static void main(String[] args) {
		String activityStartDateStr = "2019-06-27 00:00:00.012";
		
		try {
			long mills = DateUtils.parseDate(activityStartDateStr, CommonConstants.DATE_PATTERNS).getTime();
			System.out.println("--->"+mills);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	
}
