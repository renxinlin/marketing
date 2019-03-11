package com.jgw.supercodeplatform.marketing.service.es.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;

@Service
public class CodeEsService extends AbstractEsSearch{
	
  public void addScanCodeRecord(String userId,String productId,String productBatchId,String codeId,String codeType,Long activitySetId,String scanCodeTime) throws SuperCodeException {
	  if (StringUtils.isBlank(userId) ||StringUtils.isBlank(productId) ||StringUtils.isBlank(productBatchId) 
			  ||StringUtils.isBlank(codeId) ||StringUtils.isBlank(codeType)||StringUtils.isBlank(scanCodeTime) ||null==activitySetId) {
		throw new SuperCodeException("新增扫码记录出错，有参数为空", 500);
	  }
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("productId", productId);
	  addParam.put("productBatchId", productBatchId);
	  addParam.put("codeId", codeId);
	  addParam.put("codeType", codeType);
	  addParam.put("activitySetId", activitySetId);
	  addParam.put("userId", userId);
	  addParam.put("scanCodeTime", scanCodeTime);
	  
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
      add(eSearch, addParam);	
  }
  
  /**
   * 根据产品id和批次id查询参与扫码的批次一共被扫了多少次
   * @param productId
   * @param productBatchId
   * @return
   */
  public Long countByUserAndActivityQuantum(String userId,Long activitySetId,String scanCodeTime) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("userId.keyword", userId);
	  addParam.put("scanCodeTime.keyword", scanCodeTime);
	  addParam.put("activitySetId", activitySetId);
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
	  eSearch.setParam(addParam);
      return getCount(eSearch);	
  }
  /**
   * 根据产品id和批次id查询参与扫码的批次一共被扫了多少次
   * @param productId
   * @param productBatchId
   * @return
   */
  public Long countByBatch(String productId,String productBatchId) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("productId.keyword", productId);
	  addParam.put("productBatchId.keyword", productBatchId);
	  
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
	  eSearch.setParam(addParam);
      return getCount(eSearch);	
  }
  
  /**
   * 根据码信息查询参与扫码的批次一共被扫了多少次
   * @param productId
   * @param productBatchId
   * @return
   */
  public Long countByCode(String codeId,String codeType) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("codeId.keyword", codeId);
	  addParam.put("codeType.keyword", codeType);
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
	  eSearch.setParam(addParam);
      return getCount(eSearch);	
  }
  
  /**
   * 通过码和码制查询该码是不是被扫过
   * @param codeId
   * @param codeType
   * @return
   */
  public List<SearchHit> selectScanCodeRecord(String codeId,String codeType) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("codeId.keyword", codeId);
	  addParam.put("codeType.keyword", codeType);
	  
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
	  eSearch.setParam(addParam);
	  
      return get(eSearch);
  }
}
