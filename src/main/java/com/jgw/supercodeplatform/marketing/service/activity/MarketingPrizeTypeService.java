package com.jgw.supercodeplatform.marketing.service.activity;

import com.jgw.supercodeplatform.marketing.common.model.RestResult;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketingPrizeTypeService {

    @Autowired
    private MarketingPrizeTypeMapper mapper;


    public RestResult<MarketingPrizeType> getActivityPrizeInfoByeditPage(Long activitySetId) {
        RestResult restResult = new RestResult();
        // 校验
        if(activitySetId == null || activitySetId <= 0 ){
            restResult.setState(500);
            restResult.setMsg("活动id校验失败");
            return  restResult;
        }
        // 获取中奖规则-奖次信息
        List<MarketingPrizeType> marketingPrizeTypes = mapper.selectByActivitySetId(activitySetId);
        // 返回
        restResult.setState(200);
        restResult.setMsg("success");
        restResult.setResults(marketingPrizeTypes);
        return  restResult;
    }
}
