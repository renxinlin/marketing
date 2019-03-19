package com.jgw.supercodeplatform;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtil;

public class LotteryUtilTest {
	private static long totalCodeNum = 125485;

	public static List<MarketingPrizeTypeMO> init() throws SuperCodeException {
		List<MarketingPrizeTypeMO> marketingPrizeTypeMOs = new ArrayList<MarketingPrizeTypeMO>();
		MarketingPrizeTypeMO m1 = new MarketingPrizeTypeMO();
		m1.setPrizeProbability(15);
		m1.setPrizeTypeName("一等奖");

		MarketingPrizeTypeMO m2 = new MarketingPrizeTypeMO();
		m2.setPrizeProbability(26);
		m2.setPrizeTypeName("二等奖");

		MarketingPrizeTypeMO m3 = new MarketingPrizeTypeMO();
		m3.setPrizeProbability(70);
		m3.setPrizeTypeName("未中奖");

		marketingPrizeTypeMOs.add(m1);
		marketingPrizeTypeMOs.add(m2);
		marketingPrizeTypeMOs.add(m3);
		return marketingPrizeTypeMOs;
	}

	public static void main(String[] args) throws SuperCodeException, UnsupportedEncodingException {
		
		String n="%E6%B4%BB%E5%8A%A8%E5%B7%B2%E5%81%9C%E6%AD%A2";
		String nn=URLDecoder.decode(n, "utf-8");
		String nnn=URLDecoder.decode(nn, "utf-8");
		System.out.println(nnn);
		List<MarketingPrizeTypeMO> marketingPrizeTypeMOs = init();
		long sum=0;
		int i=0;
		long codeTotalNum=125485;
		for (MarketingPrizeTypeMO mTypeMO : marketingPrizeTypeMOs) {
			if (i==marketingPrizeTypeMOs.size()-1) {
				mTypeMO.setTotalNum(codeTotalNum);
			}else {
				long num = (long) (mTypeMO.getPrizeProbability() / 100.00 * codeTotalNum);
				mTypeMO.setTotalNum(num);
				codeTotalNum=codeTotalNum-num;
				sum+=num;
				mTypeMO.setTotalNum(num);
			}
			i++;
			System.out.println("第i="+i+"次，中奖奖次：" + mTypeMO.getPrizeTypeName()+",中奖总数="+mTypeMO.getTotalNum());
		}
//		for (int j = 0; j < 10; j++) {
//			List<MarketingPrizeTypeMO> marketingPrizeTypeMOs = init();
//			for (MarketingPrizeTypeMO mTypeMO : marketingPrizeTypeMOs) {
//				long num = (long) (mTypeMO.getPrizeProbability() / 100.00 * totalCodeNum);
//				mTypeMO.setTotalNum(num);
//			}
//			for (int i = 0; i < totalCodeNum; i++) {
//				MarketingPrizeTypeMO mPrizeTypeMO = LotteryUtil.lottery(marketingPrizeTypeMOs);
//				while (mPrizeTypeMO.getTotalNum() <= mPrizeTypeMO.getWiningNum()) {
//					mPrizeTypeMO = LotteryUtil.lottery(marketingPrizeTypeMOs);
//				}
//				mPrizeTypeMO.setWiningNum(mPrizeTypeMO.getWiningNum() + 1);
//				System.out.println("中奖奖次：" + mPrizeTypeMO.getPrizeTypeName());
//			}
//			System.out.println("-------------第 " + j + "次执行------------------------------");
//		}
	}

}
