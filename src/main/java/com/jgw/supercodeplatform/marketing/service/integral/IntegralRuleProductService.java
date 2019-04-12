package com.jgw.supercodeplatform.marketing.service.integral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleMapperExt;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleProductMapperExt;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam;
import com.jgw.supercodeplatform.marketing.dto.integral.BatchSetProductRuleParam.Product;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRule;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;

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
    
    @Value("${rest.codemanager.url}")
    private String codeManagerRestUrl;
    
	@Override
	protected List<IntegralRuleProduct> searchResult(DaoSearch searchParams) throws Exception {
		String organizationId=commonUtil.getOrganizationId();
		List<IntegralRuleProduct> list=dao.list(searchParams,organizationId);
		return super.searchResult(searchParams);
	}

	@Override
	protected int count(DaoSearch searchParams) throws Exception {
		return dao.count(searchParams);
	}

	public void deleteByProductIds(List<String> productIds) throws SuperCodeException {
      if (null==productIds || productIds.isEmpty()) {
	 	throw new SuperCodeException("产品id不能为空", 500);
	  }	
      dao.deleteByProductIds(productIds);      
	}

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
		
		List<IntegralRuleProduct> ruleProducts=new ArrayList<IntegralRuleProduct>();
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
	}

	public void singleSetRuleProduct( IntegralRuleProduct inRuleProduct) throws SuperCodeException {
		if (null==inRuleProduct.getId()) {
			String organizationId=commonUtil.getOrganizationId();
			IntegralRuleProduct eruleProduct=dao.selectByProductIdAndOrgId(inRuleProduct.getProductId(),organizationId);
			if (null!=eruleProduct) {
				throw new SuperCodeException("已存在该产品规则请带上主键id", 500);
			}
			dao.insertSelective(inRuleProduct);
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
