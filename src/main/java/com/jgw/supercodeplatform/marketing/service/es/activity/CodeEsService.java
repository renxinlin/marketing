package com.jgw.supercodeplatform.marketing.service.es.activity;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			String organizationId, Date parse) throws SuperCodeException {
		if (null==userId  || StringUtils.isBlank(productId) || StringUtils.isBlank(productBatchId)
				|| StringUtils.isBlank(outerCodeId) || StringUtils.isBlank(codeTypeId) || StringUtils.isBlank(organizationId)|| null== parse) {
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
		addParam.put("scanCodeTime", parse);

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
}
