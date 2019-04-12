package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.ProductUnsaleMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.ProductUnsale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductUnsaleMapperExt  extends ProductUnsaleMapper, CommonSql {

    static String allFeilds = " Id id, ProductId productId, UnsaleProductName unsaleProductName, UnsaleProductPic unsaleProductPic, " +
            " UnsaleProductSkuNum unsaleProductSkuNum, UnsaleProductSkuInfo unsaleProductSkuInfo, ShowPrice showPrice, RealPrice realPrice, " +
            " Detail detail, UpdateUserId updateUserId, UpdateUserName updateUserName, UpdateDate updateDate, CreateUserId createUserId, " +
            " CreateUserName createUserName, CreateDate createDate, OrganizationId organizationId, OrganizationName organizationName, showPrice showPriceStr";


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
                    // TODO TINYINT 这种模式不支持
                    " mpu.UnsaleProductName LIKE CONCAT('%',#{search},'%') " +
                    " OR mpu.UnsaleProductSkuInfo LIKE CONCAT('%',#{search},'%') " +
                    " OR mpu.UpdateUserName LIKE CONCAT('%',#{search},'%') " +
                    " OR mpu.UpdateDate LIKE CONCAT('%',#{search},'%') " +
                    ")" +
                    "</if>" +
                    "</otherwise>" +
                    "</choose>" +
                    " <if test='organizationId !=null and organizationId != &apos;&apos; '> and OrganizationId = #{organizationId} </if>"+
                    "</where>";

    @Select(startScript+" select " +allFeilds + " from marketing_product_unsale mpu where organizationId = #{organizationId}"+endScript)
    List<ProductUnsale> selectAll(@Param("organizationId") String organizationId);

    @Select(startScript + " select "+ allFeilds + " from  marketing_product_unsale mpu "
            +whereSearch
            + " ORDER BY CreateDate DESC"
            + " <if test='startNumber != null and pageSize != null and pageSize != 0'> LIMIT #{startNumber},#{pageSize}</if>"
            +endScript)
    List<ProductUnsale> list(ProductUnsale searchParams);

    @Select(startScript
            + " select  " +count + " from  marketing_product_unsale mpu "
            +whereSearch
             +endScript)
    int count(ProductUnsale searchParams);

    @Select(startScript+" select " +allFeilds + " from marketing_product_unsale mpu where mpu.ProductId = #{productId}"+endScript)
    ProductUnsale selectByProductId(@Param("productId")  String productId);
}