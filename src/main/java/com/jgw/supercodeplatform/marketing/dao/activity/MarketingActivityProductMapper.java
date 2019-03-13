package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MarketingActivityProductMapper {

	String selectSql = " Id as id, ActivitySetId as activitySetId,CodeType as codeType,ProductBatchId as productBatchId,"
			+ " ProductBatchName as productBatchName,ProductId as productId,"
			+ " ProductName as productName,CodeTotalAmount as codeTotalAmount";



	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ProductBatchId = #{productBatchId}")
	MarketingActivityProduct selectByProductAndProductBatchId(@Param("productId") String productId,@Param("productBatchId") String productBatchId);


	@Insert({
			"<script>",
			"INSERT INTO marketing_activity_product(ActivitySetId,CodeType,ProductBatchId,ProductBatchName,ProductId,ProductName,CodeTotalAmount) VALUES ",
			"<foreach collection='mList' item='mProduct' index='index' separator=','>",
			"(#{mProduct.activitySetId},#{mProduct.codeType},#{mProduct.productBatchId},#{mProduct.productBatchName},#{mProduct.productId},#{mProduct.productName},#{mProduct.codeTotalAmount})",
			"</foreach>",
			"</script>"
	})
	void activityProductInsert(@Param(value="mList") List<MarketingActivityProduct> mList);

	
	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ActivitySetId = #{activitySetId} ")
	List<MarketingActivityProduct> selectByActivitySetId(@Param("activitySetId") String activitySetId);
}
