package com.jgw.supercodeplatform.marketing.service.activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MarketingActivityProductService {
     @Autowired
    private MarketingActivityProductMapper mapper;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Autowired
    private CommonUtil commonUtil;

    @Value("${rest.user.url}")
    private String restUserUrl;

	@Value("${rest.codemanager.url}")
	private String codeManagerUrl;
	
    public RestResult<HashSet<MarketingActivityProductParam>> getActivityProductInfoByeditPage(Long activitySetId) {
        RestResult restResult = new RestResult();
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            restResult.setState(500);
            restResult.setMsg("活动id校验失败");
            return  restResult;
        }
        // 获取中奖规则-奖次信息
        List<MarketingActivityProduct> marketingActivityProducts = mapper.selectByActivitySetId(activitySetId);

        // 产品批次转换成网页格式数据转换1==产品去重
        Map<String, MarketingActivityProductParam> transferDatas = new HashMap<>();
        for (MarketingActivityProduct marketingActivityProduct : marketingActivityProducts) {
            // 数据转换 产品去重
            MarketingActivityProductParam transferData = new MarketingActivityProductParam();
            transferData.setProductId(marketingActivityProduct.getProductId());
            transferData.setProductName(marketingActivityProduct.getProductName());
            // 产品信息集合
            transferDatas.put(marketingActivityProduct.getProductId(), transferData);
        }

        // 产品批次转换成网页格式数据转换2==产品关联相关批次
        for (MarketingActivityProductParam transferData : transferDatas.values()) {
            // 产品批次对象
            List<ProductBatchParam> productParams = new ArrayList<ProductBatchParam>() ;
            for(MarketingActivityProduct marketingActivityProduct : marketingActivityProducts){
                // 校验该批次是否属于该产品
                if(transferData.getProductId().equals(marketingActivityProduct.getProductId())){
                    if (StringUtils.isNotBlank(marketingActivityProduct.getProductBatchId())) {
                        ProductBatchParam batch = new ProductBatchParam();
                        batch.setProductBatchId(marketingActivityProduct.getProductBatchId());
                        batch.setProductBatchName(marketingActivityProduct.getProductBatchName());
                        productParams.add(batch);
                    }
                }
            }
            transferData.setProductBatchParams(productParams);
        }

        // 返回
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(transferDatas.values());
        return  restResult;
    }

	public JSONObject relationActProds(String search, Integer pageSize, Integer current) throws SuperCodeException {
		String organizationId=commonUtil.getOrganizationId();
		List<String> productBatchIds=mapper.usedProductBatchIds(organizationId);
		Map<String, Object>params=new HashMap<String, Object>();
//		if (null!=productBatchIds && !productBatchIds.isEmpty()) {
//			StringBuffer buf=new StringBuffer();
//			for (String productBatchId : productBatchIds) {
//				buf.append(productBatchId).append(",");
//			}
//			params.put("productBatchIds",buf.substring(0, buf.length()-1));
//		}
		params.put("organizationId",organizationId );
		params.put("relationType",3 );
		params.put("search",search);
		params.put("pageSize",pageSize);
        params.put("current",current);
        params.put("isRelations","0,2");
		Map<String, String> headerMap=new HashMap<>();
		headerMap.put(commonUtil.getSysAuthHeaderKey(), commonUtil.getSecretKeyForCodeManager());
        HashMap<String, String> superToken = new HashMap<>();
        superToken.put("super-token",commonUtil.getSuperToken());

		ResponseEntity<String> responseEntity=restTemplateUtil.getRequestAndReturnJosn(codeManagerUrl+CommonConstants.CODEMANAGER_RELATION_PRODUCT_PRODUCT_BATCH, params, superToken);
		log.info("获取码管理做过码关联的产品及批次信息："+responseEntity.toString());
		String body=responseEntity.getBody();
		JSONObject json=JSONObject.parseObject(body);
		if (null==json.getString("results")) {
		    Map<String, Object> liMap = new HashMap<>();
            liMap.put("pagination", new HashMap<>());
            liMap.put("list", new ArrayList<>());
			json.put("results", liMap);
		} else {
            JSONObject resJson = json.getJSONObject("results");
            JSONArray listJson = resJson.getJSONArray("list");
            listJson.forEach(obj -> {
                Map prodMap = (Map)obj;
                List<Object> prodBatchs = (List)prodMap.get("productBatchs");
                if (prodBatchs != null) {
                    List<Object> filPordBatchList =  prodBatchs.stream().filter(prodObj -> {
                        Map prodBatchMap = (Map)prodObj;
                        if (prodBatchMap == null) {
                            return false;
                        }
                        if (prodBatchMap.get("productBatchId") == null || prodBatchMap.get("productBatchName") == null) {
                            return false;
                        }
                        return true;
                    }).collect(Collectors.toList());
                    prodMap.put("productBatchs", filPordBatchList);
                }
            });
            resJson.put("list", listJson);
            json.put("results", resJson);
        }
		return json;
	}

    public JSONObject relationCustomers(String search, Integer pageSize, Integer current) throws SuperCodeException {
        Map<String, Object>params=new HashMap<String, Object>();
        params.put("search",search);
        params.put("pageSize",pageSize);
        params.put("current",current);
        Map<String, String> headerMap=new HashMap<>();
        headerMap.put(commonUtil.getSysAuthHeaderKey(), commonUtil.getSecretKeyForCodeManager());
        HashMap<String, String> superToken = new HashMap<>();
        superToken.put("super-token",commonUtil.getSuperToken());

        ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(restUserUrl+CommonConstants.CUSTOMER_ENABLE_PAGE_LIST, params, superToken);
        log.info("获取所有渠道信息："+responseEntity.toString());
        String body=responseEntity.getBody();
        JSONObject json=JSONObject.parseObject(body);
        if (null==json.getString("results")) {
            Map<String, Object> liMap = new HashMap<>();
            liMap.put("pagination", new HashMap<>());
            liMap.put("list", new ArrayList<>());
            json.put("results", liMap);
        } else {
            JSONObject resJson = json.getJSONObject("results");
            JSONArray listJson = resJson.getJSONArray("list");
            if (listJson != null) {
                List mkList = listJson.stream().filter(obj -> {
                    Map prodMap = (Map)obj;
                    Integer mkwarehousing = (Integer) prodMap.get("mkwarehousing");
                    if (mkwarehousing != null && mkwarehousing.intValue() == 1) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                resJson.put("list", mkList);
                json.put("results", resJson);
            }
        }
        return json;
    }


    public JSONObject relationChildrenCustomers(String customerSuperior, String search, Integer pageSize, Integer current) throws SuperCodeException {
        Map<String, Object>params=new HashMap<String, Object>();
        params.put("customerSuperior",customerSuperior);
        params.put("search",search);
        params.put("pageSize",pageSize);
        params.put("current",current);
        Map<String, String> headerMap=new HashMap<>();
        headerMap.put(commonUtil.getSysAuthHeaderKey(), commonUtil.getSecretKeyForCodeManager());
        HashMap<String, String> superToken = new HashMap<>();
        superToken.put("super-token",commonUtil.getSuperToken());

        ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(restUserUrl+CommonConstants.CUSTOMER_ENABLE_CHILD_PAGE_LIST, params, superToken);
        log.info("获取下级渠道信息："+responseEntity.toString());
        String body=responseEntity.getBody();
        JSONObject json=JSONObject.parseObject(body);
        if (null==json.getString("results")) {
            Map<String, Object> liMap = new HashMap<>();
            liMap.put("pagination", new HashMap<>());
            liMap.put("list", new ArrayList<>());
            json.put("results", liMap);
        } else {
            JSONObject resJson = json.getJSONObject("results");
            JSONArray listJson = resJson.getJSONArray("list");
            if (listJson != null) {
                List mkList = listJson.stream().filter(obj -> {
                    Map prodMap = (Map)obj;
                    Integer mkwarehousing = (Integer) prodMap.get("mkwarehousing");
                    if (mkwarehousing != null && mkwarehousing.intValue() == 1) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
                resJson.put("list", mkList);
                json.put("results", resJson);
            }
        }
        return json;
    }


}
