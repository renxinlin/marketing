package com.jgw.supercodeplatform.marketing.common.util;
/**
 * 中奖算法类
 * @author czm
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;

public class LotteryUtil {


	public static List<MarketingPrizeTypeMO> judge(List<MarketingPrizeType> mPrizeTypes, Long codeTotalNum)
			throws SuperCodeException {
		if (null == mPrizeTypes || mPrizeTypes.isEmpty()) {
			throw new SuperCodeException("中奖算法参数不能为空", 500);
		}
		List<MarketingPrizeTypeMO> mTypeMOs = new ArrayList<MarketingPrizeTypeMO>();
		int i = 0;
        Collections.sort(mPrizeTypes);
        Long sum=0L;
		for (MarketingPrizeType marketingPrizeType : mPrizeTypes) {
			Integer probability = marketingPrizeType.getPrizeProbability();
			MarketingPrizeTypeMO mo = new MarketingPrizeTypeMO();
			mo.setActivitySetId(marketingPrizeType.getActivitySetId());
			mo.setId(marketingPrizeType.getId());
			mo.setPrizeAmount(marketingPrizeType.getPrizeAmount());
			mo.setPrizeProbability(probability);
			mo.setPrizeTypeName(marketingPrizeType.getPrizeTypeName());
			mo.setIsRrandomMoney(marketingPrizeType.getIsRrandomMoney());
			mo.setWiningNum(marketingPrizeType.getWiningNum() == null ? 0L : marketingPrizeType.getWiningNum());
			mo.setLowRand(marketingPrizeType.getLowRand());
			mo.setHighRand(marketingPrizeType.getHighRand());
			mo.setRealPrize(marketingPrizeType.getRealPrize());
			if (i == mPrizeTypes.size() - 1) {
				mo.setTotalNum(codeTotalNum-sum);
			} else {
				long num = (long) ((marketingPrizeType.getPrizeProbability() / 100.00) * codeTotalNum);
				mo.setTotalNum(num);
				sum += num;
			}
			System.out.println(mo.getPrizeTypeName()+":"+mo.getTotalNum());
			// 只返回没有被抽完的奖次
			if (mo.getWiningNum() < mo.getTotalNum()) {
				mTypeMOs.add(mo);
			}
			i++;
		}
		System.out.println("=============================================================");
		return mTypeMOs;
	}

	/**
	 * 该算法是通过参数未被抽完的奖次集合，随机生成一个该集合长度大小之内的数获取随机获取里面的任一奖次
	 * 
	 * @param mTypeMOs
	 * @return
	 * @throws SuperCodeException
	 */
	public static MarketingPrizeTypeMO lottery(List<MarketingPrizeTypeMO> mTypeMOs) throws SuperCodeException {
		// 执行中奖算法
		MarketingPrizeTypeMO mPrizeTypeMO = mTypeMOs.get((int) (Math.random() * mTypeMOs.size()));
		return mPrizeTypeMO;
	}

	public static void main(String[] args) {
    double n=31/100.00*100;
    System.out.println(n);
	}

}
