package com.jgw.supercodeplatform.marketing.service.es.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ScanCodeInfoMO;
import com.jgw.supercodeplatform.marketing.common.properties.IndexAndType;
import com.jgw.supercodeplatform.marketing.dto.SalerScanInfo;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.pojo.PieChartVo;
import com.jgw.supercodeplatform.marketing.vo.platform.ActivityOrganizationDataVo;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregator;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.common.util.SpringContextUtil;
import com.jgw.supercodeplatform.marketing.diagram.enums.QueryEnum;
import com.jgw.supercodeplatform.marketing.diagram.vo.DiagramRemebermeVo;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;

@Service
public class CodeEsService extends AbstractEsSearch {
	protected static Logger logger = LoggerFactory.getLogger(CodeEsService.class);


	@Autowired
	@Qualifier("elClient")
	private TransportClient eClient;
	public void addScanCodeRecord(String openId, String productId, String productBatchId, String codeId,
								  String codeType, Long activitySetId, Long scanCodeTime, String organizationId,Integer memberType,Long  userId) throws SuperCodeException {
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(codeId) || StringUtils.isBlank(codeType) || null== scanCodeTime
				|| null == activitySetId|| StringUtils.isBlank(organizationId) || null == memberType|| null == userId) {
			throw new SuperCodeException("新增扫码记录出错，有参数为空", 500);
		}

		logger.info("es保存 openId="+openId+",productId="+productId+",productBatchId="+productBatchId+",codeId="+codeId+",codeType="+codeType+",activitySetId="+activitySetId+",scanCodeTime="+scanCodeTime
				+",memberType="+memberType+",userId="+userId);
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId", productId);
		addParam.put("productBatchId", productBatchId);
		addParam.put("codeId", codeId);
		addParam.put("codeType", codeType);
		addParam.put("activitySetId", activitySetId);
		addParam.put("openId", openId);
		addParam.put("scanCodeTime", scanCodeTime);
		addParam.put("organizationId", organizationId);
		addParam.put("memberType", memberType);
		addParam.put("userId", userId);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKETING);
		eSearch.setType(EsType.INFO);
		add(eSearch,true, addParam);
	}
	/**
	 * 根据产品id和批次id查询参与扫码的批次一共被扫了多少次
	 *
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public long countByUserAndActivityQuantum(String openId, Long activitySetId, long nowTtimeStemp) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("openId.keyword", openId);
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
	public long countByCode(String codeId, String codeType,Integer memberType) {
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
	 * 根据码信息查询积分扫码批次
	 *
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public Long countByCodeForIntegral(String codeId, String codeType,Integer memberType) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		addParam.put("codeType.keyword", codeType);
		addParam.put("memberType", memberType);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.INTEGRAL);
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
		add(eSearch,true, addParam);

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
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 活动点击量
	 * @param organizationId
	 * @param startDate yyyy-MM-dd
	 * @param endDate yyyy-MM-dd
	 * @return
	 */
	public Integer countOrganizationActivityClickNumByDate(String organizationId, String startDate, String endDate) throws ParseException {
		// 聚合求和;效果同 select count from table where org = and date between a and b

		// out of date
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_SCAN_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=

		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(sdf.parse(startDate).getTime()).lt(sdf.parse(endDate).getTime());
		QueryBuilder queryBuilderOrg = QueryBuilders.termQuery("organizationId", organizationId);
		// 只获取会员活动点击量

		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		// 添加查询条件
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate).must(queryBuilderOrg);
		searchRequestBuilder.setQuery(boolQueryBuilder);
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
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termOrgIdQuery).must(termUserIdQuery);
		searchRequestBuilder.setQuery(boolQueryBuilder);
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
		if(QueryEnum.ALL.getStatus().indexOf(toEsVo.getChoose())==-1){
			throw new SuperCodeException("时间选择信息非法...");
		}
		String afterChoose = JSONObject.toJSONString(toEsVo);

		// 判断userId,orgId是否存在
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_DIAGRAM_REMBER.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termOrgIdQuery = new TermQueryBuilder("organizationId",toEsVo.getOrganizationId());
		QueryBuilder termUserIdQuery = new TermQueryBuilder("userId",toEsVo.getUserId());
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termOrgIdQuery).must(termUserIdQuery);
		searchRequestBuilder.setQuery(boolQueryBuilder);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		String id =null;
		SearchHit[] hit = hits.getHits();
		if(hit !=null && hit.length>0){
			id = hit[0].getId();
		}


		if(StringUtils.isBlank(id)){
			// 插入
			IndexResponse indexResponse = eClient.prepareIndex(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType())
					.setSource(afterChoose,XContentType.JSON).get();
			if(indexResponse.getVersion() != -1){
				return true;
			}
		}else {
			// 根据id判断插入存在则更新【需要index doc的id】
			IndexRequest indexRequest = new IndexRequest(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType(),id).source(afterChoose,XContentType.JSON);
			UpdateRequest updateRequest = new UpdateRequest(EsIndex.MARKET_DIAGRAM_REMBER.getIndex(), EsType.INFO.getType(),id).doc(afterChoose,XContentType.JSON).upsert(indexRequest);
			UpdateResponse updateResponse = eClient.update(updateRequest).get();

			if(updateResponse.getVersion()> 0 ){
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取导购员的所有扫码后的领奖记录
	 * @param memberId
	 * @param memberType
	 * @return
	 */
    public Integer searchScanInfoNum(Long memberId, Byte memberType) throws SuperCodeException{
    	if(memberId == null || memberId<=0){
    		throw new SuperCodeException("会员不存在...");
		}
    	if(memberType == null){
			throw new SuperCodeException("会员类型获取失败...");
		}
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_SCAN_INFO.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termOrgIdQuery = new TermQueryBuilder("userId",memberId);
		QueryBuilder termUserIdQuery = new TermQueryBuilder("memberType",memberType);
		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termOrgIdQuery).must(termUserIdQuery);
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(aggregation);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		Stats aggs = searchResponse.getAggregations().get(AggregationName);
		return  (int)aggs.getCount();

    }
    
    public int countCodeIdScanNum(String codeId, String codeTypeId) throws SuperCodeException {
    	if(codeId == null || codeId == null){
    		throw new SuperCodeException("码Id和码类型不能为空");
		}
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_SCAN_INFO.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termOrgIdQuery = new TermQueryBuilder("codeId",codeId);
		QueryBuilder termUserIdQuery = new TermQueryBuilder("codeTypeId",codeTypeId);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termOrgIdQuery).must(termUserIdQuery);
		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(aggregation);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		Stats aggs = searchResponse.getAggregations().get(AggregationName);
		return (int)aggs.getCount();
    }
    

    /**
     * 统计活动扫码量
     * 扫码信息,扫完就插入,插入失败不影响业务,统计数据：不是扫码成功的业务数据
     * @param sCodeInfoMO
     */
    public void indexScanInfo(ScanCodeInfoMO sCodeInfoMO) {
        try{
            // 保存用户产品信息
            eClient.prepareIndex(EsIndex.MARKET_SCAN_INFO.getIndex(), EsType.INFO.getType())
					.setSource(JSONObject.toJSONString(sCodeInfoMO), XContentType.JSON).get();
        }catch (Exception e){
            logger.debug("扫码信息插入失败");
            logger.debug(e.getMessage(), e);
        }
    }

	/**
	 * 查询某一个码被多少导购扫了
	 * @param codeId
	 * @param codeTypeId
	 * @return
	 */
	public int searchCodeScanedBySaler(String codeId, String codeTypeId) {
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_SALER_INFO.getIndex()).setTypes(EsType.INFO.getType());
		QueryBuilder termIdQuery = new TermQueryBuilder("codeId",codeId);
		QueryBuilder termTypeQuery = new TermQueryBuilder("codeTypeId",codeTypeId);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(termIdQuery).must(termTypeQuery);
		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(aggregation);
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		Stats aggs = searchResponse.getAggregations().get(AggregationName);
		return  (int)aggs.getCount();

	}



	/**
	 *
	 * @param organizationId
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SuperCodeException
	 */
	public int countSalerNumByUserIdAndDate(String organizationId, Long userId, long startDate ,long endDate) throws SuperCodeException {
		Map<String, Object> addParam = new HashMap<String, Object>();
		if (null ==userId) {
			throw new SuperCodeException("userID error");
		}
		if (StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("organizationId error");
		}
		// 聚合求和;效果同 select count from table where org = and date between a and b
		// out of date
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_SALER_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(startDate).lt(endDate);
		QueryBuilder queryBuilderOrg = QueryBuilders.termQuery("organizationId", organizationId);
		// 只获取会员活动点击量
		QueryBuilder memberType = QueryBuilders.termQuery("userId",userId);

		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("scanCodeTime");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate).must(queryBuilderOrg).must(memberType);
		searchRequestBuilder.setQuery(boolQueryBuilder);
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

	public void indexSalerScanInfo(SalerScanInfo param) throws SuperCodeException {
		try{
			// 保存导购信息
			eClient.prepareIndex(EsIndex.MARKET_SALER_INFO.getIndex(), EsType.INFO.getType())
					.setSource(JSONObject.toJSONString(param), XContentType.JSON).setRefreshPolicy(RefreshPolicy.IMMEDIATE).get();
		}catch (Exception e){
			logger.error("扫码信息插入失败");
			logger.error(e.getMessage(), e);
			throw new SuperCodeException("保存导购领奖的扫码信息失败...");
		}
	}

	/**
	 * 导购码是不是被扫过
	 * @param codeTypeId
	 * @param codeId
	 * @return
	 */
	public boolean searchCodeScaned(String codeTypeId, String codeId) {

		boolean scaned = true;
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		addParam.put("codeType.keyword", codeTypeId);

		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKET_SALER_INFO);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		// TODO 改成乐观锁查询
		List<SearchHit> searchHits = get(eSearch);
		if( searchHits.size() <= 0){
			scaned = false;
		}
		return scaned;

	}

	/**
	 * 添加全网运营红包扫码放弃抽奖的记录
	 * @param productId
	 * @param productBatchId
	 * @param codeId
	 * @param codeType
	 * @param activitySetId
	 * @param scanCodeTime
	 * @param organizationId
	 * @throws SuperCodeException
	 */
	public void addAbandonPlatformScanCodeRecord(String productId, String productBatchId, String codeId, Long activityId,
								  String codeType, Long activitySetId, Long scanCodeTime, String organizationId, String organizationFullName) {
		if (StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(codeId) || StringUtils.isBlank(codeType) || null== scanCodeTime
				|| null == activitySetId|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeExtException("新增扫码记录出错，有参数为空", 500);
		}

		logger.info("es保存productId="+productId+",productBatchId="+productBatchId+",codeId="+codeId+",codeType="+codeType+",activitySetId="+activitySetId+",scanCodeTime="+scanCodeTime);
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId", productId);
		addParam.put("productBatchId", productBatchId);
		addParam.put("codeId", codeId);
		addParam.put("codeType", codeType);
		addParam.put("activitySetId", activitySetId);
		addParam.put("activityId", activityId);
		//addParam.put("openId", openId);
		addParam.put("scanCodeTime", scanCodeTime);
		addParam.put("scanCodeDate", DateFormatUtils.format(scanCodeTime, "yyyy-MM-dd"));
		addParam.put("organizationId", organizationId);
		addParam.put("organizationFullName", organizationFullName);
		//addParam.put("memberType", memberType);
		//addParam.put("userId", userId);
		//0表示扫码后点击放弃抽奖状态，1表示点击了抽奖
		addParam.put("status", 0);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKET_PLATFORM_SCAN_INFO);
		eSearch.setType(EsType.INFO);
		add(eSearch,true, addParam);
	}

	/**
	 * 添加全网运营红包扫码抽奖的记录
	 * @param productId
	 * @param productBatchId
	 * @param codeId
	 * @param openId
	 * @param userId
	 * @param memberType
	 * @param codeType
	 * @param activitySetId
	 * @param scanCodeTime
	 * @param organizationId
	 * @throws SuperCodeException
	 */
	public void addPlatformScanCodeRecord(String productId, String productBatchId, String codeId,String openId,String userId, Integer memberType, Long activityId,
												 String codeType, Long activitySetId, Long scanCodeTime, String organizationId, String organizationFullName) throws SuperCodeException {
		if (StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId) || StringUtils.isBlank(openId) || StringUtils.isBlank(userId)
				|| StringUtils.isBlank(codeId) || StringUtils.isBlank(codeType) || null== scanCodeTime || memberType == null
				|| null == activitySetId|| StringUtils.isBlank(organizationId)) {
			throw new SuperCodeException("新增扫码记录出错，有参数为空", 500);
		}

		logger.info("es保存productId="+productId+",productBatchId="+productBatchId+",codeId="+codeId+",codeType="+codeType+",activitySetId="+activitySetId+",scanCodeTime="+scanCodeTime);
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("productId", productId);
		addParam.put("productBatchId", productBatchId);
		addParam.put("codeId", codeId);
		addParam.put("codeType", codeType);
		addParam.put("activitySetId", activitySetId);
		addParam.put("activityId", activityId);
		addParam.put("openId", openId);
		addParam.put("scanCodeTime", scanCodeTime);
		addParam.put("scanCodeDate", DateFormatUtils.format(scanCodeTime, "yyyy-MM-dd"));
		addParam.put("organizationId", organizationId);
		addParam.put("organizationFullName", organizationFullName);
		addParam.put("memberType", memberType);
		addParam.put("userId", userId);
		//0表示扫码后点击放弃抽奖状态，1表示点击了抽奖
		addParam.put("status", 1);
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKET_PLATFORM_SCAN_INFO);
		eSearch.setType(EsType.INFO);
		add(eSearch,true, addParam);
	}


	/**
	 * 查询全网运营平台红包该码被扫过次数
	 *
	 * @param productId
	 * @param productBatchId
	 * @return
	 */
	public long countPlatformScanCodeRecord(String codeId, Integer status) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		addParam.put("codeId.keyword", codeId);
		if (status != null) {
			addParam.put("status", status);
		}
		EsSearch eSearch = new EsSearch();
		eSearch.setIndex(EsIndex.MARKET_PLATFORM_SCAN_INFO);
		eSearch.setType(EsType.INFO);
		eSearch.setParam(addParam);
		return getCount(eSearch);
	}

	/**
	 * 统计指定时间段内码的数量
	 * @param timeStart
	 * @param timeEnd
	 * @param status
	 * @return
	 */
	public long countPlatformScanCodeRecordByTime(long timeStart, long timeEnd, Integer status) {
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_PLATFORM_SCAN_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(timeStart).lt(timeEnd);
		ValueCountAggregationBuilder aggregation =
				AggregationBuilders.count(AggregationName).field("codeId.keyword");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate);
		if (status != null) {
			QueryBuilder queryBuilderStatus = QueryBuilders.termQuery("status", status);
			boolQueryBuilder.must(queryBuilderStatus);
		}
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(aggregation);
		// 获取查询结果
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		// 获取count
		ValueCount aggs = searchResponse.getAggregations().get(AggregationName);
		// 优化方向，其他结果如非必须可剔除
		// 除去评分机制
		// 采用过滤而非查询提高查询速度
		// 取所需结果
		return aggs.getValue();
	}

	/**
	 * 获取扫码组织排行
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	public List<ActivityOrganizationDataVo> scanOrganizationList(long timeStart, long timeEnd){
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_PLATFORM_SCAN_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(timeStart).lt(timeEnd);
		Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,"doc['organizationId.keyword'].value+','+doc['organizationFullName.keyword'].value", new HashMap<>());
		TermsAggregationBuilder callTypeTeamAgg = AggregationBuilders.terms(AggregationName).script(script).order(BucketOrder.count(false)).size(7);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate);
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(callTypeTeamAgg);
		// 获取查询结果
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		// 获取count
		Aggregation team = searchResponse.getAggregations().get(AggregationName);
		if (team instanceof UnmappedTerms) {
			return new ArrayList<>();
		}
		StringTerms teamAgg = (StringTerms) team;
		List<StringTerms.Bucket> bucketList = teamAgg.getBuckets();
		List<ActivityOrganizationDataVo> idAndNameList = bucketList.stream().map(bucket -> {
			String[] idAndName = bucket.getKeyAsString().split(",");
			ActivityOrganizationDataVo activityOrganizationDataVo = new ActivityOrganizationDataVo();
			activityOrganizationDataVo.setOrganizationId(idAndName[0]);
			activityOrganizationDataVo.setOrganizationFullName(idAndName[1]);
			activityOrganizationDataVo.setActivityJoinNum(bucket.getDocCount());
			return activityOrganizationDataVo;
		}).collect(Collectors.toList());
		return idAndNameList;
	}

	/**
	 * 按天统计扫码量
	 * @param timeStart
	 * @param timeEnd
	 * @param status
	 * @return
	 */
	public List<PieChartVo> dayActivityStatistic(long timeStart, long timeEnd, Integer status){
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_PLATFORM_SCAN_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(timeStart).lt(timeEnd);
		TermsAggregationBuilder callTypeTeamAgg = AggregationBuilders.terms(AggregationName).field("scanCodeDate");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate);
		if (status != null) {
			QueryBuilder queryBuilderStatus = QueryBuilders.termQuery("status", status);
			boolQueryBuilder.must(queryBuilderStatus);
		}
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(callTypeTeamAgg).setSize(0);
		// 获取查询结果
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		// 获取count
		Aggregation team = searchResponse.getAggregations().get(AggregationName);
		if (team instanceof UnmappedTerms) {
			return new ArrayList<>();
		}
		LongTerms teamAgg = (LongTerms) team;
		List<LongTerms.Bucket> bucketList = teamAgg.getBuckets();
		List<PieChartVo> idAndNameList = bucketList.stream().map(bucket -> {
			PieChartVo pieChartVo = new PieChartVo();
			String name = bucket.getKeyAsString().substring(0,10);
			long value = bucket.getDocCount();
			pieChartVo.setName(name);
			pieChartVo.setValue(value);
			return pieChartVo;
		}).collect(Collectors.toList());
		return idAndNameList;
	}

	/**
	 * 统计指定时间段内扫码用户的数量
	 * @param timeStart
	 * @param timeEnd
	 * @param status
	 * @return
	 */
	public long countPlatformScanCodeUserByTime(long timeStart, long timeEnd, Integer status) {
		Map<String, Object> addParam = new HashMap<String, Object>();
		SearchRequestBuilder searchRequestBuilder = eClient.prepareSearch(EsIndex.MARKET_PLATFORM_SCAN_INFO.getIndex()).setTypes( EsType.INFO.getType());
		// 创建查询条件 >= <=
		QueryBuilder queryBuilderDate = QueryBuilders.rangeQuery("scanCodeTime").gte(timeStart).lt(timeEnd);
		StatsAggregationBuilder aggregation =
				AggregationBuilders
						.stats(AggregationName)
						// 聚和字段：码
						.field("userId");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(queryBuilderDate);
		if (status != null) {
			QueryBuilder queryBuilderStatus = QueryBuilders.termQuery("status", status);
			boolQueryBuilder.must(queryBuilderStatus);
		}
		searchRequestBuilder.setQuery(boolQueryBuilder);
		searchRequestBuilder.addAggregation(aggregation);
		// 获取查询结果
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		// 获取count
		Stats aggs = searchResponse.getAggregations().get(AggregationName);
		// 优化方向，其他结果如非必须可剔除
		// 除去评分机制
		// 采用过滤而非查询提高查询速度
		// 取所需结果
		return aggs.getCount();
	}

}
