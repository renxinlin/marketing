package com.jgw.supercodeplatform.marketing.service.es.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.stats.SearchStats;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;

@Service
public class CodeEsService extends AbstractEsSearch {
	protected static Logger logger = LoggerFactory.getLogger(CodeEsService.class);
	public void addScanCodeRecord(String userId, String productId, String productBatchId, String codeId,
			String codeType, Long activitySetId, Long scanCodeTime) throws SuperCodeException {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(codeId) || StringUtils.isBlank(codeType) || null== scanCodeTime
				|| null == activitySetId) {
			throw new SuperCodeException("新增扫码记录出错，有参数为空", 500);
		}
		
		logger.info("es保存 userId="+userId+",productId="+productId+",productBatchId="+productBatchId+",codeId="+codeId+",codeType="+codeType+",activitySetId="+activitySetId+",scanCodeTime="+scanCodeTime);
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId", productId);
		addParam.put("productBatchId", productBatchId);
		addParam.put("codeId", codeId);
		addParam.put("codeType", codeType);
		addParam.put("activitySetId", activitySetId);
		addParam.put("userId", userId);
		addParam.put("scanCodeTime", scanCodeTime);

		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		add(eSearch, addParam);
	}
	/**
	 * 根据产品id和批次id查询参与扫码的批次一共被扫了多少次
	 * 
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public Long countByUserAndActivityQuantum(String userId, Long activitySetId, long nowTtimeStemp) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("userId.keyword", userId);
		addParam.put("scanCodeTime", nowTtimeStemp);
		addParam.put("activitySetId", activitySetId);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}

	/**
	 * 根据产品id和批次id查询参与扫码的批次一共被扫了多少次
	 * 
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public Long countByBatch(String productId, String productBatchId) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId.keyword", productId);
		addParam.put("productBatchId.keyword", productBatchId);

		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}

	/**
	 * 根据码信息查询参与扫码的批次一共被扫了多少次
	 * 
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public Long countByCode(String codeId, String codeType) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		addParam.put("codeType.keyword", codeType);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}

	/**
	 * 通过码和码制查询该码是不是被扫过
	 * 
	 * @param codeId
	 * @param codeType
	 * @return
	 */
	public List<SearchHit> selectScanCodeRecord(String codeId, String codeType) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		addParam.put("codeType.keyword", codeType);

		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);

		return get(eSearch);
	}

	public Long countByActivitySetId(Long activitySetId) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("activitySetId", activitySetId);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}


	// 兑换数量插入
	public void  putExchangeCount(String key, Integer count){
		// TODO 待实现
		// 刷入写数据
	}
	// 兑换数量统计
	public Long getExchangeCount(String key){
		return 0L;
	}
	
	
	/**
	 * 添加积分领取记录
	 * @param userId
	 * @param outerCodeId
	 * @param codeTypeId
	 * @param productId
	 * @param productBatchId
	 * @param organizationId
	 * @param parse
	 * @throws SuperCodeException
	 */
	public void addCodeIntegral(Long userId, String outerCodeId, String codeTypeId, String productId, String productBatchId,
			String organizationId, Long scanCodeTime) throws SuperCodeException {
		if (null==userId  || StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(outerCodeId) || StringUtils.isBlank(codeTypeId) || StringUtils.isBlank(organizationId)|| null== scanCodeTime) {
			throw new SuperCodeException("新增扫码记录出错，有参数为空", 500);
		}
		
		logger.info("es保存 userId="+userId+",productId="+productId+",productBatchId="+productBatchId+",outerCodeId="+outerCodeId+",codeTypeId="+codeTypeId+",organizationId="+organizationId);
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId", productId);
		addParam.put("productBatchId", productBatchId);
		addParam.put("outerCodeId", outerCodeId);
		addParam.put("codeTypeId", codeTypeId);
		addParam.put("organizationId", organizationId);
		addParam.put("userId", userId);
		addParam.put("scanCodeTime", scanCodeTime);

		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.INTEGRAL);
		eSearch.setType(EsType.INFO);
		add(eSearch, addParam);
		
	}
	
	/**
	 * 根据码和码制查询当前码的积分有没有被领取
	 * @param outerCodeId
	 * @param codeTypeId
	 * @return
	 */
	public Long countCodeIntegral(String outerCodeId, String codeTypeId) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("outerCodeId.keyword", outerCodeId);
		addParam.put("codeTypeId.keyword", codeTypeId);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.INTEGRAL);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}
	
    /**
     * 
     * @param userId
     * @param scanCodeTime
     * @param organizationId 
     * @return
     */
	public Long countIntegralByUserIdAndDate(Long userId, Long scanCodeTime, String organizationId) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		if (null!=userId) {
			addParam.put("userId", userId);
		}
		if (null!=scanCodeTime) {
			addParam.put("scanCodeTime", scanCodeTime);
		}
		if (StringUtils.isNotBlank(organizationId)) {
			addParam.put("organizationId.keyword", organizationId);
		}
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.INTEGRAL);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}


	/**
	 * 活动点击量聚合名称
	 */
	private static final String AggregationName="agg";
	/**
	 * 活动点击量
	 * @param organizationId
	 * @param date
	 * @param date1
	 * @return
	 */
	public Integer countOrganizationActivityClickNumByDate(String organizationId, String startDate, String endDate) {
		// 聚合求和;效果同 select count from table where org = and date between a and b

		// out of date
		TransportClient eClient = SpringContextUtil.getBean("elClient");
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKETING.getIndex(), EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(startDate).lte(endDate);
		QueryBuilder queryBuilderOrg = QueryBuilders.termQuery("organizationId", organizationId);
		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		// 添加查询条件
		searchRequestBuilder.setQuery(queryBuilderOrg).setQuery(queryBuilderDate);
		searchRequestBuilder.addAggregation(aggregation);
 		// 获取查询结果
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		// 获取count
		Stats aggs = searchResponse.getAggregations().get(AggregationName);
		return  (int)aggs.getCount();
	}


}
