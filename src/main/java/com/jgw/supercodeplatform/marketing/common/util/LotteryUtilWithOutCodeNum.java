package com.jgw.supercodeplatform.marketing.common.util;
/**
 * 中奖算法类
 * @author czm
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.exception.SuperCodeExtException;
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
				probabilityLowRan = 1 + probabilityHighRan;
				probabilityHighRan = probabilityHighRan+prizeProbability;
			}
			//如果随机数在当前的概率范围内就直接返回不需要继续循环
			if (rand>=probabilityLowRan && rand<=probabilityHighRan) {
				return mTypeMO;
			}
		}
		throw new SuperCodeExtException("抽奖算法更新中，暂时无法抽奖请稍后再试", 500);
	}

	/**
	 *
	 * @param mPrizeTypes
	 * @param hasFirst 是否已经抽过一等奖
	 * @return
	 * @throws SuperCodeException
	 */
	public static MarketingPrizeTypeMO platfromStartLottery(List<MarketingPrizeTypeMO> mPrizeTypes, boolean hasFirst)
			throws SuperCodeException {
		if (null == mPrizeTypes || mPrizeTypes.isEmpty()) {
			throw new SuperCodeException("中奖算法参数不能为空", 500);
		}
		int probabilityLowRan=0;
		int probabilityHighRan=0;
		int rand=(int) (Math.random() * 100+1);
		if (hasFirst) {
			//末等奖
			MarketingPrizeTypeMO endPizeType = mPrizeTypes.stream().filter(prize -> prize.getAwardType() != null && prize.getAwardType() >0)
					.max((v1,v2) -> v1.getAwardType().compareTo(v2.getAwardType())).get();
			//一等奖
			MarketingPrizeTypeMO firstPizeType = mPrizeTypes.stream().filter(prize -> prize.getAwardType() != null && prize.getAwardType() >0)
					.min((v1,v2) -> v1.getAwardType().compareTo(v2.getAwardType())).get();
			endPizeType.setPrizeProbability(endPizeType.getPrizeProbability() + firstPizeType.getPrizeProbability());
			mPrizeTypes.remove(firstPizeType);
		}

		int size = mPrizeTypes.size();
		for (int i=0;i<size;i++) {
			MarketingPrizeTypeMO mTypeMO=mPrizeTypes.get(i);
			Integer prizeProbability=mTypeMO.getPrizeProbability();
			if (i==0) {
				probabilityLowRan=1;
				probabilityHighRan=prizeProbability;
			}else {
				probabilityLowRan = 1 + probabilityHighRan;
				probabilityHighRan = probabilityHighRan+prizeProbability;
			}
			//如果随机数在当前的概率范围内就直接返回不需要继续循环
			if (rand>=probabilityLowRan && rand<=probabilityHighRan) {
				return mTypeMO;
			}
		}
		throw new SuperCodeExtException("抽奖算法更新中，暂时无法抽奖请稍后再试", 500);
	}

}
