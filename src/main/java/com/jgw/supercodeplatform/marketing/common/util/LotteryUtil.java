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
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;

public class LotteryUtil {
	
  public static MarketingPrizeTypeMO lottery(List<MarketingPrizeType> mPrizeTypes,Long codeTotalNum) throws SuperCodeException {
	  if (null==mPrizeTypes || mPrizeTypes.isEmpty()) {
		throw new SuperCodeException("中奖算法参数不能为空", 500);
	  }
		List<MarketingPrizeTypeMO> mTypeMOs=new ArrayList<MarketingPrizeTypeMO>();
		int i=0;
		for (MarketingPrizeType marketingPrizeType : mPrizeTypes) {
			Integer probability=marketingPrizeType.getPrizeProbability();
			MarketingPrizeTypeMO mo=new MarketingPrizeTypeMO();
			mo.setActivitySetId(marketingPrizeType.getActivitySetId());
			mo.setId(marketingPrizeType.getId());
			mo.setPrizeAmount(marketingPrizeType.getPrizeAmount());
			mo.setPrizeProbability(probability);
			mo.setPrizeTypeName(marketingPrizeType.getPrizeTypeName());
			mo.setRandomAmount(marketingPrizeType.getRandomAmount());
			mo.setWiningNum(marketingPrizeType.getWiningNum()==null?0L:marketingPrizeType.getWiningNum());
			mo.setLowRand(marketingPrizeType.getLowRand());
		    mo.setHighRand(marketingPrizeType.getHighRand());
			if (i==mPrizeTypes.size()-1) {
				mo.setTotalNum(codeTotalNum);
			}else {
				long num = (long) (marketingPrizeType.getPrizeProbability() / 100.00 * codeTotalNum);
				mo.setTotalNum(num);
				codeTotalNum=codeTotalNum-num;
			}
			mo.setRealPrize(marketingPrizeType.getRealPrize());
			mTypeMOs.add(mo);
			i++;
		}
		Collections.sort(mTypeMOs);
	
		//执行中奖算法
		MarketingPrizeTypeMO mPrizeTypeMO = LotteryUtil.begin(mTypeMOs);
		//如果该奖次的参与数已经大于等于他所占的百分比则重新抽奖
		while (mPrizeTypeMO.getTotalNum() <= mPrizeTypeMO.getWiningNum()) {
			mPrizeTypeMO = LotteryUtil.begin(mTypeMOs);
		}
		return mPrizeTypeMO;
  }
  
  public static MarketingPrizeTypeMO  begin(List<MarketingPrizeTypeMO> mTypeMOs) throws SuperCodeException {
	  int w = (int)(Math.random()*100+1);
	  MarketingPrizeTypeMO mTypeMO0=mTypeMOs.get(0);
	  if (w<=mTypeMO0.getPrizeProbability() ) {
		return mTypeMOs.get(0);
	  }else {
		  int currentPrizeProbability=0;
		  int nextPrizeProbability=0;
		  MarketingPrizeTypeMO currentTypeMo=null;
		  MarketingPrizeTypeMO nextTypeMo=null;
		  int i=0;
		  int lastProbability=0;
		  for(;i<mTypeMOs.size()-1;i++) {
			  currentTypeMo=mTypeMOs.get(i);
			  nextTypeMo=mTypeMOs.get(i+1);
			  
			  currentPrizeProbability=currentTypeMo.getPrizeProbability();
			  lastProbability+=currentPrizeProbability;
			  nextPrizeProbability=nextTypeMo.getPrizeProbability()+lastProbability;
			  
			  if (w>currentPrizeProbability &&w<=nextPrizeProbability) {
				  mTypeMOs.get(i+1);
				  return mTypeMOs.get(i+1);
			  }
		  }
		  throw new SuperCodeException("抽奖算法返回异常，无数据返回,，随机数="+w, 500);
	  }
  }
}
