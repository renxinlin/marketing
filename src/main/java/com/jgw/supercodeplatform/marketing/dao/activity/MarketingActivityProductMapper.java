package com.jgw.supercodeplatform.marketing.dao.activity;

import org.apache.ibatis.annotations.Mapper;

import com.jgw.supercodeplatform.marketing.pojo.MarketingActivityProduct;

@Mapper
public interface MarketingActivityProductMapper {
    
	MarketingActivityProduct selectByProductAndProductBatchId(String productId, String productBatchId);

}
