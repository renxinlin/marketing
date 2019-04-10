package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralExchangeMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IntegralExchangeMapperExt extends IntegralExchangeMapper, CommonSql {


    ////



    static String allFileds=" Id id, MemberType memberType, ExchangeResource exchangeResource, " +
            " ExchangeIntegral exchangeIntegral, ExchangeStock exchangeStock, HaveStock haveStock, CustomerLimitNum customerLimitNum, " +
            " Status status, PayWay payWay, UndercarriageSetWay undercarriageSetWay, UnderCarriage underCarriage, StockWarning stockWarning, " +
            " StockWarningNum stockWarningNum, OrganizationId organizationId, OrganizationName organizationName, ProductId productId, ProductName productName, " +
            " SkuName skuName, SkuUrl skuUrl, SkuStatus skuStatus";
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
                    // TODO TINYINT 需要修改
                    " ie.MemberType LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.ExchangeResource LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.ProductName LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.SkuName LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.ExchangeIntegral LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.ExchangeStock LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.HaveStock LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.CustomerLimitNum LIKE CONCAT('%',#{search},'%') " +
                    " OR ie.Status LIKE CONCAT('%',#{search},'%') " +

                    ")" +
                    "</if>" +
                    "</otherwise>" +
                    "</choose>" +
                    " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
                    "</where>";
    @Select(startScript
            + " select " +allFileds + " from  marketing_integral_exchange ie "
            + whereSearch
            + " ORDER BY Id DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + endScript)
    List<IntegralExchange> list(IntegralExchange searchParams);

    @Select(startScript
            + " select " + count + " from  marketing_integral_exchange ie "
            + whereSearch
             + endScript)
    int count(IntegralExchange searchParams);

    @Delete(startScript + "delete from marketing_integral_exchange ie where Id =  #{id} and OrganizationId = #{organizationId} " + endScript)
    int deleteByOrganizationId(@Param("id")Long id, @Param("organizationId")String organizationId);
    // TODO 待产品确认自动下架能否上架，目前可以【兑换活动状态0上架1手动下架2自动下架】
    @Update(startScript +" update marketing_integral_exchange ie set Status = #{status} where Id =  #{id} and OrganizationId = #{organizationId} " +
            " and Status != 0 "+ endScript)
    int updateStatusUp(IntegralExchange updateStatus);

    @Update(startScript +" update marketing_integral_exchange ie set Status = #{status} where Id =  #{id} and OrganizationId = #{organizationId} " +
            " and Status = 0 "+ endScript)
    int updateStatusLowwer(IntegralExchange updateStatus);

    // TODO 分组
    @Select(startScript
            + " select ProductId, ExchangeIntegral from marketing_integral_exchange ie  where OrganizationId = #{organizationId} and Status = 0 "
            + " group by ProductId, ExchangeIntegral "
            + endScript)
    List<IntegralExchange> getOrganizationExchange(@Param("organizationId") String organizationId);
}