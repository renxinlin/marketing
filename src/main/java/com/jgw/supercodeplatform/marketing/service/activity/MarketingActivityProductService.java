package com.jgw.supercodeplatform.marketing.service.activity;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarketingActivityProductService {
	protected static Logger logger = LoggerFactory.getLogger(MarketingActivityProductService.class);
    @Autowired
    private MarketingActivityProductMapper mapper;

    @Autowired
    private RestTemplateUtil restTemplateUtil;
    
    @Autowired
    private CommonUtil commonUtil;
    
    
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
        Set<MarketingActivityProductParam> transferDatas = new HashSet<MarketingActivityProductParam>();
        for (MarketingActivityProduct marketingActivityProduct : marketingActivityProducts) {
            // 数据转换 产品去重
            MarketingActivityProductParam transferData = new MarketingActivityProductParam();
            transferData.setProductId(marketingActivityProduct.getProductId());
            transferData.setProductName(marketingActivityProduct.getProductName());
            // 产品信息集合
            transferDatas.add(transferData);
        }

        // 产品批次转换成网页格式数据转换2==产品关联相关批次
        for (MarketingActivityProductParam transferData : transferDatas) {
            // 产品批次对象
            List<ProductBatchParam> productParams = new ArrayList<ProductBatchParam>() ;
            for(MarketingActivityProduct marketingActivityProduct : marketingActivityProducts){
                // 校验该批次是否属于该产品
                if(transferData.getProductId().equals(marketingActivityProduct.getProductId())){
                    ProductBatchParam batch = new ProductBatchParam();
                    batch.setProductBatchId(marketingActivityProduct.getProductBatchId());
                    batch.setProductBatchName(marketingActivityProduct.getProductBatchName());
                    productParams.add(batch);
                }
            }
            transferData.setProductBatchParams(productParams);
        }

        // 返回
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(transferDatas);
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
		Map<String, String> headerMap=new HashMap<>();
		headerMap.put(commonUtil.getSysAuthHeaderKey(), commonUtil.getSecretKeyForCodeManager());
		ResponseEntity<String>responseEntity=restTemplateUtil.getRequestAndReturnJosn(codeManagerUrl+CommonConstants.CODEMANAGER_RELATION_PRODUCT_PRODUCT_BATCH, params, null);
		logger.info("获取码管理做过码关联的产品及批次信息："+responseEntity.toString());
		String body=responseEntity.getBody();
		JSONObject json=JSONObject.parseObject(body);
		if (null==json.getString("results")) {
			json.put("results", new ArrayList<>());
		}
		return json;
	}
}
