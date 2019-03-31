package com.jgw.supercodeplatform;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.activity.MarketingPrizeTypeMO;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;
import com.jgw.supercodeplatform.marketing.common.util.LotteryUtil;
import com.jgw.supercodeplatform.marketing.dao.activity.MarketingPrizeTypeMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizeType;
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringBootTest(classes = SuperCodeMarketingApplication.class) // 指定我们SpringBoot工程的Application启动类
public class LotteryUtilTest {
	@Autowired
	private MarketingPrizeTypeMapper mTypeMapper;
	
	private static long totalCodeNum = 100;

	@Test
	public void test() throws SuperCodeException {
		long first=0;
		long sec=0;
		long third=0;
		long four=0;
		long sum=totalCodeNum;
		while(sum-->0) {
			List<MarketingPrizeType> list=mTypeMapper.selectByActivitySetIdIncludeUnreal(244L);
			List<MarketingPrizeTypeMO> moTypeMOs=LotteryUtil.judge(list, totalCodeNum);
			System.out.println("判断后奖次总数："+moTypeMOs.size());
			MarketingPrizeTypeMO marketingPrizeTypeMO=LotteryUtil.lottery(moTypeMOs);
			switch (marketingPrizeTypeMO.getPrizeTypeName()) {
			case "一等奖":
				first++;
				marketingPrizeTypeMO.setWiningNum(first);
				break;
			case "二等奖":
				sec++;
				marketingPrizeTypeMO.setWiningNum(sec);
				break;
			case "三等奖":
				third++;
				marketingPrizeTypeMO.setWiningNum(third);
				break;
			case "未中奖":
				four++;
				marketingPrizeTypeMO.setWiningNum(four);
				break;
			default:
				throw new SuperCodeException("无此奖次sum="+sum, 500);
			}
			MarketingPrizeType marketingPrizeType =new MarketingPrizeType();
			marketingPrizeType.setId(marketingPrizeTypeMO.getId());
			marketingPrizeType.setWiningNum((marketingPrizeTypeMO.getWiningNum()==null?0L:marketingPrizeTypeMO.getWiningNum())+1);
			mTypeMapper.update(marketingPrizeType);
		}
		System.out.println("一等奖："+first+"，二等奖："+sec+"，三等奖："+third+"，未中奖："+four);
		
	}
	
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

	public static void main(String[] args) throws SuperCodeException, NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		InputStream certStream=new FileInputStream("H:\\test\\cert\\113a2c2ef0cb438a84a69114c35b9a00\\CEEED.p12");
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(certStream, "1269223601".toCharArray());
        

//		String suffix="P12";
//		String prefix = "CEEED";
//		FileOutputStream fileOutputStream=null;
//		String newName=prefix+"."+suffix;
//		try {
//			String wholeFilePath="H:\\test\\cert\\113a2c2ef0cb438a84a69114c35b9a00";
//			File dir=new File(wholeFilePath);
//			if (!dir.exists()) {
//				dir.mkdirs();
//			}
//			String wholeName=wholeFilePath+File.separator+newName;
//			
//			fileOutputStream=new FileOutputStream(wholeName);
//			byte[]buf=new byte[1024];
//			int length=0;
//			while((length=certStream.read(buf))>0) {
//				fileOutputStream.write(buf, 0, length);
//			}
//			fileOutputStream.flush();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			if (null!=certStream) {
//				certStream.close();
//			}
//			
//			if (null!=fileOutputStream) {
//				fileOutputStream.close();
//			}
//		}
	}

}
