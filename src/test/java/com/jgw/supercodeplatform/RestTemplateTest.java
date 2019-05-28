package com.jgw.supercodeplatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.config.redis.RedisUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
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
private CommonUtil commonUtil;

@Value("${marketing.integral.h5page.urls}")
private String integrals;


@Autowired
private RedisUtil redisUtil;

@Autowired
private RestTemplate restTemplate;

@Value("${rest.user.url}")
private String restUserUrl;

@Value( "亲爱的{{user}},恭喜成功注册成为{{organization}}的会员")
private  String registerMsgContent ;

private String codeManagerUrl="http://PLATFORM-CODEMANAGER-SUPERCODE-ZC";

@Value("${rest.user.url}")
private String userServiceUrl;

private String msCodeUrl="http://PLATFORM-MS-CODETEST";

@Value("${marketing.domain.url}")
private String wxauthRedirectUri;

@Test
public void unSelectPage() throws SuperCodeException {
	Map<String, Object>params=new HashMap<String, Object>();
	Integer current=1;
	Integer pagesize=15;
	
	if (null!=current && null!=pagesize) {
		params.put("current", 1);
		params.put("pageSize", pagesize);
	}
	String organizationId="86ff1c47b5204e88918cb89bbd739f12";
	params.put("organizationId",organizationId );
	params.put("search", null);
	
	List<String>productIds=new ArrayList<String>();
	productIds.add("6ddcdfa718314dbeba04e297ba064bd2");
	productIds.add("afcf7384963e4b5c914296e15113c500");
	
	if (null!=productIds && !productIds.isEmpty()) {
		params.put("excludeProductIds",String.join(",", productIds));
	}
	ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(codeManagerUrl+CommonConstants.CODEMANAGER_RELATION_PRODUCT_URL, params, null);
	String body=responseEntity.getBody();
	JSONObject json=JSONObject.parseObject(body);
	System.out.println(json);
}
@Test
public  void test1() throws UnsupportedEncodingException, SuperCodeException {
    String wholeUrl=wxauthRedirectUri+"/marketing/front/auth/code";
	String encoderedirectUri=URLEncoder.encode(wholeUrl, "utf-8");
	System.out.println(encoderedirectUri);
	
//	Map<String, Object>params=new HashMap<String, Object>();
//	String organizationId="3d096f49448e4444b97d9a79aaa21f13";
//	params.put("organizationId",organizationId );
//	params.put("productBatchIds","e46604d24aa54f9091e054adaae020dd,e46604d24aa54f9091e054adaae020dd");
//	
//	ResponseEntity<String>responseEntity=getRequestAndReturnJosn(codeManagerUrl+CommonConstants.CODEMANAGER_RELATION_PRODUCT_PRODUCT_BATCH, params, null);
//    System.out.println(responseEntity.toString());
}


@Test
public  void requestOrgName() throws UnsupportedEncodingException, SuperCodeException {
	List<String>productIds=new ArrayList<String>();
	productIds.add("86ff1c47b5204e88918cb89bbd739f12");
	
	Map<String, Object> params=new HashMap<String, Object>();
	params.put("organizationIds", JSONObject.toJSONString(productIds));
	ResponseEntity<String>responseEntity=getRequestAndReturnJosn(restUserUrl+CommonConstants.USER_REQUEST_ORGANIZATION_BATCH, params, null);
    System.out.println(responseEntity.toString());
}

/**
 * 发送get请求返回json数据
 * @param url
 * @param params 可以传递value为list的情况;会剔除null的相关情况
 * @param headerMap
 * @return
 * @throws SuperCodeException
 */
public ResponseEntity<String> getRequestAndReturnJosn(String url,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeException {
	if (StringUtils.isBlank(url)) {
		throw new SuperCodeException("sendGetRequestAndReturnJosn参数url不能为空", 500);
	}
	HttpHeaders headers = new HttpHeaders();
	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	if (null!=headerMap && !headerMap.isEmpty()) {
		for(String key:headerMap.keySet()) {
			headers.add(key, headerMap.get(key));
		}
	}
    UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(url);
	if (null!=params && !params.isEmpty()) {
		for(String key:params.keySet()) {
			Object value=params.get(key);
			builder.queryParam(key,  value);
		}
	}

    HttpEntity<?> entity = new HttpEntity<>(headers);
    ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
    return result;
}
@Test
public  void main() throws UnsupportedEncodingException, SuperCodeException {
	long start=System.currentTimeMillis();
	String userName="周几了";
	String organizationFullName="阿里巴巴科技又像是";
	String msg=registerMsgContent.replace("{{user}}",userName).replace("{{organization}}",organizationFullName);
	String mobile="13805766204";
	Map msgData = new HashMap();
	msgData.put("mobileId",mobile);
	msgData.put("sendContent",msg);
	String jsonData= JSONObject.toJSONString(msgData);
	String iso8859 = new String(jsonData.getBytes("iso8859-1"));
	String gbk = new String(jsonData.getBytes("gbk"));
	String utf8 = new String(jsonData.getBytes("utf-8"));
	if(iso8859.equals(jsonData.toString())){
		System.out.println("iso8859");
	}else  if(gbk.equals(jsonData.toString())){
		System.out.println("gbk");
	}else  if(utf8.equals(jsonData.toString())){
		System.out.println("utf8");
	}
	Map<String, String> headerMap = new HashMap<>();
	headerMap.put("charset","UTF-8");
	restTemplateUtil.postJsonDataAndReturnJosnObject(userServiceUrl+ WechatConstants.SMS_SEND_PHONE_MESSGAE, msgData, headerMap);
	long end=System.currentTimeMillis();
	System.out.println("耗时："+(end-start)/1000+"秒");
	System.out.println("over");
}

	@Test
	public void selectByNo() throws SuperCodeException {
//		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs=new ArrayList<ProductAndBatchGetCodeMO>();
//		ProductAndBatchGetCodeMO pGetCodeMO=new ProductAndBatchGetCodeMO();
//		
//		List<Map<String,String>> productBatchList=new ArrayList<Map<String,String>>();
//		Map<String,String> map=new HashMap<String, String>();
//		map.put("productBatchId", "dbdd589a370c47c5bfbde4f1b6419b44");
//		productBatchList.add(map);
//		
//		pGetCodeMO.setProductBatchList(productBatchList);
//		pGetCodeMO.setProductId("dbdd589a370c47c5bfbde4f1b6419b44");
//		
//		productAndBatchGetCodeMOs.add(pGetCodeMO);
//		String jsonData=JSONObject.toJSONString(productAndBatchGetCodeMOs);
//		Map<String,String> headerMap=new HashMap<String, String>();
////		headerMap.put("super-token", "9b6d023cd4ed495a82aaf8ed4bf32b6f");
//		ResponseEntity<String> resp=restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl+"/code/relation/getBatchInfo", jsonData, headerMap);
//		String body=resp.getBody();
//		System.out.println(body);
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
	
	@Test
	public void test2() throws IOException, SuperCodeException {
      System.out.println(integrals);
      Map<String, String> headerMap = new HashMap<>();
      headerMap.put("super-token", "d2c0617286dd4db0a25b315f413ac0b5");
      Map<String, Object> paramMap = new HashMap<String, Object>();
      List<String> list=new ArrayList<String>();
      list.add("d572ee82753f48bb84b2242284dad52e");
      paramMap.put("productIds", "d572ee82753f48bb84b2242284dad52e");
      
  	  ResponseEntity<String> response= restTemplateUtil.getRequestAndReturnJosn(restUserUrl+"/product-batch/array/batch/list", paramMap, headerMap);
      System.out.println(response);

	}
	
	
	@Test
	public  void checkOuterCode() throws UnsupportedEncodingException, SuperCodeException {
		
		Map<String, String>headerparams=new HashMap<String, String>();
		headerparams.put("token",commonUtil.getCodePlatformToken() );
		ResponseEntity<String>responseEntity=getRequestAndReturnJosn(msCodeUrl + "/outer/info/one?outerCodeId=86580781309180006&codeTypeId=122", null, headerparams);
	    System.out.println(responseEntity.toString());
	    
	}
}
