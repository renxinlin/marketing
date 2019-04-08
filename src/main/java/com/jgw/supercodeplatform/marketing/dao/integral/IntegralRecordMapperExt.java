package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRecordMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IntegralRecordMapperExt extends IntegralRecordMapper,CommonSql {
    static String allFileds=" Id id,CustomerType customerType,CustomerId customerId, " +
            " CustomerName customerName,Mobile mobile,IntegralReasonCode integralReasonCode,IntegralReason integralReason, " +
            " ProductId productId,ProductName productName,OuterCodeId outerCodeId,CodeTypeId codeTypeId,RegisterStore registerStore, " +
            " RegisterStoreId registerStoreId,CreateDate createDate,OrganizationId organizationId,OrganizationName organizationName,IntegralNum integralNum ";

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
                    " ir.CustomerType LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CustomerId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CustomerName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.Mobile LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralReasonCode LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralReason LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ProductId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ProductName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.OuterCodeId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CodeTypeId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.RegisterStore LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.RegisterStoreId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.IntegralNum LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CreateDate LIKE binary  CONCAT('%',#{search},'%') " +
                    ")" +
                    "</if>" +
                    "</otherwise>" +
                    "</choose>" +
                    " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
                    "</where>";
    @Select(startScript
            + " select ir.Id id, ir.CustomerType customerType,ir.CustomerId customerId, "
            + " ir.CustomerName customerName,ir.Mobile mobile,ir.IntegralReasonCode integralReasonCode,ir.IntegralReason integralReason, "
            + " ir.ProductId productId,ir.ProductName productName,ir.OuterCodeId outerCodeId,ir.CodeTypeId codeTypeId,ir.RegisterStore registerStore, "
            + " ir.RegisterStoreId registerStoreId,ir.CreateDate createDate,ir.OrganizationId organizationId,ir.OrganizationName organizationName,ir.IntegralNum integralNum "
            + " from  marketing_integral_record ir "

            +whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            +endScript)
    List<IntegralRecord> list(IntegralRecord integralRecord);

    @Select(startScript
            + " select  " +count + " from  marketing_integral_record ir "
            +whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            +endScript)
    int count(IntegralRecord integralRecord);
}