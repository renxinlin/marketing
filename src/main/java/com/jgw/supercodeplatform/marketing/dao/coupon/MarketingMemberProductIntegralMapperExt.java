package com.jgw.supercodeplatform.marketing.dao.coupon;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingMemberProductIntegralMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberProductIntegral;

@Mapper
public interface MarketingMemberProductIntegralMapperExt extends MarketingMemberProductIntegralMapper {

	String allFileds = "Id id, MemberId memberId,"+
	        "ProductId productId, ProductBatchId productBatchId,"+
	        "SbatchId sbatchId, AccrueIntegral accrueIntegral,"+
	        "OrganizationId organizationId, OrganizationName organizationName,"+
	        "CreateTime createTime, UpdateTime updateTime ";
	
	@Select({"select",allFileds,"from marketing_member_product_integral where MemberId = #{memberId} and ProductId = #{productId}"})
	public MarketingMemberProductIntegral selectProductIntegralByMemberIdAndProductId(@Param("memberId")Long memberId, @Param("productId")String productId);
	
	
}
