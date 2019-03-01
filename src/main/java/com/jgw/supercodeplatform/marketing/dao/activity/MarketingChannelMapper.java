package com.jgw.supercodeplatform.marketing.dao.activity;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingChannel;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MarketingChannelMapper {


	@Insert({
			"<script>",
			"INSERT INTO marketing_channel(ActivitySetId,CustomerName,CustomerCode,CustomerType,CustomerSuperiorType,CustomerSuperior ) VALUES ",
			"<foreach collection='mList' item='mChannel' index='index' separator=','>",
			"(#{mChannel.activitySetId},#{mChannel.customerName},#{mChannel.customerCode},#{mChannel.customerType},#{mChannel.customerSuperiorType},#{mChannel.customerSuperior})",
			"</foreach>",
			"</script>"
	})
	void batchInsert(@Param(value="mList")List<MarketingChannel> mList);

}
