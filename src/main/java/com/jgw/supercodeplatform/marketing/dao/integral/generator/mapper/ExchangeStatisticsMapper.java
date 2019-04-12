package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.ExchangeStatisticsSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.ExchangeStatistics;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
public interface ExchangeStatisticsMapper {
    @Delete({
        "delete from marketing_member_exchange_statistics",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into marketing_member_exchange_statistics (Id, OrganizationId, ",
        "ProductId, MemberId, ",
        "ExchangeNum)",
        "values (#{id,jdbcType=BIGINT}, #{organizationId,jdbcType=VARCHAR}, ",
        "#{productId,jdbcType=VARCHAR}, #{memberId,jdbcType=BIGINT}, ",
        "#{exchangeNum,jdbcType=INTEGER})"
    })
    int insert(ExchangeStatistics record);

    @InsertProvider(type= ExchangeStatisticsSqlProvider.class, method="insertSelective")
    int insertSelective(ExchangeStatistics record);

    @Select({
        "select",
        "Id, OrganizationId, ProductId, MemberId, ExchangeNum",
        "from marketing_member_exchange_statistics",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="Id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="OrganizationId", property="organizationId", jdbcType=JdbcType.VARCHAR),
        @Result(column="ProductId", property="productId", jdbcType=JdbcType.VARCHAR),
        @Result(column="MemberId", property="memberId", jdbcType=JdbcType.BIGINT),
        @Result(column="ExchangeNum", property="exchangeNum", jdbcType=JdbcType.INTEGER)
    })
    ExchangeStatistics selectByPrimaryKey(Long id);

    @UpdateProvider(type=ExchangeStatisticsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ExchangeStatistics record);

    @Update({
        "update marketing_member_exchange_statistics",
        "set OrganizationId = #{organizationId,jdbcType=VARCHAR},",
          "ProductId = #{productId,jdbcType=VARCHAR},",
          "MemberId = #{memberId,jdbcType=BIGINT},",
          "ExchangeNum = #{exchangeNum,jdbcType=INTEGER}",
        "where Id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(ExchangeStatistics record);
}