package com.jgw.supercodeplatform.marketing.dao.coupon;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.MarketingMemberCouponMapper;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingCouponPageParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponCustmerVerifyPageParam;
import com.jgw.supercodeplatform.marketing.dto.coupon.CouponPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberCoupon;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponPageVo;
import com.jgw.supercodeplatform.marketing.vo.coupon.CouponVerifyVo;

@Mapper
public interface MarketingMemberCouponMapperExt extends MarketingMemberCouponMapper {
	
	String allFields = "mmc.Id, mmc.MemberId memberId, mmc.CouponId couponId, mmc.CouponCode couponCode,mmc.CouponAmount couponAmount, mmc.MemberPhone memberPhone, mmc.ProductId productId, mmc.ProductBatchId productBatchId,"+
	        "mmc.ProductBatchName productBatchName, mmc.SbatchId sbatchId, mmc.ProductName productName, mmc.ObtainCustomerId obtainCustomerId, mmc.DeductionStartDate deductionStartDate, mmc.DeductionEndDate deductionEndDate,"+
	        "mmc.CreateTime createTime,mmc.ObtainCustmerName obtainCustmerName,mmc.VerifyCustomerId verifyCustomerId, mmc.VerifyCustomerName verifyCustomerName,mmc.VerifyMemberId verifyMemberId, mmc.VerifyPersonName verifyPersonName,"+
	        "mmc.VerifyPersonPhone verifyPersonPhone,mmc.VerifyTime verifyTime,mmc.VerifyPersonType verifyPersonType, mmc.Used used ";
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

	@Select({"<script>",
		"select Id id,CouponCode couponCode, CouponAmount couponAmount,DeductionStartDate deductionStartDate, DeductionEndDate, deductionEndDate ",
		"from marketing_member_coupon ",
		"<where> MemberId = #{memberId}",
		"<if test='useType == 1'> and Used = 0 and DeductionEndDate gt now()</if>",
		"<if test='useType == 2'> and Used = 1</if>",
		"<if test='useType == 3'> and Used = 0 and DeductionEndDate lte now() </if>",
		"</where>",
		"<if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>",
		"</script>"})
	List<CouponPageVo> listCoupon(CouponPageParam couponPageParam);
	
	@Select({"<script>",
		"select count(*) ",
		"from marketing_member_coupon",
		"<where> MemberId = #{memberId}",
		"<if test='useType == 1'> and Used = 0 and DeductionEndDate gt now()</if>",
		"<if test='useType == 2'> and Used = 1</if>",
		"<if test='useType == 3'> and Used = 0 and DeductionEndDate lte now() </if>",
		"</where>",
		"</script>"})
	int couponCount(CouponPageParam couponPageParam);
	
	@Select({"select",allFields,"from marketing_member_coupon mmc where CouponCode = #{couponCode}"})
	MarketingMemberCoupon getMarketingMemberCouponByCouponCode(@Param("couponCode") String couponCode);
	
	@Select({"select CouponAmount couponAmount,CouponCode couponCode,VerifyTime verifyTime from marketing_member_coupon ",
		"where VerifyMemberId = #{verifyMemberId} and VerifyCustomerId = #{verifyCustomerId} ",
		"order by VerifyTime desc LIMIT #{startNumber},#{pageSize}"})
	List<CouponVerifyVo> listCustomerVerifyCoupon(CouponCustmerVerifyPageParam searchParams);
	
	@Select("select count(*) from marketing_member_coupon where VerifyMemberId = #{verifyMemberId} and VerifyCustomerId = #{verifyCustomerId}")
	int CustomerVerifyCouponCout(CouponCustmerVerifyPageParam searchParams);
	
	
}
