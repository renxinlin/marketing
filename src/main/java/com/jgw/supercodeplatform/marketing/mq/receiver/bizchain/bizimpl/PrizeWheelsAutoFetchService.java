package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.util.RestTemplateUtil;
import com.jgw.supercodeplatform.marketing.constants.BizTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.RoleTypeEnum;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingActivityProductMapper;
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
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizewheelsProduct;
import com.jgw.supercodeplatform.prizewheels.domain.constants.ActivityTypeConstant;
import com.jgw.supercodeplatform.prizewheels.domain.constants.CallBackConstant;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理大转盘的自动追加
 */
@Slf4j
@Service
public class PrizeWheelsAutoFetchService extends AutoFetchChainAbs<List<Map<String, Object>>> {
    @Autowired
    private GetSbatchIdsByPrizeWheelsFeign getSbatchIdsByPrizeWheelsFeign;

    @Value("${marketing.domain.url}")
    private String marketingDomain;


    @Autowired
    private MarketingActivityProductMapper mProductMapper;
    @Override
    public boolean shouldProcess(List<Map<String, Object>> datafromMq) {
        return true;
    }

    @Override
    protected void ifDoBiz(List<Map<String, Object>> batchList) {
        List<SbatchUrlDto>  bindCouponActivitys = new ArrayList<>();
        for (Map<String, Object> map : batchList) {
            // 数据转换
            Object productId=map.get("productId");
            Object productBatchId=map.get("productBatchId");
            Object codeBatch=map.get("codeBatch");
            log.info("大转盘自动追加:productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
            if (null==productId || null==productBatchId || null==codeBatch) {
                log.warn("获取码管理平台推送的新增批次mq消息，值有空值productId="+productId+",productBatchId="+productBatchId+",codeBatch="+codeBatch);
                continue;
            }
            String strProductId=String.valueOf(productId);
            String strProductBatchId=String.valueOf(productBatchId);

            // 大转盘数据获取
            MarketingPrizewheelsProduct needAutoFetchPrizewheels = mProductMapper.selectNeedAutoFetchPrizewheels(strProductId, strProductBatchId,AutoGetEnum.BY_AUTO.getAuto(), ActivityTypeConstant.wheels.intValue());
            if(needAutoFetchPrizewheels == null ){
                continue;
            }

            // 如果自动追加就处理 业务处理
            boolean needSend = updateSbathId((String) codeBatch, needAutoFetchPrizewheels);
            // 自动追加成功 绑定码管理
            if(needSend){
                SbatchUrlDto sbatchUrlDto = new SbatchUrlDto();
                sbatchUrlDto.setProductBatchId(productBatchId.toString());
                sbatchUrlDto.setProductId(productId.toString());
                sbatchUrlDto.setBusinessType(BizTypeEnum.MARKETING_COUPON.getBusinessType());
                sbatchUrlDto.setBatchId(Long.valueOf(codeBatch.toString()));
                sbatchUrlDto.setUrl(CallBackConstant.PRIZE_WHEELS_URL);
                sbatchUrlDto.setClientRole(RoleTypeEnum.MEMBER.getMemberType() + "");
//                CouponActivity couponActivity = new CouponActivity();
//                couponActivity.setProductId(strProductId);
//                couponActivity.setProductBatchId(strProductBatchId);
//                List<String> codeBatchs = new ArrayList<>();
//                codeBatchs.add((String) codeBatch);
//                couponActivity.setCodeBatchIds(codeBatchs);
//                couponActivity.setStatus(BindCouponRelationToCodeManagerEnum.BIND.getBinding());
                bindCouponActivitys.add(sbatchUrlDto);
                try {
                    sendToCodeManager(bindCouponActivitys);
                } catch (Exception e) {
                    e.printStackTrace();
                    // todo 保留日志级别:  码管理服务调用失败
                    log.error("CouponAutoFecthService do biz error when custome code mamaner {}",e.getMessage());
                }
            }

        }
    }

    /**
     *
     * @param activitys
     * @throws SuperCodeException
     */
    private void sendToCodeManager(List<SbatchUrlDto> activitys) {
        if(CollectionUtils.isEmpty(activitys)){
            return;
        }
        getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(activitys);
//        String jsonData=JSONObject.toJSONString(activitys);
//        restTemplateUtil.postJsonDataAndReturnJosn(CallBackConstant.PRIZE_WHEELS_URL, jsonData, null);
    }

    /**
     *
     * @param codeBatch
     * @param needAutoFetchPrizewheels
     * @return 码管理是否需要处理绑定
     */
    private boolean updateSbathId(String codeBatch, MarketingPrizewheelsProduct needAutoFetchPrizewheels) {
        boolean needSendToCodeManager = false;
        // 数据转换
        MarketingActivityProduct marketingActivityProduct = new MarketingActivityProduct();
        marketingActivityProduct.setId(needAutoFetchPrizewheels.getId());
        marketingActivityProduct.setSbatchId(needAutoFetchPrizewheels.getSbatchId());

        if(StringUtils.isEmpty(marketingActivityProduct.getSbatchId()) ){
            marketingActivityProduct.setSbatchId(codeBatch);
            needSendToCodeManager = true;
        }else if(
            !StringUtils.isEmpty(marketingActivityProduct.getSbatchId())
            && !marketingActivityProduct.getSbatchId().contains(codeBatch)
        ){
            marketingActivityProduct.setSbatchId(marketingActivityProduct.getSbatchId()+","+codeBatch);
            needSendToCodeManager = true;
        }
        if (needSendToCodeManager){
            mProductMapper.updateWhenAutoFetch(marketingActivityProduct);
            return needSendToCodeManager;
        }
        return false;
    }



    @Override
    protected void ifNotBiz(List<Map<String, Object>> datafromMq) {
        //
    }
}
