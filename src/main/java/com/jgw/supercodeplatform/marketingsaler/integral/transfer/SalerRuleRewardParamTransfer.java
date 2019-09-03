package com.jgw.supercodeplatform.marketingsaler.integral.transfer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import com.jgw.supercodeplatform.marketingsaler.integral.pojo.SalerRuleReward;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分奖励
 */
public class SalerRuleRewardParamTransfer extends CommonTransfer{
    /**
     * 分页查询的page对象
     */
    public static IPage<SalerRuleReward> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<SalerRuleReward> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;
    }

    /**
     * 分页查询的条件对象
     * @param daoSearch
     * @return
     */
    public static Wrapper<SalerRuleReward> getPageParam(DaoSearch daoSearch,String organizationId) {
        QueryWrapper<SalerRuleReward> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OrganizationId",organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query ->query.like("",daoSearch.getSearch())
                    .or().like("ProductName",daoSearch.getSearch())
                    .or().like("ProductPrice",daoSearch.getSearch())
                    .or().like("RewardIntegral",daoSearch.getSearch())
            );
        }
        return queryWrapper;
    }

    /**
     * 将mybatis-plus的查询结果转换成VO对象
     * @param salerRuleRewardIPage
     * @return
     */
    public static AbstractPageService.PageResults<List<SalerRuleReward>> toPageResult(IPage<SalerRuleReward> salerRuleRewardIPage) {
        List<SalerRuleReward> records = salerRuleRewardIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)salerRuleRewardIPage.getSize()
                    ,(int)salerRuleRewardIPage.getCurrent()
                    ,(int)salerRuleRewardIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<SalerRuleReward>> results =
        new AbstractPageService.PageResults(records, page);
        return results;
    }

    public static List<ProductAndBatchGetCodeMO> constructProductAndBatchMOByPPArr(JSONArray jsonArray) {
        Map<String, ProductAndBatchGetCodeMO> productMap=new HashMap<String, ProductAndBatchGetCodeMO>();
        for(int i=0;i<jsonArray.size();i++) {
            JSONObject prodObject=jsonArray.getJSONObject(i);
            String productId=prodObject.getString("productId");
            //TODO 到时候要换成基础平台的批次
            String productBatchId=prodObject.getString("traceBatchInfoId");

            ProductAndBatchGetCodeMO  productAndBatchGetCodeMO=productMap.get(productId);
            if (null==productAndBatchGetCodeMO) {
                productAndBatchGetCodeMO=new ProductAndBatchGetCodeMO();
                productAndBatchGetCodeMO.setProductId(productId);
            }

            List<Map<String, String>> productBatchIdS=productAndBatchGetCodeMO.getProductBatchList();
            if (null==productBatchIdS) {
                productBatchIdS=new ArrayList<Map<String,String>>();
            }
            Map<String, String> batchmap=new HashMap<String, String>();
            batchmap.put("productBatchId", productBatchId);
            productBatchIdS.add(batchmap);
            productAndBatchGetCodeMO.setProductBatchList(productBatchIdS);
            productMap.put(productId, productAndBatchGetCodeMO);
        }
        List<ProductAndBatchGetCodeMO> productAndBatchGetCodeMOs=new ArrayList<ProductAndBatchGetCodeMO>();
        for(Map.Entry<String, ProductAndBatchGetCodeMO> entry:productMap.entrySet()) {
            productAndBatchGetCodeMOs.add(entry.getValue());
        }
        return productAndBatchGetCodeMOs;
    }

}
