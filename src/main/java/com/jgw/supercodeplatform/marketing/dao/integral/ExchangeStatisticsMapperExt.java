package com.jgw.supercodeplatform.marketing.dao.integral;

import com.jgw.supercodeplatform.marketing.dao.integral.generator.mapper.ExchangeStatisticsMapper;
import com.jgw.supercodeplatform.marketing.pojo.integral.ExchangeStatistics;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ExchangeStatisticsMapperExt extends ExchangeStatisticsMapper {

    static String allFeilds = " Id id, OrganizationId organizationId, ProductId productId, MemberId memberId, ExchangeNum exchangeNum ";



    @Insert(" insert into marketing_member_exchange_statistics (OrganizationId,ProductId,MemberId,ExchangeNum) " +
            " values (#{organizationId},#{productId},#{memberId},#{exchangeNum}) on duplicate key update ExchangeNum= #{exchangeNum};")
    int updateCount(ExchangeStatistics exchangeStatistics);
    @Select(" select " + allFeilds + " from marketing_member_exchange_statistics mmes where mmes.OrganizationId = #{organizationId} and  mmes.MemberId = #{memberId} and  mmes.ProductId = #{productId} ")
    ExchangeStatistics selectCount(String organizationId, String productId, Long memberId);
    @Delete(" delete from  marketing_member_exchange_statistics  where ProductId = #{productId} ")
    int deleteWhernBaseServiceDelete(@Param("productId") String productId);
    
    @Update("update marketing_member_exchange_statistics set MemberId=#{newMemberId} where MemberId=#{oldMemberId} ")
	void updateMemberId(@Param("oldMemberId")Long oldMemberId, @Param("newMemberId")Long newMemberId);
}
