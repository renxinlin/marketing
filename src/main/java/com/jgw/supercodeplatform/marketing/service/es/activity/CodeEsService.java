package com.jgw.supercodeplatform.marketing.service.es.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgw.supercodeplatform.marketing.diagram.vo.DiagramRemebermeVo;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class CodeEsService extends AbstractEsSearch {
	protected static Logger logger = LoggerFactory.getLogger(CodeEsService.class);


	@Autowired
	@Qualifier("elClient")
	private TransportClient eClient;

	public void addScanCodeRecord(String userId, String productId, String productBatchId, String codeId,
			String codeType, Long activitySetId, Long scanCodeTime, String organizationId,Integer memberType) throws SuperCodeException {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(codeId) || StringUtils.isBlank(codeType) || null== scanCodeTime
				|| null == activitySetId|| StringUtils.isBlank(organizationId) || null == memberType) {
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
		addParam.put("organizationId", organizationId);
		addParam.put("memberType", memberType);
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
	public Long countByCode(String codeId, String codeType,Integer memberType) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		addParam.put("codeType.keyword", codeType);
		addParam.put("memberType", memberType);
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
	public List<SearchHit> selectScanCodeRecord(String codeId, String codeType,Integer memberType) {
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
	 * @param startDate yyyy-MM-dd
	 * @param endDate yyyy-MM-dd
	 * @return
	 */
	public Integer countOrganizationActivityClickNumByDate(String organizationId, String startDate, String endDate) {
		// 聚合求和;效果同 select count from table where org = and date between a and b

		// out of date
		TransportClient eClient = SpringContextUtil.getBean("elClient");
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKETING.getIndex()).setTypes( EsType.INFO.getType());
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
        // 优化方向，其他结果如非必须可剔除
        // 除去评分机制
        // 采用过滤而非查询提高查询速度
		// 取所需结果
		return  (int)aggs.getCount();
	}

	/**
	 * 查询用户的图表时间选择维度
	 * @param toEsVo
	 * @return
	 */
    public DiagramRemebermeVo searchDiagramRemberMeInfo(DiagramRemebermeVo toEsVo) throws SuperCodeException{
		if(StringUtils.isBlank(toEsVo.getOrganizationId())){
			throw new SuperCodeException("组织信息获取失败...");
		}
		if(StringUtils.isBlank(toEsVo.getUserId())){
			throw new SuperCodeException("用户信息获取失败...");

		}
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_DIAGRAM_REMBER.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termOrgIdQuery = new TermQueryBuilder("organizationId",toEsVo.getOrganizationId());
		QueryBuilder termUserIdQuery = new TermQueryBuilder("userId",toEsVo.getUserId());
		searchRequestBuilder.setQuery(termOrgIdQuery).setQuery(termUserIdQuery);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		SearchHit[] hits = searchResponse.getHits().getHits();
		if(hits!=null && hits.length>0){
			SearchHit hit = hits[0];
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			Map<String, DocumentField> fields = hit.getFields();
			DiagramRemebermeVo toWebVo = new DiagramRemebermeVo((String) sourceAsMap.get("organizationId")
					,(String) sourceAsMap.get("userId"),(String) sourceAsMap.get("choose"));
			return toWebVo;
		}else {
			return toEsVo;
		}

	}
	/**
	 * 记住用户的图表时间选择维度
	 * @param toEsVo
	 * @return
	 */
	public boolean indexDiagramRemberMeInfo(DiagramRemebermeVo toEsVo) throws SuperCodeException, IOException, ExecutionException, InterruptedException {
		if(StringUtils.isBlank(toEsVo.getOrganizationId())){
			throw new SuperCodeException("组织信息获取失败...");
		}
		if(StringUtils.isBlank(toEsVo.getUserId())){
			throw new SuperCodeException("用户信息获取失败...");

		}
		if(StringUtils.isBlank(toEsVo.getChoose())){
			throw new SuperCodeException("时间选择信息获取失败...");
		}
		if("123456".indexOf(toEsVo.getChoose())==-1){
			throw new SuperCodeException("时间选择信息非法...");
		}
 		String afterChoose = JSONObject.toJSONString(toEsVo);


		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_DIAGRAM_REMBER.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termOrgIdQuery = new TermQueryBuilder("organizationId",toEsVo.getOrganizationId());
		QueryBuilder termUserIdQuery = new TermQueryBuilder("userId",toEsVo.getUserId());
		searchRequestBuilder.setQuery(termOrgIdQuery).setQuery(termUserIdQuery);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		String id =null;
		SearchHit[] hit = hits.getHits();
		if(hit !=null && hit.length>0){
			id = hit[0].getId();
		}






		if(StringUtils.isBlank(id)){
			IndexResponse indexResponse = eClient.prepareIndex(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType())
					.setSource(afterChoose,XContentType.JSON).get();
			if(indexResponse.getVersion() != -1){
				return true;
			}
		}else {
			IndexRequest indexRequest = new IndexRequest(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType(),id).source(afterChoose,XContentType.JSON);
			UpdateRequest updateRequest = new UpdateRequest(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType(),id).doc(afterChoose,XContentType.JSON).upsert(indexRequest);
			UpdateResponse updateResponse = eClient.update(updateRequest).get();

			if(updateResponse.getVersion()> 0 ){
				return true;
			}
		}
		return false;
	}
}
