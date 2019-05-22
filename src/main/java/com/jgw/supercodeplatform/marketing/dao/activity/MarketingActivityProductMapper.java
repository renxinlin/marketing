package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MarketingActivityProductMapper extends CommonSql{

	String selectSql = " Id as id, ActivitySetId as activitySetId,CodeType as codeType,ProductBatchId as productBatchId,"
			+ " ProductBatchName as productBatchName,ProductId as productId,ReferenceRole referenceRole,"
			+ " ProductName as productName,CodeTotalAmount as codeTotalAmount,CreateDate createDate,UpdateDate updateDate";



	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ProductBatchId = #{productBatchId} and ReferenceRole=#{referenceRole}")
	MarketingActivityProduct selectByProductAndProductBatchIdWithReferenceRole(@Param("productId") String productId,@Param("productBatchId") String productBatchId,@Param("referenceRole") byte referenceRole);


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

    @Delete(startScript
    		+"delete FROM marketing_activity_product where ReferenceRole=#{referenceRole} and ("
    		+" <foreach item='item' collection='list' separator='or' open='(' close=')' index=''>" + 
    		"   ProductId=#{item.productId} and ProductBatchId=#{item.productBatchId}" + 
    		" </foreach>"
    		+")"
    		+endScript
    		)
	void batchDeleteByProBatchsAndRole( @Param(value="list")List<MarketingActivityProduct> mList, @Param(value="referenceRole")int referenceRole);

    @Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ProductBatchId = #{productBatchId}")
	List<MarketingActivityProduct> selectByProductAndProductBatchId(@Param("productId") String productId,@Param("productBatchId") String productBatchId);

    @Delete(" delete from marketing_activity_product where ActivitySetId = #{activitySetId}  ")
	void deleteByActivitySetId(Long id);
}
