package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralExchangeMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.ExchangeProductParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralExchangeDetailParam;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralExchangeParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralExchange;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface IntegralExchangeMapperExt extends IntegralExchangeMapper, CommonSql {


    ////



    static String allFileds=" Id id, MemberType memberType, ExchangeResource exchangeResource, " +
            " ExchangeIntegral exchangeIntegral, ExchangeStock exchangeStock, HaveStock haveStock, CustomerLimitNum customerLimitNum, " +
            " Status status, PayWay payWay, UndercarriageSetWay undercarriageSetWay, UnderCarriage underCarriage, StockWarning stockWarning, " +
            " StockWarningNum stockWarningNum, OrganizationId organizationId, OrganizationName organizationName, ProductId productId, ProductName productName, " +
            " SkuId skuId, SkuName skuName, SkuUrl skuUrl, SkuStatus skuStatus, ProductPic productPic, ShowPrice showPrice ";
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

    @Delete(startScript + "delete from marketing_integral_exchange where Id =  #{id} and OrganizationId = #{organizationId} " + endScript)
    int deleteByOrganizationId(@Param("id")Long id, @Param("organizationId")String organizationId);

     @Update(startScript +" update marketing_integral_exchange ie set Status = #{status} where Id =  #{id} and OrganizationId = #{organizationId} " +
            " and Status != 3 "+ endScript)
    int updateStatusUp(IntegralExchange updateStatus);

    @Update(startScript +" update marketing_integral_exchange ie set Status = #{status} where Id =  #{id} and OrganizationId = #{organizationId} " +
            " and Status = 3 "+ endScript)
    int updateStatusLowwer(IntegralExchange updateStatus);

    /**
     * 获取组织的兑换产品，产品不可重复
     * @param organizationId
     * @return
     */
    @Select(startScript
            + " select ProductId productId, ProductName productName, ProductPic productPic, ExchangeIntegral exchangeIntegral, ShowPrice showPriceStr from marketing_integral_exchange ie  where OrganizationId = #{organizationId} and Status = 3 "
            + " group by ProductId,ProductName, ProductPic, ExchangeIntegral, ShowPrice order by id desc "
            + endScript)
    List<IntegralExchangeParam> getOrganizationExchange(@Param("organizationId") String organizationId);

    // 没有匹配的详情信息去基础数据查询
    @Select(startScript +
            " select  ProductId productId, ProductName productName, ProductPic productPic, ExchangeIntegral exchangeIntegral,ShowPrice showPriceStr, " +
            " ExchangeResource exchangeResource, PayWay payWay, SkuStatus skuStatus, HaveStock haveStock  " +
            " from marketing_integral_exchange ie " +
            " where ie.ProductId = #{productId} " +
            endScript)
    List<IntegralExchangeDetailParam> selectH5ById(@Param("productId") String productId);



    @Select(startScript + " select " + allFileds + " from marketing_integral_exchange ie where ie.ProductId = #{productId} " +
            " <if test='skuId != null and skuId != &apos;&apos;'> and skuId = #{skuId} </if>  " +  endScript)
    List<IntegralExchange> selectByProductId(@Param("productId") String productId,@Param("skuId") String skuId);



    @Select(startScript + "select " + allFileds + " from marketing_integral_exchange ie where ie.ProductId = #{productId}  and ie.Status = 3" + endScript)
    List<IntegralExchange> selectH5ByIdFirst(@Param("productId") String productId);





    @Select(startScript + " select " + allFileds + " from marketing_integral_exchange ie where OrganizationId = #{organizationId} " +
            " and ProductId = #{productId} " +
            " <if test='skuId != null and skuId != &apos;&apos;'> and skuId = #{skuId} </if>  for update" +endScript )
    IntegralExchange exists(@Param("organizationId") String organizationId, @Param("productId") String productId,@Param("skuId") String skuId);








    // 保证数据一致性
    @Update(startScript +
            " update marketing_integral_exchange ie set HaveStock = HaveStock - #{exchangeNum} where HaveStock- #{exchangeNum} >= 0 and " +
            " productId = #{productId} " +
            " <if test='skuId != null and skuId != &apos;&apos;'> and skuId = #{skuId} </if> " +endScript )
    int reduceStock(ExchangeProductParam exchangeProductParam);

    @Insert(startScript +
            " insert into marketing_integral_exchange (MemberType,ExchangeResource,ExchangeIntegral,ExchangeStock, " +
            " HaveStock,CustomerLimitNum,Status,PayWay,UndercarriageSetWay,UnderCarriage,StockWarning,StockWarningNum, " +
            " OrganizationId,OrganizationName,ProductId,ProductName,SkuId,SkuName,SkuUrl,SkuStatus,ProductPic,ShowPrice) values " +
            " <foreach collection='list' item='item' index='index' separator=','> " +
            " (" +
            " #{item.memberType}, " +
            " #{item.exchangeResource}, " +
            " #{item.exchangeIntegral}, " +
            " #{item.exchangeStock}," +
            " #{item.haveStock}, " +
            " #{item.customerLimitNum}, " +
            " #{item.status}, " +
            " #{item.payWay}, " +
            " #{item.undercarriageSetWay}, " +
            " #{item.underCarriage}, " +
            " #{item.stockWarning}, " +
            " #{item.stockWarningNum}, " +
            " #{item.organizationId}, " +
            " #{item.organizationName}, " +
            " #{item.productId}, " +
            " #{item.productName}, " +
            " #{item.skuId}, " +
            " #{item.skuName}, " +
            " #{item.skuUrl}, " +
            " #{item.skuStatus}, " +
            " #{item.productPic}, " +
            " #{item.showPrice}" +
            " ) " +
            " </foreach> "+
            endScript)
    int insertBatch(@Param("list") List<IntegralExchange> list);



    @Select(startScript+" select " +allFileds + " from marketing_integral_exchange where ProductId " +
            " in <foreach collection='array' item='productId' index='index' open='(' close=')' separator=','> #{productId} </foreach>" +endScript)
    List<IntegralExchange> having(String[] productIds);

    /**
     * 获取需要下架
     * @return
     */
    @Select("select " + allFileds + " from marketing_integral_exchange ie where ie.Status = 3 and ie.HaveStock = 0 and  DATE_FORMAT(UnderCarriage , '%Y-%m-%d') =  DATE_FORMAT(now(), '%Y-%m-%d') ")
    List<IntegralExchange>  getNeedOffExchange();

   // 自动下架
    @Update( "update  marketing_integral_exchange ie  set ie.Status = 2 where ie.Id in (" +
            "<foreach collection='list' item='item' index='index' open='' close=''  separator=','>#{item.id}</foreach>" +
            ") ")
    int undercarriage(List<IntegralExchange> readingToDb);

    /**
     * 查询自卖产品;0非自卖1自卖产品
     * @param organizationId
     * @return
     */
    @Select(" select ProductId, SkuId, SkuStatus from marketing_integral_exchange ie where ie.OrganizationId=#{organizationId} and ie.ExchangeResource = 1 ")
    Set<IntegralExchange> selectSalePruduct(@Param("organizationId") String organizationId);
    /**
     * 查询非自卖产品;0非自卖1自卖产品
     * @param organizationId
     * @return
     */
    @Select(" select ProductId, SkuId, SkuStatus from marketing_integral_exchange ie where ie.OrganizationId=#{organizationId} and ie.ExchangeResource = 0 ")
    Set<IntegralExchange> selectUnSalePruduct(@Param("organizationId") String organizationId);
}