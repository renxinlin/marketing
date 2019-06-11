package com.jgw.supercodeplatform.marketing.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingMemberCouponMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingCouponPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;

@Mapper
public interface MarketingMemberCouponMapperExt extends MarketingMemberCouponMapper {
	
	String allFields = "mmc.Id, mmc.MemberId memberId, mmc.CouponId couponId, mmc.CouponCode couponCode,mmc.CouponAmount couponAmount, mmc.MemberPhone memberPhone, mmc.ProductId productId, mmc.ProductBatchId productBatchId,"+
	        "mmc.ProductBatchName productBatchName, mmc.SbatchId sbatchId, mmc.ProductName productName, mmc.ObtainCustomerId obtainCustomerId, mmc.DeductionDate deductionDate, mmc.CreateTime createTime,"+
	        "mmc.ObtainCustmerName obtainCustmerName,mmc.VerifyCustomerId verifyCustomerId, mmc.VerifyCustomerName verifyCustomerName, mmc.VerifyPersonName verifyPersonName, mmc.VerifyPersonPhone verifyPersonPhone,"+
	        "mmc.VerifyTime verifyTime,mmc.VerifyPersonType verifyPersonType, mmc.Used used ";
	String whereSearch = "<where>"+
			"<if test='organizationId !=null and organizationId != &apos;&apos; '> AND mas.OrganizationId = #{organizationId}</if>"+
			"<if test='activitySetId !=null and activitySetId != &apos;&apos; '> AND mas.ActivitySetId = #{activitySetId}</if>"+
			"<if test='used !=null'> AND mmc.Used = #{used}</if>"+
			"<if test='search !=null and search != &apos;&apos;'>"+
			"AND ("+
			" mmc.couponCode LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.memberPhone LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.productName LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.obtainCustmerName LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.verifyCustomerName LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.verifyPersonName LIKE CONCAT('%',#{search},'%') "+
			" OR mmc.verifyPersonPhone LIKE CONCAT('%',#{search},'%') "+
			")</if></where>";
	
	@Select({"<script>",
		"select",allFields,
		"from marketing_member_coupon mmc inner join marketing_activity_set mas ",
		"on mmc.CouponId = mas.Id ",
		whereSearch,
		"<if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>",
	"</script>"})
	List<MarketingMemberCoupon> list(MarketingCouponPageParam marketingCouponPageParam);

	@Select({"<script>",
		"select count(*) ",
		"from marketing_member_coupon mmc inner join marketing_activity_set mas ",
		"on mmc.CouponId = mas.Id ",
		whereSearch,
	"</script>"})
	int count(MarketingCouponPageParam marketingCouponPageParam);
	
	@Select({"<script>",
		"select",allFields,
		"from marketing_member_coupon mmc inner join marketing_activity_set mas ",
		"on mmc.CouponId = mas.Id ",
		whereSearch,
	"</script>"})
	List<MarketingMemberCoupon> searchVerfiyList(MarketingCouponPageParam marketingCouponPageParam);
}
