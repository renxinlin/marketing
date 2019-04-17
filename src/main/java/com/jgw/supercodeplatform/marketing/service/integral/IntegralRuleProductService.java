package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
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
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleProductMapperExt;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam.Product;
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
		return super.searchResult(searchParams);
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
		String organizationId=commonUtil.getOrganizationId();
		IntegralRule integralRule=inRuleMapperExt.selectByOrgId(organizationId);
		if (null==integralRule) {
			throw new SuperCodeException("当前企业未设置同意积分规则，请先设置通用积分规则", 500);
		}
		Byte memberType=bProductRuleParam.getMemberType();
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
		for (Product product : products) {
			IntegralRuleProduct ruleProduct=new IntegralRuleProduct();
			ruleProduct.setIntegralRuleId(integralRule.getId());
			ruleProduct.setMemberType(memberType);
			ruleProduct.setOrganizationId(organizationId);
			ruleProduct.setPerConsume(perConsume);
			ruleProduct.setProductPrice(productPrice);
			ruleProduct.setRewardIntegral(rewardIntegral);
			ruleProduct.setRewardRule(rewardRule);
			ruleProduct.setProductId(product.getProductId());
			ruleProduct.setProductName(product.getProductName());
			ruleProducts.add(ruleProduct);
		}
		dao.batchInsert(ruleProducts);
		//请求生码批次及积分url绑定批次
		integralUrlBindBatch(1,superToken, productAndBatchGetCodeMOs);
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
			String productBatchId=prodObject.getString("productBatchId");
			
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
			String batchInfoBody=commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken);
			JSONObject obj=JSONObject.parseObject(batchInfoBody);
			int batchInfostate=obj.getInteger("state");
			if (200!=batchInfostate) {
				throw new SuperCodeException("积分设置时根据产品及批次获取码管理生码批次失败："+batchInfoBody, 500);
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

	@Transactional
	public void singleSetRuleProduct( IntegralRuleProduct inRuleProduct) throws SuperCodeException {
		if (null==inRuleProduct.getId()) {
			String organizationId=commonUtil.getOrganizationId();
			IntegralRuleProduct eruleProduct=dao.selectByProductIdAndOrgId(inRuleProduct.getProductId(),organizationId);
			if (null!=eruleProduct) {
				throw new SuperCodeException("已存在该产品规则请带上主键id", 500);
			}
			dao.insertSelective(inRuleProduct);
			String superToken=commonUtil.getSuperToken();
			
			List<String> productIds=new ArrayList<String>();
			productIds.add(inRuleProduct.getProductId());
			//根据产品id集合去基础平台请求对应的产品批次
			JSONArray jsonArray= commonService.requestPriductBatchIds(productIds, superToken);
			//构建请求生码批次参数
			List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs = constructProductAndBatchMOByPPArr(jsonArray);
			integralUrlBindBatch(1,superToken, productAndBatchGetCodeMOs);
		}else {
			dao.updateByPrimaryKeySelective(inRuleProduct);
		}
	}

	public JSONObject unSelectPage(DaoSearch daoSearch) throws SuperCodeException {
		Map<String, Object>params=new HashMap<String, Object>();
		Integer current=daoSearch.getCurrent();
		Integer pagesize=daoSearch.getPageSize();
		
		if (null!=current && null!=pagesize) {
			params.put("startNumber", (current-1)*pagesize);
			params.put("pageSize", daoSearch.getPageSize());
		}
		String organizationId=commonUtil.getOrganizationId();
		params.put("organizationId",organizationId );
		params.put("search", daoSearch.getSearch());
		List<String> productIds=dao.selectProductIdsByOrgId(organizationId);
		params.put("excludeProductIds",productIds);
		ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(codeManagerRestUrl+CommonConstants.RELATION_PRODUCT_URL, params, null);
		String body=responseEntity.getBody();
		logger.info("接收到码管理进行过码关联的产品信息："+body);
		
		JSONObject json=JSONObject.parseObject(body);
		return json;
	}

	public IntegralRuleProduct selectByProductIdAndOrgId(String productId, String organizationId) {
		return dao.selectByProductIdAndOrgId(productId, organizationId);
	}
    
    
}
