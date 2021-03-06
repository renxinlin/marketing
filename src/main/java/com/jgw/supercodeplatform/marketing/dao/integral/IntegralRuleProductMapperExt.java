package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRuleProductMapper;
import com.jgw.supercodeplatform.marketing.dto.DaoSearchWithOrganizationIdParam;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface  IntegralRuleProductMapperExt extends IntegralRuleProductMapper,CommonSql {

	
    @Select(startScript
       + "select"
       +" Id, IntegralRuleId, ProductId, ProductName, ProductPrice, MemberType, RewardRule, "
       + " PerConsume, RewardIntegral,OrganizationId"
       +" from marketing_integral_rule_product"
       +startWhere
       +"<if test='organizationId!=null and organizationId!=&apos;&apos;'> OrganizationId=#{organizationId}</if>"
       +"<if test='search != null and search != &apos;&apos;'> AND ProductName LIKE CONCAT('%',#{search},'%') </if>"
       +endWhere
       +" order by Id desc"
       +page
       +endScript
    )
	List<IntegralRuleProduct> list(DaoSearchWithOrganizationIdParam searchParams);

    @Select(startScript
    	       + "select"
    	       +" count(*)"
    	       +" from marketing_integral_rule_product"
    	       +startWhere
    	       +"<if test='organizationId!=null and organizationId!=&apos;&apos;'> OrganizationId=#{organizationId}</if>"
				+"<if test='search != null and search != &apos;&apos;'> AND ProductName LIKE CONCAT('%',#{search},'%') </if>"
    	       +endWhere
    	       +endScript
    	    )
	int count(DaoSearchWithOrganizationIdParam searchParams);

    @Delete(startScript
    		+ "delete from marketing_integral_rule_product where ProductId in"
    		+ " <foreach item='item' collection='list' open='(' separator=',' close=')'>" 
    		+ "   #{item,jdbcType=VARCHAR}" 
    		+ " </foreach>"
    		+endScript
    		)
	void deleteByProductIds(List<String> list);

    
    @Insert(startScript
        +"insert into marketing_integral_rule_product (IntegralRuleId, "
        +"ProductId, ProductName, "
        +"ProductPrice, MemberType, "
        +"RewardRule, PerConsume, "
        +"RewardIntegral, OrganizationId)"
        +"values"
        +"<foreach collection='list' item='item' index='index' separator=','>"  
	        +"(#{item.integralRuleId,jdbcType=INTEGER},#{item.productId,jdbcType=VARCHAR}, #{item.productName,jdbcType=VARCHAR}, "
	        +"#{item.productPrice,jdbcType=REAL}, #{item.memberType,jdbcType=BIT}, "
	        +"#{item.rewardRule,jdbcType=BIT}, #{item.perConsume,jdbcType=REAL}, "
	        +"#{item.rewardIntegral,jdbcType=INTEGER}, #{item.organizationId,jdbcType=VARCHAR})"
        + "</foreach>"
        +endScript
    )
	void batchInsert(List<IntegralRuleProduct> ruleProducts);

    @Select({
        "select",
        "Id, IntegralRuleId, ProductId, ProductName, ProductPrice, MemberType, RewardRule, ",
        "PerConsume, RewardIntegral, OrganizationId",
        "from marketing_integral_rule_product",
        "where ProductId = #{productId} and OrganizationId=#{organizationId}"
    })
	IntegralRuleProduct selectByProductIdAndOrgId(@Param("productId")String productId, @Param("organizationId")String organizationId);

    @Select({
        "select",
        " ProductId",
        " from marketing_integral_rule_product",
        " where  OrganizationId=#{organizationId}"
    })
	List<String> selectProductIdsByOrgId(@Param("organizationId")String organizationId);

    @Select(startScript
         +" select"
         +" Id, IntegralRuleId, ProductId, ProductName, ProductPrice, MemberType, RewardRule, "
         +" PerConsume, RewardIntegral, OrganizationId"
         +" from marketing_integral_rule_product"
         +" where ProductId in"
         +" <foreach item='item' collection='productIds' open='(' separator=',' close=')'>" 
         + "   #{item,jdbcType=VARCHAR}" 
         + "</foreach>"
         +endScript)
	List<IntegralRuleProduct> selectByProductIdsAndOrgId(@Param("productIds")List<String> productIds, @Param("organizationId")String organizationId);

    @Select("select count(*) from marketing_integral_rule_product where ProductId = #{productId}")
    long countProductId(@Param("productId") String productId);

    
}