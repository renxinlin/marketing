package com.jgw.supercodeplatform.prizewheels.domain.model;

import com.jgw.supercodeplatform.prizewheels.infrastructure.expectionsUtil.ErrorCodeEnum;
import org.apache.http.util.Asserts;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ProbabilityCalculator {

    private List <WheelsReward> rewards;

   public void initRewards(List<WheelsReward> rewards){
       this.rewards = rewards;
   }

    public WheelsReward  calculator(){
       Asserts.check(CollectionUtils.isEmpty(rewards), ErrorCodeEnum.NULL_ERROR.getErrorMessage());
        double random = Math.random()* 100D;
        // [0,1）


        if(true){
            return null;
        }

       return  null;

    }

}
