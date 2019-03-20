package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.dto.activity.ProductBatchParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarketingActivityProductService {

    @Autowired
    private MarketingActivityProductMapper mapper;

    public RestResult<HashSet<MarketingActivityProductParam>> getActivityProductInfoByeditPage(Long activitySetId) {
        RestResult restResult = new RestResult();
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            restResult.setState(500);
            restResult.setMsg("活动id校验失败");
            return  restResult;
        }
        // 获取中奖规则-奖次信息
        String strActivitySetId = String.valueOf(activitySetId);
        List<MarketingActivityProduct> marketingActivityProducts = mapper.selectByActivitySetId(strActivitySetId);

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
}
