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
		if (null == mPrizeTypes || mPrizeTypes.size() <= 1) {
			throw new SuperCodeException("中奖算法参数不能为空", 500);
		}
		//没中奖和一等奖
		MarketingPrizeTypeMO noPrizeTypeMo = null, firstPizeType = null;
		for (MarketingPrizeTypeMO prizeTypeMO : mPrizeTypes) {
			if (prizeTypeMO.getAwardGrade().intValue() == 0) {
				noPrizeTypeMo = prizeTypeMO;
			}
			if (prizeTypeMO.getAwardGrade().intValue() == 1) {
				firstPizeType = prizeTypeMO;
			}
		}
		int probabilityLowRan=0;
		int probabilityHighRan=0;
		int rand=(int) (Math.random() * 100+1);
		//为0表示未中奖的虚拟奖项，此时中奖概率并不会为百分百
		if (noPrizeTypeMo.getPrizeProbability().intValue() > 0){
			if (hasFirst) {
				//末等奖
				MarketingPrizeTypeMO endPizeType = mPrizeTypes.stream().filter(prize -> prize.getAwardGrade() != null && prize.getAwardGrade() >0)
						.max((v1,v2) -> v1.getAwardGrade().compareTo(v2.getAwardGrade())).get();
				if (endPizeType.equals(firstPizeType)) {
					return noPrizeTypeMo;
				}
				endPizeType.setPrizeProbability(endPizeType.getPrizeProbability() + firstPizeType.getPrizeProbability());
				mPrizeTypes.remove(firstPizeType);
			}
		} else {
			//得到没有库存的奖项
			List<MarketingPrizeTypeMO> noStockPizeTypes = mPrizeTypes.stream().filter(prize -> prize.getRemainingStock().intValue() <= 0).collect(Collectors.toList());
			//得到不能用的概率和
			int disabledPrizeProbility = noStockPizeTypes.stream().mapToInt(MarketingPrizeTypeMO::getPrizeProbability).sum();
			mPrizeTypes.removeAll(noStockPizeTypes);
			if (mPrizeTypes.size() <= 1) {
				//说明此时只剩下一个虚拟奖项，即不中奖
				return noPrizeTypeMo;
			}
			//如果已经抽过一等奖，则一等奖也为不可用
			if (hasFirst && firstPizeType.getRemainingStock().intValue() > 0) {
				disabledPrizeProbility = disabledPrizeProbility + firstPizeType.getPrizeProbability();
				mPrizeTypes.remove(firstPizeType);
			}
			if (mPrizeTypes.size() <=1){
				return noPrizeTypeMo;
			}
			MarketingPrizeTypeMO endPizeType = mPrizeTypes.stream().filter(prize -> prize.getAwardGrade() != null && prize.getAwardGrade() >0)
					.max((v1,v2) -> v1.getAwardGrade().compareTo(v2.getAwardGrade())).get();
			endPizeType.setPrizeProbability(endPizeType.getPrizeProbability() + disabledPrizeProbility);
		}
		int size = mPrizeTypes.size();
		for (int i=0; i<size; i++) {
			MarketingPrizeTypeMO mTypeMO = mPrizeTypes.get(i);
			Integer prizeProbability = mTypeMO.getPrizeProbability();
			if (i==0) {
				probabilityLowRan = 1;
				probabilityHighRan = prizeProbability;
			}else {
				probabilityLowRan = 1 + probabilityHighRan;
				probabilityHighRan = probabilityHighRan+prizeProbability;
			}
			//如果随机数在当前的概率范围内就直接返回不需要继续循环
			if (rand>=probabilityLowRan && rand<=probabilityHighRan) {
				if (mTypeMO.getRemainingStock().intValue() <= 0) {
					return noPrizeTypeMo;
				}
				return mTypeMO;
			}
		}
		throw new SuperCodeExtException("抽奖算法更新中，暂时无法抽奖请稍后再试", 500);
	}

}
