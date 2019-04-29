package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRecordMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IntegralRecordMapperExt extends IntegralRecordMapper,CommonSql {
    static String allFileds=" Id id,MemberType memberType,MemberId memberId, " +
            " MemberName memberName,Mobile mobile,IntegralReasonCode integralReasonCode,IntegralReason integralReason, " +
            " ProductId productId,ProductName productName,OuterCodeId outerCodeId,CodeTypeId codeTypeId,CustomerName customerName, " +
            " CustomerId customerId,CreateDate createDate,OrganizationId organizationId,OrganizationName organizationName,IntegralNum integralNum ";

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
}