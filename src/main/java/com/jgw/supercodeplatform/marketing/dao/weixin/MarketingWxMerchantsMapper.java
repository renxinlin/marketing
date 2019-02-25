package com.jgw.supercodeplatform.marketing.dao.weixin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;

@Mapper
public interface MarketingWxMerchantsMapper {
	static String allFields="Id id,Mchid mchid,MchAppid mchAppid,MerchantName merchantName,MerchantKey merchantKey,CertificateAddress certificateAddress,"
			+ "OrganizationId organizationId,OrganizatioIdlName organizatioIdlName";
	
    @Select("select "+allFields+" from marketing_wx_merchants where OrganizationId=#{organizationId}")
	MarketingWxMerchants get(@Param("organizationId") String organizationId);

}
