package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralOrderMapper;
import com.jgw.supercodeplatform.marketing.dto.integral.IntegralOrderPageParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IntegralOrderMapperExt extends IntegralOrderMapper, CommonSql {



    static String allFileds=" Id id, OrderId orderId, ExchangeResource exchangeResource, " +
            " ProductId productId, ProductName productName,  " +
            " SkuName skuName, SkuUrl skuUrl, ExchangeIntegralNum exchangeIntegralNum, " +
            " ExchangeNum exchangeNum, Name name, Mobile mobile, Address address, Status status, MemberId memberId, MemberName memberName, " +
            " CreateDate createDate, DeliveryDate deliveryDate, OrganizationId organizationId, OrganizationName organizationName ," +
            " SkuId skuId, ShowPrice showPrice, ShowPrice showPriceStr, ProductPic productPic";

    static String excelFileds=" Id id, OrderId orderId, ExchangeResource exchangeResource, " +
            " ProductId productId, ProductName productName,  " +
            " SkuName skuName, SkuUrl skuUrl, ExchangeIntegralNum exchangeIntegralNum, " +
            " ExchangeNum exchangeNum, Name name, Mobile mobile, Address address, " +
            // 获取状态位增加改为case wher
            " if (Status=0, '待发货','已发货' ) as statusName, " +
            " MemberId memberId, MemberName memberName, " +
            // 处理日期问题
            " date_format(CreateDate, '%Y-%m-%d %H:%i:%S') createDateStr, CreateDate createDate, " +
            "DeliveryDate deliveryDate, OrganizationId organizationId, OrganizationName organizationName ," +
            " SkuId skuId, ShowPrice showPrice, ShowPrice showPriceStr, ProductPic productPic";
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
                    " ir.OrderId LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ProductName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.SkuName LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ExchangeNum LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.ExchangeIntegralNum LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.Name LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.Mobile LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.Address LIKE CONCAT('%',#{search},'%') " +
                    " OR ir.CreateDate LIKE binary CONCAT('%',#{search},'%') " +
                    " OR ir.Status LIKE CONCAT('%',#{search},'%') " +

                    ")" +
                    "</if>" +
                    "</otherwise>" +
                    "</choose>" +
                    // 组织订单
                    " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
                    // H5订单
                    " <if test=' memberId !=null '> and MemberId = #{memberId} </if>"+
                     "</where>";

    @Select(startScript
            + " select " + count + " from  marketing_order ir "
            + whereSearch
            + endScript)
    int count(IntegralOrder searchParams);




    @Select(startScript
            + " select " +allFileds + " from  marketing_order ir "
            + whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + endScript)
    List<IntegralOrderPageParam> list(IntegralOrder searchParams);

    /**
     * excel分页,状态位改数据展示
     * @param searchParams
     * @return
     */
    @Select(startScript
            + " select " +excelFileds + " from  marketing_order ir "
            + whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            + endScript)
    List<IntegralOrderPageParam>  listWithExcel(IntegralOrder searchParams);
}