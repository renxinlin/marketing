package com.jgw.supercodeplatform.marketingsaler.outservicegroup.rest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.CommonConstants;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dto.integral.Product;
import com.jgw.supercodeplatform.marketing.service.common.CommonService;
import com.jgw.supercodeplatform.marketingsaler.integral.interfaces.dto.BatchSalerRuleRewardDto;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleReward;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微服务层 供service调用
 */
@Component
@Slf4j
public class SalerRuleRewardRestInterface {
    @Autowired
    private CommonService commonService; // 反调用设计

    @Value("${marketing.domain.url}")
    private String marketingDomain;

    @Value("${rest.user.url}")
    private String restUserUrl;
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    /**
     * 构建请求生码批次参数 copy会员代码
     * @param
     * @return
     */
    public void integralUrlBindBatch(int businessType, String superToken, List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs)
            throws SuperCodeException {
        if (null!=productAndBatchGetCodeMOs && !productAndBatchGetCodeMOs.isEmpty()) {
            String batchInfoBody=commonService.getBatchInfo(productAndBatchGetCodeMOs, superToken, WechatConstants.CODEMANAGER_GET_BATCH_CODE_INFO_URL_WITH_ALL_RELATIONTYPE);
            JSONObject obj=JSONObject.parseObject(batchInfoBody);
            int batchInfostate=obj.getInteger("state");
            if (200!=batchInfostate) {
                throw new SuperCodeException("积分设置时根据产品及批次获取码管理生码批次失败："+batchInfoBody, 500);
            }
            JSONArray batchArray=obj.getJSONArray("results");
            if (null==batchArray || batchArray.isEmpty()) {
                throw new SuperCodeException("该产品的批次未查到码关联信息，前检查是否已做过码关联的批次被删除", 500);
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


    /**
     * 更新产品营销信息,请求基础数据 copy会员代码
     * @param
     * @param superToken
     * @throws SuperCodeException
     */
    public void updateBaseProductPriceBatch(List<Product> products, BatchSalerRuleRewardDto bProductRuleParam, String superToken) throws SuperCodeException {
        if(!CollectionUtils.isEmpty(bProductRuleParam.getProducts()) && bProductRuleParam.getProductPrice() != null ){
            List<Map<String, Object>> updateProductList = new ArrayList<>();
            products.forEach(product -> {
                Map<String, Object> updateProductMap=new HashMap<String, Object>();
                updateProductMap.put("price", bProductRuleParam.getProductPrice());
                updateProductMap.put("productId", product.getProductId());
                updateProductList.add(updateProductMap);
            });
            if (null!=updateProductList && !updateProductList.isEmpty()) {
                String json=JSONObject.toJSONString(updateProductList);
                Map<String,String> headerMap=new HashMap<>();
                headerMap.put("super-token", superToken);
                ResponseEntity<String> resopEntity=restTemplateUtil.putJsonDataAndReturnJosn(restUserUrl+ CommonConstants.USER_BATCH_UPDATE_PRODUCT_MARKETING_INFO, json, headerMap);
                log.info("更新基础平台营销数据返回信息："+resopEntity.toString());
                String body=resopEntity.getBody();
                JSONObject bodyJosn=JSONObject.parseObject(body);
                Integer state=bodyJosn.getInteger("state");
                if (null==state || state.intValue()!=200) {
                    throw new SuperCodeException("请求基础平台批量更新产品营销信息出错："+bodyJosn.getString("msg"), 500);
                }
            }
        }

    }

    public void updateBaseProductPrice(SalerRuleReward inRuleProduct, String superToken) throws SuperCodeException {
        if (null!=inRuleProduct && inRuleProduct.getProductId() != null  && inRuleProduct.getProductPrice() != null) {
            List<Map<String, Object>> updateProductList = new ArrayList<>();
            Map<String, Object> updateProductMap=new HashMap<String, Object>();
            updateProductMap.put("price", inRuleProduct.getProductPrice());
            updateProductMap.put("productId", inRuleProduct.getProductId());
            updateProductList.add(updateProductMap);
            String json=JSONObject.toJSONString(updateProductList);
            Map<String,String> headerMap=new HashMap<>();
            headerMap.put("super-token", superToken);
            ResponseEntity<String> resopEntity=restTemplateUtil.putJsonDataAndReturnJosn(restUserUrl+ CommonConstants.USER_BATCH_UPDATE_PRODUCT_MARKETING_INFO, json, headerMap);
            log.info("更新基础平台营销数据返回信息："+resopEntity.toString());
            String body=resopEntity.getBody();
            JSONObject bodyJosn=JSONObject.parseObject(body);
            Integer state=bodyJosn.getInteger("state");
            if (null==state || state.intValue()!=200) {
                throw new SuperCodeException("请求基础平台批量更新产品营销信息出错："+bodyJosn.getString("msg"), 500);
            }
        }
    }
}
