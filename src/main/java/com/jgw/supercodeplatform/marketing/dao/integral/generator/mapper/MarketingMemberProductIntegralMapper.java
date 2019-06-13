package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.MarketingMemberProductIntegralSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberProductIntegral;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface MarketingMemberProductIntegralMapper {
    @Delete({
        "delete from marketing_member_product_integral",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_member_product_integral (Id, MemberId, ",
        "ProductId, ProductBatchId, ",
        "SbatchId, AccrueIntegral, ",
        "OrganizationId, OrganizationName, ",
        "CreateTime, UpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{memberId,jdbcType=BIGINT}, ",
        "#{productId,jdbcType=VARCHAR}, #{productBatchId,jdbcType=VARCHAR}, ",
        "#{sbatchId,jdbcType=VARCHAR}, #{accrueIntegral,jdbcType=BIGINT}, ",
        "#{organizationId,jdbcType=VARCHAR}, #{organizationName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    
    int insert(MarketingMemberProductIntegral record);

    @InsertProvider(type=MarketingMemberProductIntegralSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys=true,keyColumn="Id",keyProperty="id")
    int insertSelective(MarketingMemberProductIntegral record);

    @Select({
        "select",
        "Id, MemberId, ProductId, ProductBatchId, SbatchId, AccrueIntegral, OrganizationId, ",
        "OrganizationName, CreateTime, UpdateTime",
        "from marketing_member_product_integral",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductBatchId", property="productBatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="SbatchId", property="sbatchId", jdbcType=JdbcType.VARCHAR),
        @Result(column="AccrueIntegral", property="accrueIntegral", jdbcType=JdbcType.BIGINT),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="OrganizationName", property="organizationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="CreateTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="UpdateTime", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MarketingMemberProductIntegral selectByPrimaryKey(Long id);

    @UpdateProvider(type=MarketingMemberProductIntegralSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MarketingMemberProductIntegral record);

    @Update({
        "update marketing_member_product_integral",
        "set MemberId = #{memberId,jdbcType=BIGINT},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "ProductBatchId = #{productBatchId,jdbcType=VARCHAR},",
          "SbatchId = #{sbatchId,jdbcType=VARCHAR},",
          "AccrueIntegral = #{accrueIntegral,jdbcType=BIGINT},",
          "OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "OrganizationName = #{organizationName,jdbcType=VARCHAR},",
          "CreateTime = #{createTime,jdbcType=TIMESTAMP},",
          "UpdateTime = #{updateTime,jdbcType=TIMESTAMP}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MarketingMemberProductIntegral record);
}