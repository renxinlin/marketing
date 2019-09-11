package com.jgw.supercodeplatform.marketingsaler.integral.domain.transfer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.ProductAndBatchGetCodeMO;
import com.jgw.supercodeplatform.marketing.common.page.AbstractPageService;
import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.enums.market.IntegralReasonEnum;
import com.jgw.supercodeplatform.marketing.vo.activity.H5LoginVO;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRecord;
import com.jgw.supercodeplatform.marketingsaler.integral.domain.pojo.SalerRuleExchange;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 积分奖励
 */
public class SalerRecordTransfer extends CommonTransfer{
    /**
     * 分页查询的page对象
     */
    public static IPage<SalerRecord> getPage(DaoSearch daoSearch) {
        int current = daoSearch.getCurrent();
        int pageSize = daoSearch.getPageSize();
        Page<SalerRecord> page = new Page<>(current,pageSize<=0 ? defaultSize : pageSize);
        return page;
    }

    /**
     * 分页查询的条件对象
     * @param daoSearch
     * @return
     */
    public static Wrapper<SalerRecord> getPageParam(DaoSearch daoSearch,String organizationId) {
        QueryWrapper<SalerRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("OrganizationId",organizationId);
        if(daoSearch != null && !StringUtils.isEmpty(daoSearch.getSearch())){
            queryWrapper.and(query ->query.like("SalerName",daoSearch.getSearch())
                    .or().like("SalerMobile",daoSearch.getSearch())
                    .or().like("CustomerName",daoSearch.getSearch())
                    .or().like("IntegralReason",daoSearch.getSearch())
                    .or().like("IntegralNum",daoSearch.getSearch())
                    .or().like("ProductName",daoSearch.getSearch())
                    .or().like("OuterCodeId",daoSearch.getSearch())
                    .or().like("",daoSearch.getSearch())
            );
        }
        queryWrapper.orderByDesc("CreateDate");
        return queryWrapper;
    }

    /**
     * 将mybatis-plus的查询结果转换成VO对象
     * @param salerRecordIPage
     * @return
     */
    public static AbstractPageService.PageResults<List<SalerRecord>> toPageResult(IPage<SalerRecord> salerRecordIPage) {
        List<SalerRecord> records = salerRecordIPage.getRecords();
        com.jgw.supercodeplatform.marketing.common.page.Page page =
                null;
        try {
            page = new com.jgw.supercodeplatform.marketing.common.page.Page(
                    (int)salerRecordIPage.getSize()
                    ,(int)salerRecordIPage.getCurrent()
                    ,(int)salerRecordIPage.getTotal());
        } catch (SuperCodeException e) {
            e.printStackTrace();
        }

        AbstractPageService.PageResults<List<SalerRecord>> results =
        new AbstractPageService.PageResults(records, page);
        return results;
    }

    /**
     * copy会员代码
     * @param jsonArray
     * @return
     */
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

    /**
     * 兑换时构建订单信息
     * @param salerRuleExchange
     * @param user
     * @return
     */
    public static SalerRecord buildRecord(SalerRuleExchange salerRuleExchange, H5LoginVO user,double money ) {
        SalerRecord salerRecord = new SalerRecord();

        salerRecord.setCreateDate(new Date());
        salerRecord.setCustomerId(user.getCustomerId());
        salerRecord.setCustomerName(user.getCustomerName());
        // 负数
        salerRecord.setIntegralNum(-salerRuleExchange.getExchangeIntegral());
        salerRecord.setIntegralReasonCode(IntegralReasonEnum.SALER_EXCHANGE.getIntegralReasonCode());
        salerRecord.setIntegralReason(IntegralReasonEnum.SALER_EXCHANGE.getIntegralReason());

        salerRecord.setOrganizationId(user.getOrganizationId());
        salerRecord.setOrganizationName(user.getOrganizationName());
        salerRecord.setSalerId(user.getMemberId());
        salerRecord.setSalerMobile(user.getMobile());
        salerRecord.setSalerName(user.getMemberName());
        // 计算中奖金额
        salerRecord.setSalerAmount(new BigDecimal(money));

        return salerRecord;



    }
}
