package com.jgw.supercodeplatform;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class)
public class IntegralCouponTest {
	
	/*@Autowired
	private CommonService commonService;
	@Autowired
	private MarketingActivityProductMapper marketingActivityProductMapper;*/
	@Autowired
	RestTemplateUtil restTemplateUtil;
	String marketingDomain = "http://marketing.kf315.net";
	
//	@Test
//	public void test() {
//		
//		try {
//			List<MarketingActivityProduct> prdList = marketingActivityProductMapper.selectActivityProductList();
//			saveProductBatchs(prdList, null, "8bca3f51d43e487983c5a01a32baa7fa",false);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	
//	private void saveProductBatchs(List<MarketingActivityProduct> prdList, List<Map<String,Object>> deleteProductBatchList, String superToken, boolean send) throws SuperCodeException {
//		Map<String, List<String>> prdAbatchMap = new HashMap<>();
//		prdList.forEach(prd -> {
//			String prdId = prd.getProductId();
//			String prdBatchId = prd.getProductBatchId();
//			List<String> prdBatchIdList = prdAbatchMap.get(prdId);
//			if(prdBatchIdList == null) {
//				prdBatchIdList = Lists.newArrayList(prdBatchId);
//				prdAbatchMap.put(prdId, prdBatchIdList);
//			} else {
//				if(!prdBatchIdList.contains(prdBatchId)) prdBatchIdList.add(prdBatchId);
//			}
//		});
//		
//		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = new ArrayList<ProductAndBatchGetCodeMO>();
//		List<MarketingActivityProduct> mList = new ArrayList<MarketingActivityProduct>();
//			for(Entry<String, List<String>> entry : prdAbatchMap.entrySet()) {	
//				ProductAndBatchGetCodeMO productAndBatchGetCodeMO = new ProductAndBatchGetCodeMO();
//				List<Map<String, String>> productBatchList = new ArrayList<Map<String, String>>();
//				String productId = entry.getKey();
//				List<String> productBatchIdList = entry.getValue();
//				for (String productBatchId : productBatchIdList) {
//					// 拼装请求码管理批次信息接口商品批次参数
//					Map<String, String> batchmap = new HashMap<String, String>();
//					batchmap.put("productBatchId", productBatchId);
//					productBatchList.add(batchmap);
//				}
//				// 拼装请求码管理批次信息接口商品参数
//				productAndBatchGetCodeMO.setProductBatchList(productBatchList);
//				productAndBatchGetCodeMO.setProductId(productId);
//				productAndBatchGetCodeMOs.add(productAndBatchGetCodeMO);
//			}
//		getProductBatchSbatchId(productAndBatchGetCodeMOs, prdList, superToken);
//		//如果是会员活动需要去绑定扫码连接到批次号
//		String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken, WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
//		JSONObject obj = JSONObject.parseObject(body);
//		int state = obj.getInteger("state");
//		if (HttpStatus.SC_OK == state) {
//			JSONArray arr = obj.getJSONArray("results");
//			
//			List<Map<String, Object>> paramsList = commonService.getUrlToBatchParam(arr, marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,BusinessTypeEnum.MARKETING_COUPON.getBusinessType());
//			if(!CollectionUtils.isEmpty(deleteProductBatchList)) {
//				String delbatchBody = commonService.deleteUrlToBatch(deleteProductBatchList, superToken);
//				JSONObject delBatchobj = JSONObject.parseObject(delbatchBody);
//				Integer delBatchstate = delBatchobj.getInteger("state");
//				if (null != delBatchstate && delBatchstate.intValue() != HttpStatus.SC_OK) {
//					throw new SuperCodeException("请求码删除生码批次和url错误：" + delbatchBody, HttpStatus.SC_INTERNAL_SERVER_ERROR);
//				}
//			}
////			// 绑定生码批次到url
////			String bindbatchBody = commonService.bindUrlToBatch(paramsList, superToken);
////			System.err.println(bindbatchBody);
////			JSONObject bindBatchobj = JSONObject.parseObject(bindbatchBody);
////			Integer batchstate = bindBatchobj.getInteger("state");
////			if (null != batchstate && batchstate.intValue() != HttpStatus.SC_OK) {
////				throw new SuperCodeException("请求码管理生码批次和url错误：" + bindbatchBody, HttpStatus.SC_INTERNAL_SERVER_ERROR);
////			}
////			Map<String, Map<String, Object>> paramsMap = commonService.getUrlToBatchParamMap(arr, marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL,
////					BusinessTypeEnum.MARKETING_COUPON.getBusinessType());
////			mList.forEach(marketingActivityProduct -> {
////				String key = marketingActivityProduct.getProductId()+","+marketingActivityProduct.getProductBatchId();
////				marketingActivityProduct.setSbatchId((String)paramsMap.get(key).get("batchId"));
////			});
//		} else {
//			throw new SuperCodeException("通过产品及产品批次获取码信息错误：" + body, HttpStatus.SC_INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	
//	public void getProductBatchSbatchId(List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs, List<MarketingActivityProduct> mList,String superToken) throws SuperCodeException {
//		// 营销绑定生码批次
//		String body = commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,
//				WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
//		JSONObject obj = JSONObject.parseObject(body);
//		int state = obj.getInteger("state");
//		if (200 == state) {
//			// 生码批次数组
//			Map<String, ActivitySet<String>> productSbathIds = new HashMap<>();
//			JSONArray arr = obj.getJSONArray("results");
//
//			mList.forEach(marketingActivityProduct -> {
//				ActivitySet<String> sbathIds = new HashSet<>();
//				for(int i=0;i<arr.size();i++) {
//					// 码管理回参类型
//					String globalBacthId = arr.getJSONObject(i).getString("globalBacthId");
//					String productId = arr.getJSONObject(i).getString("productId");
//					String productBatchId = arr.getJSONObject(i).getString("productBatchId");
//					sbathIds.add(globalBacthId);
//					if(marketingActivityProduct.getProductId().equals(productId)
//							&& marketingActivityProduct.getProductBatchId().equals(productBatchId)){
//						sbathIds.add(globalBacthId);
//						productSbathIds.put(productId+productBatchId,sbathIds);
//					}
//				}
//				ActivitySet<String> sbathIdsDto = productSbathIds.get(marketingActivityProduct.getProductId() + marketingActivityProduct.getProductBatchId());
//				if(!CollectionUtils.isEmpty(sbathIdsDto)){
//					String[] sbathIdsDtoArray = new String[sbathIdsDto.size()];
//					sbathIdsDto.toArray(sbathIdsDtoArray);
//					String sbatchId = StringUtils.join(sbathIdsDtoArray, ",");
//					marketingActivityProduct.setSbatchId(sbatchId);
//				}
//
//			});
//		} else {
//			throw new SuperCodeException("通过产品及产品批次获取码信息错误：" + body, 500);
//		}
//	}
	
	@Test
	public void main1() {
		Map<String, Object> map1 = new HashMap<>();
		map1.put("batchId", 60306);
		map1.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map1.put("businessType", 6);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("batchId", 60320);
		map2.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map2.put("businessType", 6);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("batchId", 60322);
		map3.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map3.put("businessType", 6);
		Map<String, Object> map4 = new HashMap<>();
		map4.put("batchId", 60323);
		map4.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map4.put("businessType", 6);
		Map<String, Object> map5 = new HashMap<>();
		map5.put("batchId", 60318);
		map5.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map5.put("businessType", 5);
		Map<String, Object> map6 = new HashMap<>();
		map6.put("batchId", 60322);
		map6.put("url", "http://marketing.kf315.net/marketing/front/scan/");
		map6.put("businessType", 5);
		List<Map<String, Object>> mapList = Lists.newArrayList(map1,map2,map3,map4,map5,map6);
		for(Map<String, Object> map : mapList) {
			String deleteJson=JSONObject.toJSONString(Lists.newArrayList(map));
			Map<String,String> headerMap=new HashMap<String, String>();
			headerMap.put("super-token", "f1b3e8c538cc4daf9f13b0d8ea60b73d");
			ResponseEntity<String> bindBatchresponse;
			try {
				bindBatchresponse = restTemplateUtil.postJsonDataAndReturnJosn("http://192.168.2.215:1008"+WechatConstants.CODEMANAGER_DELETE_BATCH_TO_URL, deleteJson, headerMap);
				System.err.println("请求码管理删除批次与url返回数据:"+bindBatchresponse.toString());
			} catch (SuperCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
