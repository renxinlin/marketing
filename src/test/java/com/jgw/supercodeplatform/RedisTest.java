package com.jgw.supercodeplatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.RedisKey;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingMembersWinRecordMapper;
import com.jgw.supercodeplatform.marketing.dao.weixin.WXPayTradeOrderMapper;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class RedisTest {
	@Autowired
	private MarketingMembersWinRecordMapper mWinRecordMapper;

	@Autowired
	private RestTemplateUtil restTemplateUtil;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private CommonService commonService;
	
	
	@Value("${rest.user.url}")
	private String restUserUrl;

	@Value("亲爱的{{user}},恭喜成功注册成为{{organization}}的会员")
	private String registerMsgContent;

	private String codeManagerUrl = "http://PLATFORM-CODEMANAGER-SUPERCODE-ZC";

	@Test
	public void test2() throws IOException, SuperCodeException, InterruptedException {
		String organizationId = "18355c8780094ca1b786464fb652f471";
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("根据组织id请求组织名称时组织id不能为空", 500);
		}
		String organizationName = (String) redisUtil.hmGet(RedisKey.organizationId_prefix, organizationId);
		Long seconds =null;
		if (null == organizationName) {
			List<String> orgIds = new ArrayList<String>();
			orgIds.add(organizationId);
			JSONArray arr = commonService.getOrgsInfoByOrgIds(orgIds);
			organizationName = arr.getJSONObject(0).getString("organizationFullName");
			redisUtil.hmSet(RedisKey.organizationId_prefix, organizationId, organizationName);

			 seconds = redisUtil.leftExpireSeconds(RedisKey.organizationId_prefix);
            Thread.sleep(10000);
			if (null == seconds || seconds.intValue()==-1) {
				redisUtil.expire(RedisKey.organizationId_prefix, 7200, TimeUnit.SECONDS);
			}
			
			seconds = redisUtil.leftExpireSeconds(RedisKey.organizationId_prefix);
		}
		System.out.println(seconds);
	}
}
