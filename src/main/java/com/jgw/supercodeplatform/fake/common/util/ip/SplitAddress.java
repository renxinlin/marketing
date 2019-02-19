package com.jgw.supercodeplatform.fake.common.util.ip;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SplitAddress {
	
	private static Logger logger = LoggerFactory.getLogger(SplitAddress.class);
	
	private static final String DEV_IP_PATH = "/usr/local/file/file-zcy/qqwry/qqwry.dat";
	
	public void SplitAddressAction(String ipaddress, IPSeeker ipseeker, IPEntity ipentity){
		try {			
			String alladdress = ipseeker.getCountry(ipaddress);	
			String[] part;
			//全国省市里唯一没有"市"字样的只有这4个省,直接作逗号","切分
			if(alladdress.startsWith("新疆")){
				ipentity.setProvince("新疆");
				alladdress = alladdress.replace("新疆", "新疆,");
			}
			else if(alladdress.startsWith("西藏")){
				ipentity.setProvince("西藏");
				alladdress = alladdress.replace("西藏", "西藏,");
			}
			else if(alladdress.startsWith("内蒙古")){
				ipentity.setProvince("内蒙古");
				alladdress = alladdress.replace("内蒙古", "内蒙古,");
			}
			else if(alladdress.startsWith("宁夏")){
				ipentity.setProvince("宁夏");
				alladdress = alladdress.replace("宁夏", "宁夏,");
			}
			alladdress = alladdress.replaceAll("省", "省,").replaceAll("市", "市,"); //最多切成3段:辽宁省,盘锦市,双台子区;
			part = alladdress.split(",");
			
			if(part.length==1){
				//只有1级地址
				ipentity.setNation(part[0]);
				ipentity.setProvince(part[0]);
			}
			else if(part.length==2){
				//有2级地址
				ipentity.setProvince(part[0]);
				ipentity.setCity(part[1]);
			}
			else if(part.length==3){
				//有3级地址
				ipentity.setProvince(part[0]);
				ipentity.setCity(part[1]);
				ipentity.setRegion(part[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据IP地址获取地区名称
	 * @param ipaddress
	 * @param ipseeker
	 * @return
	 * @throws Exception 
	 */
	public static IPEntity getPlaceFromIp(String ipaddress) throws Exception{
		logger.info("获取服务器的纯真IP目录:==============");
		IPSeeker ipseeker = new IPSeeker(new File(DEV_IP_PATH));//DEV_IP_PATH  LOCAL_IP_PATH
		logger.info("获取服务器的纯真IP目录结束:==============");
		IPEntity ipentity = new IPEntity();
		String alladdress = ipseeker.getCountry(ipaddress);	
		
		String[] part;
		alladdress = alladdress.replaceAll("省", "省,").replaceAll("市", "市,").replaceAll("县", "县"); //最多切成3段:辽宁省,盘锦市,双台子区;
		part = alladdress.split(",");
		if(part.length==1){
			//只有1级地址
			ipentity.setNation(part[0]);
			ipentity.setProvince(part[0]);
		}
		else if(part.length==2){
			//有2级地址
			ipentity.setProvince(part[0]);
			ipentity.setCity(part[1]);
		}
		else if(part.length==3){
			//有3级地址
			ipentity.setProvince(part[0]);
			ipentity.setCity(part[1]);
			ipentity.setRegion(part[2]);
		}
		return ipentity;
	}
	
	//223.104.246.140   115.238.42.146
	public static void main(String[] args) throws Exception {
		System.out.println(getPlaceFromIp("115.238.42.146").toString());
	}


}
