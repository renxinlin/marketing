package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketingActivityProductMapper {

	String selectSql = " Id as id, ActivitySetId as activitySetId,CodeType as codeType,ProductBatchId as productBatchId,"
			+ " ProductBatchName as productBatchName,ProductId as productId,"
			+ " ProductName as productName,CodeTotalAmount as codeTotalAmount,CreateDate createDate,UpdateDate updateDate";



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



	@Update(" <script>"
			+ " UPDATE marketing_activity_product "
			+ " <set>"
			+ " <if test='codeTotalAmount !=null '> CodeTotalAmount = #{codeTotalAmount} ,</if> "
			+ " UpdateDate=now(),"
			+ " </set>"
			+ " <where> "
			+ " Id = #{id} "
			+ " </where>"
			+ " </script>")
	void updateCodeTotalAmount(@Param("codeTotalAmount")Long sum, @Param("id")Long id);

	@Select("SELECT  ap.ProductBatchId FROM marketing_activity_product ap left join marketing_activity_set aset on ap.ActivitySetId=aset.Id WHERE aset.OrganizationId = #{organizationId} ")
	List<String> usedProductBatchIds(@Param("organizationId")String organizationId);
}
