package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRecordMapper;
import com.jgw.supercodeplatform.marketing.pojo.MarketingMembers;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface IntegralRecordMapperExt extends IntegralRecordMapper,CommonSql {
    static String allFileds=" Id id,MemberType memberType,MemberId memberId, " +
            " MemberName memberName,Mobile mobile,IntegralReasonCode integralReasonCode,IntegralReason integralReason, " +
            " ProductId productId,ProductName productName,OuterCodeId outerCodeId,CodeTypeId codeTypeId,CustomerName customerName, " +
            " CustomerId customerId,CreateDate createDate,OrganizationId organizationId,OrganizationName organizationName,IntegralNum integralNum,ProductPrice productPrice," +
			" SalerName,SalerId,SalerMobile,Status,SalerAmount ";

    static String whereSearch =
            "<where>" +
                    "<choose>" +
                    //当search为空时要么为高级搜索要么没有搜索暂时为不搜索
                    "<when test='search == null or search == &apos;&apos;'>" +
                    "</when>" +
                    //如果search不为空则普通搜索
                    "<otherwise>" +
                    "<if test='search !=null and search != &apos;&apos;'>" +
                    " AND (" +
                    // TODO TINYINT 是否可以这样
                    " ir.MemberType LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.MemberId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.MemberName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.Mobile LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralReasonCode LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralReason LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ProductId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ProductName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.OuterCodeId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CodeTypeId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CustomerName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CustomerId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralNum LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CreateDate LIKE binary  CONCAT('%',#{search},'%') " +
                    ")" +
                    "</if>" +
                    "</otherwise>" +
                    "</choose>" +
                    " <if test='organizationId != null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
                    " <if test='memberId != null and organizationId != &apos;&apos; '> and MemberId = #{memberId} </if>"+
                    // 大于
					" <if test='integralType != null and integralType == 0 '> and IntegralNum &gt; 0 </if>"+
					// 积分记录区分用户类别
					// TODO 评估MEMBERTYPE是否可以去除if条件
					" <if test='memberType != null '> and MemberType= #{memberType}  </if>"+
                    // 小于
                    " <if test='integralType != null and integralType == 1 '> and IntegralNum &lt;  0 </if>"+
                    "</where>";
    @Select(startScript
            + " select ir.Id id, ir.MemberType memberType,ir.MemberId memberId, "
            + " ir.MemberName memberName,ir.Mobile mobile,ir.IntegralReasonCode integralReasonCode,ir.IntegralReason integralReason, "
            + " ir.ProductId productId,ir.ProductName productName,ir.OuterCodeId outerCodeId,ir.CodeTypeId codeTypeId,ir.CustomerName customerName, "
            + " ir.CustomerId customerId,DATE_FORMAT(ir.CreateDate,'%Y-%m-%d %H:%i:%s') createDate,ir.OrganizationId organizationId,ir.OrganizationName organizationName,ir.IntegralNum integralNum "
            + " from  marketing_integral_record ir "

            +whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            +endScript)
    List<IntegralRecord> list(IntegralRecord integralRecord);

    @Select(startScript
            + " select  " +count + " from  marketing_integral_record ir "
            +whereSearch
              +endScript)
    int count(IntegralRecord integralRecord);

    @Select(startScript
            + " select ir.Id id, ir.MemberType memberType,ir.MemberId memberId, "
            + " ir.MemberName memberName,ir.Mobile mobile,ir.IntegralReasonCode integralReasonCode,ir.IntegralReason integralReason, "
            + " ir.ProductId productId,ir.ProductName productName,ir.OuterCodeId outerCodeId,ir.CodeTypeId codeTypeId,ir.CustomerName customerName, "
            + " ir.CustomerId customerId,DATE_FORMAT(ir.CreateDate,'%Y-%m-%d %H:%i:%s') createDate,ir.OrganizationId organizationId,ir.OrganizationName organizationName,ir.IntegralNum integralNum "
            + " from  marketing_integral_record ir "
            +startWhere
            +  " <if test='memberId != null '>ir.MemberId=#{memberId} </if>"
             +  " <if test='integralReasonCode != null '>and ir.IntegralReasonCode=#{integralReasonCode} </if>"
             +  " <if test='organizationId != null and organizationId != &apos;&apos; '>and ir.OrganizationId=#{organizationId} </if>"
            +endWhere
            +endScript)
	List<IntegralRecord> selectByMemberIdAndIntegralReasonCode(@Param("memberId")Long memberId, @Param("integralReasonCode")Integer integralReasonCode, @Param("organizationId")String organizationId);

    @Insert({startScript,
    	        "insert into marketing_integral_record (MemberType, ",
    	        "MemberId, MemberName, ",
    	        "Mobile, IntegralReasonCode, ",
    	        "IntegralReason, ProductId, ",
    	        "ProductName, OuterCodeId, ",
    	        "CodeTypeId, CustomerName, ",
    	        "CustomerId, CreateDate, ",
    	        "OrganizationId, OrganizationName, ",
    	        "IntegralNum)",
    	        "values ",
    	        " <foreach collection='list' item='item' index='index' separator=','>",
	    	        "( #{item.memberType,jdbcType=TINYINT}, ",
	    	        "#{item.memberId,jdbcType=BIGINT}, #{item.memberName,jdbcType=VARCHAR}, ",
	    	        "#{item.mobile,jdbcType=VARCHAR}, #{item.integralReasonCode,jdbcType=INTEGER}, ",
	    	        "#{item.integralReason,jdbcType=VARCHAR}, #{item.productId,jdbcType=VARCHAR}, ",
	    	        "#{item.productName,jdbcType=VARCHAR}, #{item.outerCodeId,jdbcType=VARCHAR}, ",
	    	        "#{item.codeTypeId,jdbcType=VARCHAR}, #{item.customerName,jdbcType=VARCHAR}, ",
	    	        "#{item.customerId,jdbcType=VARCHAR}, now(), ",
	    	        "#{item.organizationId,jdbcType=VARCHAR}, #{item.organizationName,jdbcType=VARCHAR}, ",
	    	        "#{item.integralNum,jdbcType=INTEGER})",
    	       " </foreach>",
    		endScript
    })
	void batchInsert(List<IntegralRecord> inRecords);

    @Update("update marketing_integral_record set MemberId=#{newMemberId} where MemberId=#{oldMemberId} ")
	void updateMemberId(@Param("oldMemberId")Long oldMemberId, @Param("newMemberId")Long newMemberId);

	/**
	 * 组织总发放的积分
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select(" select sum(IntegralNum) from marketing_integral_record where OrganizationId = #{organizationId} " +
			" and IntegralNum > 0  " +
			" and CreateDate between #{startDate} and #{endDate} ")
    Integer sumOrganizationUsingIntegralByDate(String organizationId, Date startDate, Date endDate);
	/**
	 * 组织总兑换金额
	 * @param organizationId
	 * @param date
	 * @param date1
	 */
	@Select(" select -sum(IntegralNum) from marketing_integral_record where OrganizationId = #{organizationId} " +
			" and IntegralNum < 0  " +
			" and CreateDate between #{startDate} and #{endDate} ")
	Integer sumOrganizationIntegralExchangeByDate(String organizationId, Date startDate, Date endDate);

	/**
	 * 获取top6产品的兑换消耗的积分； &lt 《
	 * 负数转证书
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select(" select ProductId,ProductName ,-sum(IntegralNum) IntegralNum from marketing_integral_record " +
			" where 1=1 " +
			" and OrganizationId = #{organizationId} " +
			" and IntegralNum < 0 " +
			" and CreateDate between #{startDate} and #{endDate} " +
 			" group by ProductId,ProductName " +
			" order by IntegralNum desc limit 0,6 ")
    List<IntegralRecord> getOrganizationTop6IntegralProduct(String organizationId, Date startDate, Date endDate);


	/**
	 *  兑换的积分 负数转正数
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Select(" select -sum(IntegralNum) IntegralNum from marketing_integral_record " +
			" where 1=1 " +
			" and OrganizationId = #{organizationId} " +
			" and IntegralNum < 0 " +
			" and CreateDate between #{startDate} and #{endDate} " )
	Integer getOrganizationAllIntegralProduct(String organizationId, Date startDate, Date endDate);

	/**
	 * IntegralReasonEnum PRODUCT_INTEGRAL 4  产品积分
	 * @param organizationId
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	@Select(" select DATE_FORMAT(CreateDate,'%Y-%m-%d') as createDateStr, ProductPrice from marketing_integral_record " +
			" where 1=1 " +
			" and OrganizationId = #{organizationId} " +
			" and IntegralReasonCode =  4 " +
			" and CreateDate between #{startDate} and #{endDate} " )
    List<IntegralRecord> getOrganizationAllSalePrice(String organizationId, Date startDate, Date endDate);


	@Select(" select count(SalerAmount) count, sum(SalerAmount) sum from marketing_integral_record where organizationId = #{organizationId} " +
			" and memberId = #{memberId}" +
			" and memberType = #{memberType} " +
			" and Status = 1" +
			" and SalerAmount is not null ")
    Map getAcquireMoneyAndAcquireNums(Long memberId, Byte memberType, String organizationId);
}