package com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.provider.MarketingMemberProductIntegralSqlProvider;
import com.jgw.supercodeplatform.marketing.pojo.integral.MarketingMemberProductIntegral;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;

public interface MarketingMemberProductIntegralMapper {
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
    int insertSelective(MarketingMemberProductIntegral record);
}