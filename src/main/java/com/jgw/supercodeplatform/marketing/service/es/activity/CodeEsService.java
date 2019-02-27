package com.jgw.supercodeplatform.marketing.service.es.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import com.jgw.supercodeplatform.marketing.common.model.es.EsSearch;
import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import com.jgw.supercodeplatform.marketing.service.es.AbstractEsSearch;

@Service
public class CodeEsService extends AbstractEsSearch{
	
  public void addScanCodeRecord(String productId,String productBatchId,String codeId,String codeType,Long activityId) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("productId", productId);
	  addParam.put("productBatchId", productBatchId);
	  addParam.put("codeId", codeId);
	  addParam.put("codeType", codeType);
	  addParam.put("activityId", activityId);
	  
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
  public Long countByBatch(String productId,String productBatchId) {
	  Map<String, Object> addParam=new HashMap<String, Object>();
	  addParam.put("productId", productId);
	  addParam.put("productBatchId", productBatchId);
	  
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
	  addParam.put("codeId", codeId);
	  addParam.put("codeType", codeType);
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
	  addParam.put("codeId", codeId);
	  addParam.put("codeType", codeType);
	  
	  EsSearch eSearch=new EsSearch();
	  eSearch.setIndex(EsIndex.MARKETING);
	  eSearch.setType(EsType.INFO);
	  eSearch.setParam(addParam);
	  
      return get(eSearch);
  }
}
