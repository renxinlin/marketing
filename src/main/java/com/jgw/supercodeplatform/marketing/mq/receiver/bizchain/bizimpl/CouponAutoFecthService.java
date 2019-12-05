package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.BizTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivitySetMapper;
import com.jgw.supercodeplatform.marketing.dto.codemanagerservice.CouponActivity;
import com.jgw.supercodeplatform.marketing.enums.market.ActivityIdEnum;
import com.jgw.supercodeplatform.marketing.enums.market.AutoGetEnum;
import com.jgw.supercodeplatform.marketing.enums.market.ReferenceRoleEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.BindCouponRelationToCodeManagerEnum;
import com.jgw.supercodeplatform.marketing.enums.market.coupon.CouponAcquireConditionEnum;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFetchChainAbs;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySet;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivitySetCondition;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 不考虑顺序一致性问题:原因1:2B的系统，原因2：产品挂在一个组织下无并发
 *
 * 产品批次参与抵扣券: 绑定与产品在活动中变更无关，只要产品参与抵扣券就要绑定到code管理
 * 抵扣绑定: 不考虑业务:删除时候解绑;新增时候绑定[覆盖老的删除也解绑下]
 *           动态追加:給产品加上吗批次后在绑定相关关系
 *
 *
 * 备注: 产品批次绑定生码批次后，这个生码批次可以解绑所有；【导致产品表的码批次数据并不准确】
 *
 *
 *
 * 设计本身就很复杂,产品和码批次信息,业务系统应当可以看到，现在业务又要保存，还要不断处理变动，数据还不准确【码平台存在修改不通知】
 * 核销:
 * 数据不准确:  1 一致:业务正常处理
 *             2  对方有我没有: 仅当前数量或者自动获取乱七八糟的变更:对比后不可领取
 *             3  对方没有我有:[解除马关联]对比后不可领取
 */
@Service
public class CouponAutoFecthService  extends AutoFetchChainAbs<List<Map<String, Object>>> {

    protected static Logger logger = LoggerFactory.getLogger(CouponAutoFecthService.class);

    @Autowired
    private MarketingActivityProductMapper mProductMapper;

    @Autowired
    private MarketingActivitySetMapper mSetMapper;

    @Autowired
    private GetSbatchIdsByPrizeWheelsFeign getSbatchIdsByPrizeWheelsFeign;

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

    private void sendToCodeManager(  List<SbatchUrlDto>  couponActivitys ) {
        // TODO 待实现，等待建强协商交互
        if(CollectionUtils.isEmpty(couponActivitys)){
            return;
        }
//        String jsonData=JSONObject.toJSONString(couponActivitys);
        getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(couponActivitys);
//        restTemplateUtil.postJsonDataAndReturnJosn(codeManagerUrl, jsonData, null);
    }

    private void updateSbathId(Object codeBatch, MarketingActivityProduct marketingActivityProduct) {
        // TODO 类型还不知道
        marketingActivityProduct.setSbatchId(codeBatch+","+marketingActivityProduct.getSbatchId());
        mProductMapper.updateWhenAutoFetch(marketingActivityProduct);
    }

    @Override
    protected void ifDoBiz(List<Map<String, Object>> batchList) {
        String bindUrl = marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL;
        List<SbatchUrlDto>  bindCouponActivitys = new ArrayList<>();
        for (Map<String, Object> map : batchList) {
            Object productId=map.get("productId");
            Object productBatchId=map.get("productBatchId");
            Object codeBatch=map.get("codeBatch");
            logger.info("收到mq:productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
            // 校验
            if (null==productId || null==codeBatch) {
                logger.warn("获取码管理平台推送的新增批次mq消息，值有空值productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
                continue;
            }
            // 校验
            String strProductId=String.valueOf(productId);
            String strProductBatchId=(String) productBatchId;
            MarketingActivityProduct marketingActivityProduct = mProductMapper.selectByProductAndProductBatchIdWithReferenceRole(strProductId, strProductBatchId, ReferenceRoleEnum.ACTIVITY_MEMBER.getType());
            if(marketingActivityProduct == null ){
                continue;
            }
            // 校验
            MarketingActivitySet activitySet = mSetMapper.selectById(marketingActivityProduct.getActivitySetId());
            // 业务解耦，该链只处理抵扣券
            if(ActivityIdEnum.ACTIVITY_COUPON.getId().intValue() != activitySet.getActivityId().intValue()){
                continue;
            }
            if(activitySet.getAutoFetch() == AutoGetEnum.BY_AUTO.getAuto()){
                // 如果自动追加就处理 业务处理1
                updateSbathId(codeBatch,marketingActivityProduct);
                // 业务处理2
                String validCondition = activitySet.getValidCondition();
                MarketingActivitySetCondition marketingActivitySetCondition = JSONObject.parseObject(validCondition, MarketingActivitySetCondition.class);
                Byte acquireCondition = marketingActivitySetCondition.getAcquireCondition();
                if(acquireCondition!=null && CouponAcquireConditionEnum.SHOPPING.getCondition().intValue() == acquireCondition.intValue() ){
                    SbatchUrlDto sbatchUrlDto = new SbatchUrlDto();
                    sbatchUrlDto.setProductBatchId(strProductBatchId);
                    sbatchUrlDto.setProductId(strProductId);
                    sbatchUrlDto.setBusinessType(BizTypeEnum.MARKETING_COUPON.getBusinessType());
                    sbatchUrlDto.setBatchId(Long.valueOf(codeBatch.toString()));
                    sbatchUrlDto.setUrl(bindUrl);
                    sbatchUrlDto.setClientRole(RoleTypeEnum.MEMBER.getMemberType() + "");
//                    CouponActivity couponActivity = new CouponActivity();
//                    couponActivity.setProductId(strProductId);
//                    couponActivity.setProductBatchId(strProductBatchId);
//                    List<String> codeBatchs = new ArrayList<>();
//                    codeBatchs.add((String) codeBatch);
//                    couponActivity.setCodeBatchIds(codeBatchs);
//                    couponActivity.setStatus(BindCouponRelationToCodeManagerEnum.BIND.getBinding());
                    bindCouponActivitys.add(sbatchUrlDto);
                }
            }

        }
        // 业务处理2
        try {
            sendToCodeManager(bindCouponActivitys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("CouponAutoFecthService do biz error when custome code mamaner {}",e.getMessage());
        }

    }

    @Override
    protected void ifNotBiz(List<Map<String, Object>> datafromMq) {

    }
}
