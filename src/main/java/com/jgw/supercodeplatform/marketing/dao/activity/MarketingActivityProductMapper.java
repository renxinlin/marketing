package com.jgw.supercodeplatform.marketing.dao.activity;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.dto.activity.MarketingActivityProductParam;
import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;
import com.jgw.supercodeplatform.marketing.pojo.MarketingPrizewheelsProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface MarketingActivityProductMapper extends CommonSql{

	String selectSql = " Id as id, ActivitySetId as activitySetId,CodeType as codeType,ProductBatchId as productBatchId,"
			+ " ProductBatchName as productBatchName,ProductId as productId,ReferenceRole referenceRole,"
			+ " ProductName as productName,CodeTotalAmount as codeTotalAmount,CreateDate as createDate,UpdateDate as updateDate,SbatchId as sbatchId";




	@Select({startScript,
			" select "+selectSql+" from marketing_activity_product where ReferenceRole= #{referenceRole} and ProductId=#{productId} and ",
			"<if test = 'productBatchId != null' >",
			" ProductBatchId = #{productBatchId}",
			"</if>",
			"<if test = 'productBatchId == null' >",
			" ProductBatchId IS NULL ",
			"</if>",
			" and ReferenceRole=#{referenceRole}",
			endScript})
//	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ProductBatchId = #{productBatchId} and ReferenceRole=#{referenceRole}")
	MarketingActivityProduct selectByProductAndProductBatchIdWithReferenceRole(@Param("productId") String productId,@Param("productBatchId") String productBatchId,@Param("referenceRole") byte referenceRole);


	@Select({startScript,
			"SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ",
			"<if test = 'productBatchId != null' >",
			" ProductBatchId = #{productBatchId}",
			"</if>",
			"<if test = 'productBatchId == null' >",
			" ProductBatchId IS NULL ",
			"</if> ",
			" and ReferenceRole=#{referenceRole} and ActivitySetId = #{activitySetId}",
			endScript})
	MarketingActivityProduct selectByProductAndProductBatchIdWithReferenceRoleAndSetId(@Param("productId") String productId,@Param("productBatchId") String productBatchId,@Param("referenceRole") byte referenceRole,@Param("activitySetId") Long activitySetId);


	@Insert({
			"<script>",
			"INSERT INTO marketing_activity_product(ActivitySetId,CodeType,ProductBatchId,ProductBatchName,ProductId,ProductName,CodeTotalAmount,ReferenceRole,SbatchId) VALUES ",
			"<foreach collection='mList' item='mProduct' index='index' separator=','>",
			"(#{mProduct.activitySetId},#{mProduct.codeType},#{mProduct.productBatchId},#{mProduct.productBatchName},#{mProduct.productId},#{mProduct.productName},#{mProduct.codeTotalAmount},#{mProduct.referenceRole},#{mProduct.sbatchId})",
			"</foreach>",
			"</script>"
	})
	void activityProductInsert(@Param(value="mList") List<MarketingActivityProduct> mList);

	
	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ActivitySetId = #{activitySetId} ")
	List<MarketingActivityProduct> selectByActivitySetId(@Param("activitySetId") Long activitySetId);



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

    @Delete(startScript +
    		"delete FROM marketing_activity_product where ReferenceRole=#{referenceRole} and " +
			" <foreach item='product' collection='list' separator='or' open='(' close=')'>"+
			" (ProductId=#{product.productId} and (ProductBatchId = '' or ProductBatchId IS NULL "+
			" <if test = 'product.productBatchId != null and product.productBatchId !=\"\"' > or ProductBatchId=#{product.productBatchId}</if> )" +
			" )</foreach>"+
    		endScript
    		)
	void batchDeleteByProBatchsAndRole( @Param(value="list")List<MarketingActivityProduct> mList, @Param(value="referenceRole")int referenceRole);

	@Select(startScript+
			" select "+selectSql+" from marketing_activity_product where "+
			" ProductId=#{productId} and (ProductBatchId = '' or ProductBatchId IS NULL "+
			" <if test = 'productBatchId != null and productBatchId !=\"\"' > or ProductBatchId=#{productBatchId} </if> )" +
			endScript)
//    @Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ProductBatchId = #{productBatchId}")
	List<MarketingActivityProduct> selectByProductAndProductBatchId(@Param("productId") String productId,@Param("productBatchId") String productBatchId);

    @Delete(" delete from marketing_activity_product where ActivitySetId = #{activitySetId}  ")
	void deleteByActivitySetId(Long id);

	/**
	 * 删除导购产品
	 * @param maProductParams
	 * @return
	 */
	@Delete( startScript +
			 " delete from marketing_activity_product where ReferenceRole = 1 and ProductId in " +
			 " <foreach collection='list' item='item' index='index' open='(' separator=',' close=')'> #{item.id}</foreach> "+
			 endScript )
    int deleteOldProducts(@Param("list") List<MarketingActivityProductParam> maProductParams);
	@Update(" update marketing_activity_product set SbatchId = #{marketingActivityProduct.sbatchId} where id = #{marketingActivityProduct.id}")
	int updateWhenAutoFetch(@Param("marketingActivityProduct") MarketingActivityProduct marketingActivityProduct);

	@Select("SELECT "+selectSql+" FROM marketing_activity_product  WHERE ProductId = #{productId} AND ReferenceRole=#{referenceRole}")
	List<MarketingActivityProduct> selectByProductWithReferenceRole(@Param("productId") String productId, @Param("referenceRole") byte referenceRole);

	@Select(startScript+
		" select "+selectSql+" from marketing_activity_product where ReferenceRole= #{referenceRole} and "+
		" <foreach item='product' collection='list' separator='or' open='(' close=')'>"+
		" (ProductId=#{product.productId} and (ProductBatchId = '' or ProductBatchId IS NULL "+
		" <if test = 'product.productBatchId != null and product.productBatchId !=\"\"' > or ProductBatchId=#{product.productBatchId} </if> )" +
		" )</foreach>"+
		endScript)
	List<MarketingActivityProduct> selectByProductAndBatch(@Param("list") List<MarketingActivityProduct> mList, @Param("referenceRole")int referenceRole);

	@Select(startScript+
			" select "+selectSql+" from marketing_activity_product where ReferenceRole=0 and "+
			" <foreach item='batchId' collection='set' separator='or' open='(' close=')'>"+
			" SbatchId LIKE CONCAT('%', #{batchId}, '%') "+
			" </foreach>"+
			endScript)
	List<MarketingActivityProduct> selectByBatchIds(@Param("set") Set<String> batchIds);


	@Select(startScript+
			" select * from marketing_activity_product where " +
			" type = #{type} " +
			" AND AutoType = #{auto} " +
			" AND ProductBatchId = #{strProductBatchId} " +
			" AND ProductId =#{strProductId}   "+
			endScript)
	MarketingPrizewheelsProduct selectNeedAutoFetchPrizewheels(String strProductId, String strProductBatchId, int auto, int type);
}
