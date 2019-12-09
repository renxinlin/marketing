package com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.bizimpl;

import com.alibaba.fastjson.JSON;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.constants.BusinessTypeEnum;
import com.jgw.supercodeplatform.marketing.constants.WechatConstants;
import com.jgw.supercodeplatform.marketing.dao.integral.IntegralRuleProductMapperExt;
import com.jgw.supercodeplatform.marketing.enums.market.MemberTypeEnums;
import com.jgw.supercodeplatform.marketing.mq.receiver.bizchain.AutoFetchChainAbs;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.GetSbatchIdsByPrizeWheelsFeign;
import com.jgw.supercodeplatform.prizewheels.infrastructure.feigns.dto.SbatchUrlDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class IntegralAutoFecthService extends AutoFetchChainAbs<List<Map<String, Object>>> {

 
    @Value("${marketing.domain.url}")
    private String marketingDomain;

    @Autowired
    private IntegralRuleProductMapperExt integralRuleProductMapperExt;

    @Autowired
    private GetSbatchIdsByPrizeWheelsFeign getSbatchIdsByPrizeWheelsFeign;
    /**
     * 执行业务
     * @param batchList
     * @return
     */
    @Override
    public boolean shouldProcess(List<Map<String, Object>> batchList) {
        return true;
    }

    @Override
    protected void ifDoBiz(List<Map<String, Object>> batchList) {
        String bindUrl = marketingDomain + WechatConstants.SCAN_CODE_JUMP_URL;
        Map<String, SbatchUrlDto> productSbatchMap = new HashMap<>();
        for (Map<String, Object> map : batchList) {
            String productId = (String)map.get("productId");
            String productBatchId = (String)map.get("productBatchId");
            String codeBatch = map.get("codeBatch") == null? null : map.get("codeBatch").toString();
            log.info("收到mq:productId=" + productId + ",productBatchId=" + productBatchId + ",codeBatch=" + codeBatch);
            // 校验
            if (StringUtils.isBlank(productId) || StringUtils.isBlank(codeBatch)) {
                log.error("获取码管理平台推送的新增批次mq消息，值有空值productId=" + productId + ",codeBatch=" + codeBatch);
                continue;
            }
            long prodIdCount = integralRuleProductMapperExt.countProductId(productId);
            //如果查询不到该产品对应的记录则不作处理
            if (prodIdCount <= 0) {
                continue;
            }
            String key = productId + "_" +codeBatch;
            SbatchUrlDto bachSbatchUrlDto = productSbatchMap.get(key);
            if (bachSbatchUrlDto == null) {
                SbatchUrlDto sbatchUrlDto = new SbatchUrlDto();
                sbatchUrlDto.setClientRole(MemberTypeEnums.VIP.getType()+"");
                sbatchUrlDto.setUrl(bindUrl);
                sbatchUrlDto.setBatchId(Long.valueOf(codeBatch));
                sbatchUrlDto.setBusinessType(BusinessTypeEnum.INTEGRAL.getBusinessType());
                sbatchUrlDto.setProductId(productId);
                productSbatchMap.put(key, sbatchUrlDto);
            }
            List<SbatchUrlDto> sbatchUrlDtoList = new ArrayList<>(productSbatchMap.values());
            log.info("码管理绑定积分入参：{}", JSON.toJSONString(sbatchUrlDtoList));
            RestResult bindBatchBody = getSbatchIdsByPrizeWheelsFeign.bindingUrlAndBizType(sbatchUrlDtoList);
            log.info("码管理绑定积分返回：{}", JSON.toJSONString(bindBatchBody));
            int bindBatchstate=bindBatchBody.getState();
            if (200!=bindBatchstate) {
                throw new SuperCodeExtException("积分设置时根据生码批次绑定url失败："+bindBatchBody, 500);
            }
        }
    }

    @Override
    protected void ifNotBiz(List<Map<String, Object>> datafromMq) {

    }
}
