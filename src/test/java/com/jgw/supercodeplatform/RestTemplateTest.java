package com.jgw.supercodeplatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
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


//扫码时保存产品和码信息到内存，待授权后根据授权state值获取
private String  MARKETING_GLOBAL_SCAN_CODE_INFO="marketing:cache:scanCodeInfo";
private String  MARKETING_GLOBAL_CACHE ="marketing:cache:wxMerchants";


public void putScanCodeInfoMO(String wxsate, ScanCodeInfoMO scanCodeInfoMO) throws SuperCodeException {
	if (StringUtils.isBlank(wxsate)) {
		throw new SuperCodeException("wxstae为空", 500);
	}
	if(scanCodeInfoMO == null){
		throw new SuperCodeException("扫码信息为空", 500);
	}
    
	redisUtil.hmSet(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate,JSONObject.toJSONString(scanCodeInfoMO));
}


public Long deleteScanCodeInfoMO(String wxsate) throws SuperCodeException {
	if (StringUtils.isBlank(wxsate)) {
		throw new SuperCodeException("wxstae为空", 500);
	}
	return redisUtil.deleteHmKey(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate);

}



public  ScanCodeInfoMO getScanCodeInfoMO(String wxsate) throws SuperCodeException {
	if (StringUtils.isBlank(wxsate)) {
		throw new SuperCodeException("获取扫码缓存信息时参数wxsate不能为空", 500);
	}
	String json =(String) redisUtil.hmGet(MARKETING_GLOBAL_SCAN_CODE_INFO, wxsate);
	ScanCodeInfoMO scanCodeInfoMO=JSONObject.parseObject(json, ScanCodeInfoMO.class);
	if (null==scanCodeInfoMO) {
		throw new SuperCodeException("根据wxsate="+wxsate+"无法获取扫码缓存信息请重新扫码", 500);
	}
	
	return scanCodeInfoMO;
}
@Test
public void unSelectPage() throws SuperCodeException {
	Map<String, Object>params=new HashMap<String, Object>();
	Integer current=1;
	Integer pagesize=10;
	
	if (null!=current && null!=pagesize) {
		params.put("startNumber", (current-1)*pagesize);
		params.put("pageSize", pagesize);
	}
	String organizationId="86ff1c47b5204e88918cb89bbd739f12";
	params.put("organizationId",organizationId );
	params.put("search", null);
	
	List<String>productIds=new ArrayList<String>();
	productIds.add("6ddcdfa718314dbeba04e297ba064bd2");
	productIds.add("afcf7384963e4b5c914296e15113c500");
	params.put("excludeProductIds",productIds);
	ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(codeManagerUrl+CommonConstants.RELATION_PRODUCT_URL, params, null);
	String body=responseEntity.getBody();
	JSONObject json=JSONObject.parseObject(body);
	System.out.println(json);
}
@Test
public  void test1() throws UnsupportedEncodingException, SuperCodeException {
	ScanCodeInfoMO scanCodeInfoMO=new ScanCodeInfoMO();
	scanCodeInfoMO.setCodeId("22655");
	putScanCodeInfoMO("1", scanCodeInfoMO);
	
	ScanCodeInfoMO scanCodeInfoMO2=getScanCodeInfoMO("1");
	System.out.println(scanCodeInfoMO2);
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
	public void test2() throws IOException {

		
	}
}
