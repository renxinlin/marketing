package com.jgw.supercodeplatform.marketing.dao.weixin;

import com.jgw.supercodeplatform.marketing.dto.activity.MarketingWxMerchantsParam;
import org.apache.ibatis.annotations.*;

import com.jgw.supercodeplatform.marketing.pojo.MarketingWxMerchants;

@Mapper
public interface MarketingWxMerchantsMapper {
	static String allFields="Id id,Mchid mchid,MchAppid mchAppid,MerchantName merchantName,MerchantKey merchantKey,CertificateAddress certificateAddress,"
			+ "OrganizationId organizationId,OrganizatioIdlName organizatioIdlName,MerchantSecret merchantSecret";
	
    @Select("select "+allFields+" from marketing_wx_merchants where OrganizationId=#{organizationId}")
	MarketingWxMerchants get(@Param("organizationId") String organizationId);

	@Insert(" INSERT INTO marketing_wx_merchants(Mchid,MchAppid,MerchantName,MerchantKey,"
			+ " CertificateAddress,CertificatePassword,OrganizationId,OrganizatioIdlName,MerchantSecret) "
			+ " VALUES(#{mchid},#{mchAppid},#{merchantName},#{merchantKey},#{certificateAddress},"
			+ "#{certificatePassword},#{organizationId},#{organizatioIdlName},#{merchantSecret}"
			+ ")")
	int addWxMerchants(MarketingWxMerchantsParam marketingWxMerchantsParam);

	@Update(" <script>"
			+ " UPDATE marketing_wx_merchants "
			+ " <set>"
			+ " <if test='mchid !=null and mchid != &apos;&apos; '> Mchid= #{mchid} ,</if> "
			+ " <if test='mchAppid !=null and mchAppid != &apos;&apos; '> MchAppid = #{mchAppid} ,</if> "
			+ " <if test='merchantName !=null and merchantName != &apos;&apos; '> MerchantName = #{merchantName} ,</if> "
			+ " <if test='merchantKey !=null and merchantKey != &apos;&apos; '> MerchantKey = #{merchantKey} ,</if> "
			+ " <if test='certificateAddress !=null and certificateAddress != &apos;&apos; '> CertificateAddress = #{certificateAddress} ,</if> "
			+ " <if test='certificatePassword !=null and certificatePassword != &apos;&apos; '> CertificatePassword = #{certificatePassword} ,</if> "
			+ " <if test='merchantSecret !=null and merchantSecret != &apos;&apos; '> MerchantSecret = #{merchantSecret} ,</if> "
			//+ " <if test='organizationId !=null and organizationId != &apos;&apos; '> OrganizationId = #{organizationId} ,</if> "
			//+ " <if test='organizatioIdlName !=null and organizatioIdlName != &apos;&apos; '> OrganizatioIdlName = #{organizatioIdlName} ,</if> "
			+ " </set>"
			+ " <where> "
			+ " <if test='id !=null and id != &apos;&apos; '> and Id = #{id} </if>"
			+ " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"
			+ " </where>"
			+ " </script>")
	int updateWxMerchants(MarketingWxMerchantsParam marketingWxMerchantsParam);

	@Select("select "+allFields+" from marketing_wx_merchants where OrganizationId=#{organizationId}")
	MarketingWxMerchants selectByOrganizationId(@Param("organizationId")String organizationId);



}
