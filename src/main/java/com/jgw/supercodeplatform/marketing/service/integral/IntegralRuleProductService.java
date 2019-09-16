package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleProductMapperExt;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.dto.integral.Product;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;

@Service
public class IntegralRuleProductService extends AbstractPageService<DaoSearch>{
    private Logger logger = LoggerFactory.getLogger(IntegralRuleProductService.class);
  
    @Autowired
    private IntegralRuleProductMapperExt dao;

    @Autowired
    private IntegralRuleMapperExt inRuleMapperExt;
    
    @Autowired
    private CommonUtil commonUtil;
    
    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Autowired
    private CommonService commonService;
    
    @Value("${rest.codemanager.url}")
    private String codeManagerRestUrl;
    
    
    @Value("${rest.user.url}")
    private String restUserUrl;

	@Value("${marketing.domain.url}")
	private String marketingDomain;

	@Autowired
	private ModelMapper modelMapper;
	@Override
	protected List<IntegralRuleProduct> searchResult(DaoSearch searchParams) throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		DaoSearchWithOrganizationIdParam searchParamsDTO = modelMapper.map(searchParams, DaoSearchWithOrganizationIdParam.class);
		searchParamsDTO.setOrganizationId(organizationId);
		List<IntegralRuleProduct> list=dao.list(searchParamsDTO);
		return list;
	}

	@Override
	protected int count(DaoSearch searchParams) throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		DaoSearchWithOrganizationIdParam searchParamsDTO = modelMapper.map(searchParams, DaoSearchWithOrganizationIdParam.class);
		searchParamsDTO.setOrganizationId(organizationId);
		return dao.count(searchParamsDTO);
	}

	public void deleteByProductIds(List<String> productIds) throws SuperCodeException {
      if (null==productIds || productIds.isEmpty()) {
	 	throw new SuperCodeException("产品id不能为空", 500);
	  }	
      dao.deleteByProductIds(productIds);      
	}
	/**
	 * 产品积分设置流程：首先根据前端传的产品去基础平台获取批次，再组装参数请求码管理生码批次，再调用码管理绑定接口进行url与生码批次绑定
	 * @param bProductRuleParam
	 * @throws SuperCodeException
	 */
    @Transactional
	public void batchSetRule(BatchSetProductRuleParam bProductRuleParam) throws SuperCodeException {
		List<Product> products=bProductRuleParam.getProducts();	
		if (null==products || products.isEmpty()) {
			throw new SuperCodeException("产品不能为空", 500);
		}
		for (Product product : products) {
			if (StringUtils.isBlank(product.getProductId())) {
				throw new SuperCodeException("productId不能为空", 500);
			}
		}
		String organizationId=commonUtil.getOrganizationId();
		IntegralRule integralRule=inRuleMapperExt.selectByOrgId(organizationId);
		if (null==integralRule) {
			throw new SuperCodeException("当前企业未设置同意积分规则，请先设置通用积分规则", 500);
		}
		Byte memberType=0;
		Float perConsume=bProductRuleParam.getPerConsume();
		Float productPrice=bProductRuleParam.getProductPrice();
		Integer rewardIntegral=bProductRuleParam.getRewardIntegral();
		Byte rewardRule=bProductRuleParam.getRewardRule();
		
		List<String> productIds=new ArrayList<String>();
		List<IntegralRuleProduct> ruleProducts=new ArrayList<IntegralRuleProduct>();
		for (Product product : products) {
			productIds.add(product.getProductId());
		}
		List<IntegralRuleProduct> excludeRuleProducts=dao.selectByProductIdsAndOrgId(productIds,organizationId);
		if (null!=excludeRuleProducts && !excludeRuleProducts.isEmpty()) {
			StringBuffer buf=new StringBuffer();
			buf.append("产品  ");
			for (IntegralRuleProduct integralRuleProduct : excludeRuleProducts) {
				buf.append(integralRuleProduct.getProductName()).append(",");
			}
			buf.append("已设置过无法继续设置");
			throw new SuperCodeException(buf.toString(), 500);
		}
		String superToken=commonUtil.getSuperToken();
		//根据产品id集合去基础平台请求对应的产品批次
		JSONArray jsonArray= commonService.requestPriductBatchIds(productIds, superToken);
		//构建请求生码批次参数
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = constructProductAndBatchMOByPPArr(jsonArray);
		List<Map<String, Object>> updateProductList=new ArrayList<Map<String,Object>>();
		for (Product product : products) {
			String productId=product.getProductId();
			IntegralRuleProduct ruleProduct=new IntegralRuleProduct();
			ruleProduct.setIntegralRuleId(integralRule.getId());
			ruleProduct.setMemberType(memberType);
			ruleProduct.setOrganizationId(organizationId);
			ruleProduct.setPerConsume(perConsume);
			ruleProduct.setProductPrice(productPrice);
			ruleProduct.setRewardIntegral(rewardIntegral);
			ruleProduct.setRewardRule(rewardRule);
			ruleProduct.setProductId(productId);
			ruleProduct.setProductName(product.getProductName());
			ruleProducts.add(ruleProduct);
			if (null!=productPrice) {
				Map<String, Object> updateProductMap=new HashMap<String, Object>();
				updateProductMap.put("price", productPrice);
				updateProductMap.put("productId", productId);
				updateProductList.add(updateProductMap);
			}
		}
		//请求生码批次及积分url绑定批次
		integralUrlBindBatch(BusinessTypeEnum.INTEGRAL.getBusinessType(),superToken, productAndBatchGetCodeMOs);
		
		dao.batchInsert(ruleProducts);
		//更新产品营销信息
		updateBaseProductPrice(updateProductList,superToken);
		
	}
    
    
	@Transactional
	public void singleSetRuleProduct( IntegralRuleProduct inRuleProduct) throws SuperCodeException {
		inRuleProduct.setMemberType((byte)0);
		if (null==inRuleProduct.getId()) {
			
			String organizationId=commonUtil.getOrganizationId();
			IntegralRuleProduct eruleProduct=dao.selectByProductIdAndOrgId(inRuleProduct.getProductId(),organizationId);
			if (null!=eruleProduct) {
				throw new SuperCodeException("已存在该产品规则请带上主键id", 500);
			}
			dao.insertSelective(inRuleProduct);
			String superToken=commonUtil.getSuperToken();
			
			List<String> productIds=new ArrayList<String>();
			String productId=inRuleProduct.getProductId();
			if (StringUtils.isBlank(productId)) {
				throw new SuperCodeException("productId不能为空", 500);
			}
			
			productIds.add(productId);
			//根据产品id集合去基础平台请求对应的产品批次
			JSONArray jsonArray= commonService.requestPriductBatchIds(productIds, superToken);
			//构建请求生码批次参数
			List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = constructProductAndBatchMOByPPArr(jsonArray);
			integralUrlBindBatch(BusinessTypeEnum.INTEGRAL.getBusinessType(),superToken, productAndBatchGetCodeMOs);
			
			//更新产品营销信息
			List<Map<String, Object>> updateProductList=new ArrayList<Map<String,Object>>();
			Float price=inRuleProduct.getProductPrice();
			if (null!=price) {
				Map<String, Object> updateProductMap=new HashMap<String, Object>();
				updateProductMap.put("price", price);
				updateProductMap.put("productId", productId);
				updateProductList.add(updateProductMap);
				updateBaseProductPrice(updateProductList,superToken);
			}
		}else {
			if (StringUtils.isBlank(inRuleProduct.getProductId())) {
				inRuleProduct.setProductId(null);
			}
			
			if (StringUtils.isBlank(inRuleProduct.getProductName())) {
				inRuleProduct.setProductName(null);
			}
			
			dao.updateByPrimaryKeySelective(inRuleProduct);
		}
	}
	
    /**
     * 请求基础平台更新营销产品信息
     * @param updateProductList
     * @throws SuperCodeException 
     */
    private void updateBaseProductPrice(List<Map<String, Object>> updateProductList,String superToken) throws SuperCodeException {
    	if (null!=updateProductList && !updateProductList.isEmpty()) {
    		String json=JSONObject.toJSONString(updateProductList);
    		Map<String,String> headerMap=new HashMap<String, String>();
    		headerMap.put("super-token", superToken);
    		ResponseEntity<String> resopEntity=restTemplateUtil.putJsonDataAndReturnJosn(restUserUrl+CommonConstants.USER_BATCH_UPDATE_PRODUCT_MARKETING_INFO, json, headerMap);
    		logger.info("更新基础平台营销数据返回信息："+resopEntity.toString());
    		String body=resopEntity.getBody();
    		JSONObject bodyJosn=JSONObject.parseObject(body);
    		Integer state=bodyJosn.getInteger("state");
    		if (null==state || state.intValue()!=200) {
    			throw new SuperCodeException("请求基础平台批量更新产品营销信息出错："+bodyJosn.getString("msg"), 500);
    		}
		}
    }

	/**
     * 通过产品及产品批次对象集合封装通过产品及产品批次请求生码批次的参数
     * @param jsonArray
     * @return
     */
	private List<ProductAndBatchGetCodeMO> constructProductAndBatchMOByPPArr(JSONArray jsonArray) {
		Map<String, ProductAndBatchGetCodeMO> productMap=new HashMap<String, ProductAndBatchGetCodeMO>();
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject prodObject=jsonArray.getJSONObject(i);
			String productId=prodObject.getString("productId");
			//TODO 到时候要换成基础平台的批次
			String productBatchId=prodObject.getString("traceBatchInfoId");
			
			ProductAndBatchGetCodeMO  productAndBatchGetCodeMO=productMap.get(productId);
			if (null==productAndBatchGetCodeMO) {
				productAndBatchGetCodeMO=new ProductAndBatchGetCodeMO();
				productAndBatchGetCodeMO.setProductId(productId);
			}
			
			List<Map<String, String>> productBatchIdS=productAndBatchGetCodeMO.getProductBatchList();
			if (null==productBatchIdS) {
				productBatchIdS=new ArrayList<Map<String,String>>();
			}
			Map<String, String> batchmap=new HashMap<String, String>();
			batchmap.put("productBatchId", productBatchId);
			productBatchIdS.add(batchmap);
			productAndBatchGetCodeMO.setProductBatchList(productBatchIdS);
			productMap.put(productId, productAndBatchGetCodeMO);
		}
		List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs=new ArrayList<ProductAndBatchGetCodeMO>();
		for(Entry<String, ProductAndBatchGetCodeMO> entry:productMap.entrySet()) {
			productAndBatchGetCodeMOs.add(entry.getValue());
		}
		return productAndBatchGetCodeMOs;
	}

    /**
     * 请求生码批次及绑定积分url到生码批次
     * @param businessType
     * @param superToken
     * @param productAndBatchGetCodeMOs
     * @throws SuperCodeException
     */
	private void integralUrlBindBatch(int businessType, String superToken, List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs)
			throws SuperCodeException {
		if (null!=productAndBatchGetCodeMOs && !productAndBatchGetCodeMOs.isEmpty()) {
			String batchInfoBody=commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken,WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
			JSONObject obj=JSONObject.parseObject(batchInfoBody);
			int batchInfostate=obj.getInteger("state");
			if (200!=batchInfostate) {
				throw new SuperCodeException("积分设置时根据产品及批次获取码管理生码批次失败："+batchInfoBody, 500);
			}
			JSONArray batchArray=obj.getJSONArray("results");
			if (null==batchArray || batchArray.isEmpty()) {
				throw new SuperCodeException("该产品的批次未查到码关联信息，前检查是否已做过码关联的批次被删除", 500);
			}
			List<Map<String, Object>> batchInfoparams=commonService.getUrlToBatchParam(obj.getJSONArray("results"), marketingDomain+WechatConstants.SCAN_CODE_JUMP_URL,businessType);
			String bindBatchBody=commonService.bindUrlToBatch(batchInfoparams, superToken);
			JSONObject bindBatchobj=JSONObject.parseObject(bindBatchBody);
			int bindBatchstate=bindBatchobj.getInteger("state");
			if (200!=bindBatchstate) {
				throw new SuperCodeException("积分设置时根据生码批次绑定url失败："+bindBatchBody, 500);
			}
		}
	}

	public RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>>  unSelectPage(DaoSearch daoSearch) throws SuperCodeException {
		
		 RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>> restResult=new RestResult<AbstractPageService.PageResults<List<IntegralRuleProduct>>>();
		Map<String, Object>params=new HashMap<String, Object>();
		Integer current=daoSearch.getCurrent();
		Integer pagesize=daoSearch.getPageSize();
		
		params.put("current", current);
		params.put("pageSize", pagesize);
		String organizationId = commonUtil.getOrganizationId();
		params.put("organizationId",organizationId );
		params.put("search", daoSearch.getSearch());
		List<String> productIds = dao.selectProductIdsByOrgId(organizationId);
		if (null!=productIds && !productIds.isEmpty()) {
			params.put("excludeProductIds",String.join(",", productIds));
		}
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("super-token", getSuperToken());
		ResponseEntity<String> responseEntity = restTemplateUtil.getRequestAndReturnJosn(codeManagerRestUrl+CommonConstants.CODEMANAGER_RELATION_PRODUCT_URL, params, headerMap);
		String body = responseEntity.getBody();
		logger.info("接收到码管理进行过码关联的产品信息："+body);
		JSONObject json=JSONObject.parseObject(body);
		int state=json.getInteger("state");
		List<IntegralRuleProduct> ruleproductList=new ArrayList<IntegralRuleProduct>();
		if (state==200) {
			restResult.setState(200);
			JSONArray arry=json.getJSONObject("results").getJSONArray("list");
  			for (int i=0 ;i<arry.size();i++) {
 				JSONObject ruleProduct=arry.getJSONObject(i);
 				String prductId = ruleProduct.getString("productId");
// 				if(productIds == null || !productIds.contains(prductId)) {
					IntegralRuleProduct product=new IntegralRuleProduct();
	 				product.setId(ruleProduct.getString("esId"));
	 				product.setProductId(prductId);
					product.setProductName(ruleProduct.getString("productName"));
					ruleproductList.add(product);
// 				}
			}
			String pagination_str=json.getJSONObject("results").getString("pagination");
			com.jgw.supercodeplatform.marketing.common.page.Page page=JSONObject.parseObject(pagination_str, com.jgw.supercodeplatform.marketing.common.page.Page.class);
			AbstractPageService.PageResults<List<IntegralRuleProduct>> pageResults=new PageResults<List<IntegralRuleProduct>>(ruleproductList, page);
		    restResult.setResults(pageResults);
		}else {
			throw new SuperCodeException("请求码管理产品出错", 500);
		}
		return restResult;
	}

	public IntegralRuleProduct selectByProductIdAndOrgId(String productId, String organizationId) {
		return dao.selectByProductIdAndOrgId(productId, organizationId);
	}
    
    
}
