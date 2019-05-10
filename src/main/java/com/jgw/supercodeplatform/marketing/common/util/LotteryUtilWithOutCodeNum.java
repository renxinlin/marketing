package com.jgw.supercodeplatform.marketing.common.util;
/**
 * 中奖算法类
 * @author czm
 *
 */

import java.util.ArrayList;
import java.util.List;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
/**
 * 与中将码数无关的抽奖
 * @author czm
 *
 */
public class LotteryUtilWithOutCodeNum {

	public static MarketingPrizeTypeMO startLottery(List<MarketingPrizeTypeMO> mPrizeTypes)
			throws SuperCodeException {
		if (null == mPrizeTypes || mPrizeTypes.isEmpty()) {
			throw new SuperCodeException("中奖算法参数不能为空", 500);
		}
		int probabilityLowRan=0;
		int probabilityHighRan=0;
		int rand=(int) (Math.random() * 100+1);
		for (int i=0;i<mPrizeTypes.size();i++) {
			MarketingPrizeTypeMO mTypeMO=mPrizeTypes.get(i);
			Integer prizeProbability=mTypeMO.getPrizeProbability();
			if (i==0) {
				probabilityLowRan=1;
				probabilityHighRan=prizeProbability;
			}else {
				probabilityLowRan=probabilityHighRan;
				probabilityHighRan=probabilityHighRan+prizeProbability;
			}
			//如果随机数在当前的概率范围内就直接返回不需要继续循环
			if (rand>=probabilityLowRan && rand<=probabilityHighRan) {
				return mTypeMO;
			}
		}
		throw new SuperCodeException("抽奖算法更新中，暂时无法抽奖请稍后再试", 500);
	}


	public static void main(String[] args) throws SuperCodeException {
		List<MarketingPrizeTypeMO> mPrizeTypes=new ArrayList<MarketingPrizeTypeMO>();
		MarketingPrizeTypeMO m1=new MarketingPrizeTypeMO();
		m1.setPrizeProbability(10);
		m1.setPrizeTypeName("一等奖");
		
		MarketingPrizeTypeMO m2=new MarketingPrizeTypeMO();
		m2.setPrizeProbability(20);
		m2.setPrizeTypeName("2等奖");
		
		MarketingPrizeTypeMO m3=new MarketingPrizeTypeMO();
		m3.setPrizeProbability(40);
		m3.setPrizeTypeName("3等奖");
		
		MarketingPrizeTypeMO m4=new MarketingPrizeTypeMO();
		m4.setPrizeProbability(30);
		m4.setPrizeTypeName("未中奖");
		
		mPrizeTypes.add(m1);
		mPrizeTypes.add(m2);
		mPrizeTypes.add(m3);
		mPrizeTypes.add(m4);
		for(int i=0;i<100000;i++) {
		}
	}

}
