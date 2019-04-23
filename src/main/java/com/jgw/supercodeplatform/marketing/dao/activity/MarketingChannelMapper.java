package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketingChannelMapper {
   static String allFields="Id id,ActivitySetId activitySetId,CustomerName customerName,CustomerId customerId,CustomerType customerType"
   		+ ",CustomerSuperiorType customerSuperiorType,CustomerSuperior customerSuperior";

	@Insert({
			"<script>",
			"INSERT INTO marketing_channel(ActivitySetId,CustomerName,CustomerId,CustomerType,CustomerSuperiorType,CustomerSuperior ) VALUES ",
			"<foreach collection='mList' item='mChannel' index='index' separator=','>",
			"(#{mChannel.activitySetId},#{mChannel.customerName},#{mChannel.customerId},#{mChannel.customerType},#{mChannel.customerSuperiorType},#{mChannel.customerSuperior})",
			"</foreach>",
			"</script>"
	})
	void batchInsert(@Param(value="mList")List<MarketingChannel> mList);

	@Select("select "+allFields+" from marketing_channel where ActivitySetId=#{activitySetId}")
	List<MarketingChannel> selectByActivitySetId(@Param("activitySetId")Long activitySetId);
}
