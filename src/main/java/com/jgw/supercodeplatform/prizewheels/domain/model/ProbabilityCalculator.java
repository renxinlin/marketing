package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.alibaba.fastjson.JSONObject;
import com.jgw.supercodeplatform.marketing.exception.NotGetPrizeWheelsException;
import com.jgw.supercodeplatform.prizewheels.domain.constants.LoseAwardConstant;
import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Slf4j
public class ProbabilityCalculator {

    private List<WheelsReward> rewards;

    public void initRewards(List<WheelsReward> rewards) {
        Asserts.check(!CollectionUtils.isEmpty(rewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        this.rewards = rewards;
    }

    /**
     * 产生个(0，100]随机数 注意随机数在（0，100]
     * 画好左开右闭区间段（0,50],(50,100]  注意区间段左开右闭,左端点0，右端点100
     * 找出随机数所在区间
     * 获取该区间奖项
     *
     * @return
     */
    public WheelsReward calculator() {

        WheelsReward getWheelsReward = null;

        double random = Math.random() * 100D;// (0,1]
        double lowerprobability = 0D;
        for (WheelsReward wheelsReward : rewards) {

            double upperprobability = lowerprobability + wheelsReward.getProbability();
            if (lowerprobability < random && random <= upperprobability) {
                getWheelsReward = wheelsReward;
                break;
            } else {
                lowerprobability = upperprobability;

            }
        }

        if(getWheelsReward == null){
            log.error("奖项为空{}", JSONObject.toJSONString(rewards));
            throw new NotGetPrizeWheelsException("未中奖,再来一次!!!");
        }
        if (getWheelsReward.getLoseAward().intValue() == LoseAwardConstant.yes.intValue()) {
            throw new NotGetPrizeWheelsException("未中奖,再来一次!");
        }
        return getWheelsReward;

    }

}
