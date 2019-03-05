package com.jgw.supercodeplatform.marketing.common.util;
/**
 * 中奖算法类
 * @author czm
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;

public class LotteryUtil {
	
  public static MarketingPrizeTypeMO lottery(List<MarketingPrizeTypeMO> marketingPrizeTypeMOs) throws SuperCodeException {
	  if (null==marketingPrizeTypeMOs || marketingPrizeTypeMOs.isEmpty()) {
		throw new SuperCodeException("中奖算法参数不能为空", 500);
	  }
	  Collections.sort(marketingPrizeTypeMOs);
	  
	  int w = (int)(Math.random()*100+1);
	  MarketingPrizeTypeMO mTypeMO0=marketingPrizeTypeMOs.get(0);
	  if (w<=mTypeMO0.getPrizeProbability() ) {
		return marketingPrizeTypeMOs.get(0);
	  }else {
		  int currentPrizeProbability=0;
		  int nextPrizeProbability=0;
		  MarketingPrizeTypeMO currentTypeMo=null;
		  MarketingPrizeTypeMO nextTypeMo=null;
		  int i=0;
		  int lastProbability=0;
		  for(;i<marketingPrizeTypeMOs.size()-1;i++) {
			  currentTypeMo=marketingPrizeTypeMOs.get(i);
			  nextTypeMo=marketingPrizeTypeMOs.get(i+1);
			  
			  currentPrizeProbability=currentTypeMo.getPrizeProbability();
			  lastProbability+=currentPrizeProbability;
			  nextPrizeProbability=nextTypeMo.getPrizeProbability()+lastProbability;
			  
			  if (w>currentPrizeProbability &&w<=nextPrizeProbability) {
				  marketingPrizeTypeMOs.get(i+1);
				  return marketingPrizeTypeMOs.get(i+1);
			  }
		  }
		  throw new SuperCodeException("抽奖算法返回异常，无数据返回,，随机数="+w, 500);
	  }
  }
  

}
