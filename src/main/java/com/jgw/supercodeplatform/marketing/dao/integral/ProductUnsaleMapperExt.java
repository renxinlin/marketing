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
            " CreateUserName createUserName, CreateDate createDate, OrganizationId organizationId, OrganizationName organizationName";

    @Select(startScript+" select "+allFeilds +" from marketing_product_unsale pu where organizationId = #{organizationId}"+endScript)
    List<ProductUnsale> selectAll(@Param("organizationId") String organizationId);
}