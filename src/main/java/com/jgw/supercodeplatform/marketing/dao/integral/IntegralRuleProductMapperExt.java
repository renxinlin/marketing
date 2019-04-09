package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.common.page.DaoSearch;
import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.IntegralRuleProductMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.IntegralRuleProduct;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface  IntegralRuleProductMapperExt extends IntegralRuleProductMapper,CommonSql {

	
    @Select(startScript
       + "select"
       +" Id, IntegralRuleId, ProductId, ProductName, ProductPrice, MemberType, RewardRule, "
       + " PerConsume, RewardIntegral,OrganizationId"
       +" from marketing_integral_rule_product"
       +startWhere
       +"<if test='organizationId!=null and organizationId!=&apos;&apos;'> OrganizationId=#{organizationId}</if>"
       +search
       +endWhere
       +" order by Id desc"
       +page
       +endScript
    )
	List<IntegralRuleProduct> list(DaoSearch searchParams, String organizationId);

    @Select(startScript
    	       + "select"
    	       +" count(*)"
    	       +" from marketing_integral_rule_product"
    	       +startWhere
    	       +"<if test='organizationId!=null and organizationId!=&apos;&apos;'> OrganizationId=#{organizationId}</if>"
    	       +search
    	       +endWhere
    	       +endScript
    	    )
	int count(DaoSearch searchParams);

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
	        +"(#{item.productId,jdbcType=VARCHAR}, #{item.productName,jdbcType=VARCHAR}, "
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
    
    
    
}