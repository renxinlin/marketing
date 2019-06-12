package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.AutoGetEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFetchChainAbs;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CouponAutoFecthService  extends AutoFetchChainAbs<List<Map<String, Object>>> {

    protected static Logger logger = LoggerFactory.getLogger(CouponAutoFecthService.class);

    @Autowired
    private MarketingActivityProductMapper mProductMapper;

    @Autowired
    private MarketingActivitySetMapper mSetMapper;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Value("${marketing.domain.url}")
    private String marketingDomain;

    @Value("${rest.codemanager.url}")
    private String codeManagerUrl;

    /**
     * 执行业务
     * @param batchList
     * @return
     */
    @Override
    public boolean shouldProcess(List<Map<String, Object>> batchList) {
        return true;
    }

    private void sendToCodeManager(String productId, String productBatchId, Object codeBatch) {
        // TODO 待实现，等待建强协商交互
    }

    private void updateSbathId(Object codeBatch, MarketingActivityProduct marketingActivityProduct) {
        // TODO 类型还不知道
        marketingActivityProduct.setSbatchId((String) codeBatch);
        mProductMapper.updateWhenAutoFetch(marketingActivityProduct);
    }

    @Override
    protected void ifDoBiz(List<Map<String, Object>> batchList) {
        for (Map<String, Object> map : batchList) {
            Object productId=map.get("productId");
            Object productBatchId=map.get("productBatchId");
            Object codeBatch=map.get("codeBatch");
            logger.info("收到mq:productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
            if (null==productId || null==productBatchId || null==codeBatch) {
                logger.error("获取码管理平台推送的新增批次mq消息，值有空值productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
                continue;
            }
            String strProductId=String.valueOf(productId);
            String strProductBatchId=String.valueOf(productBatchId);
            MarketingActivityProduct marketingActivityProduct = mProductMapper.selectByProductAndProductBatchIdWithReferenceRole(strProductId, strProductBatchId, ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
            MarketingActivitySet activitySet = mSetMapper.selectById(marketingActivityProduct.getActivitySetId());
            // 业务解耦，该链只处理抵扣券
            if(ActivityIdEnum.ACTIVITY_COUPON.getId().intValue() == activitySet.getActivityId().intValue()){
                continue;
            }
            if(activitySet.getAutoFetch() == AutoGetEnum.BY_AUTO.getAuto()){
                // 如果自动追加就处理
                updateSbathId((String) codeBatch,marketingActivityProduct);
            }
            String validCondition = activitySet.getValidCondition();
            MarketingActivitySetCondition marketingActivitySetCondition = JSONObject.parseObject(validCondition, MarketingActivitySetCondition.class);
            Byte acquireCondition = marketingActivitySetCondition.getAcquireCondition();
            if(acquireCondition!=null && CouponAcquireConditionEnum.SHOPPING.getCondition().intValue() == acquireCondition.intValue() ){
                sendToCodeManager(strProductId,strProductBatchId,codeBatch);
            }
        }

    }

    @Override
    protected void ifNotBiz(List<Map<String, Object>> datafromMq) {

    }
}
