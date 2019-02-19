package com.jgw.supercodeplatform.marketing.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestTableMapper {

	
	
	@Insert(" insert into ${tableName}(GlobalBatchId,CodeTypeId,SBatchId,SGlobalUniqueInnerId,EGlobalUniqueInnerId,"
            + " SGlobalUniqueOuterId,EGlobalUniqueOuterId,InnerOwnerAccount,AppId,InnerUserAccount,Slevel,Totalcount,SOutId,"
            + " EOutId,ApplyTime)"
            + " values"
            + " (#{globalBatchId},#{codeTypeId},#{sBatchId},#{sGlobalUniqueInnerId},"
            + " #{eGlobalUniqueInnerId},#{sGlobalUniqueOuterId},#{eGlobalUniqueOuterId},#{innerOwnerAccount},"
            + " #{appId},#{innerUserAccount},#{slevel},#{totalCount},#{sOutId},#{eOutId},#{applyTime})"
    )
	Integer insertTestDatad(int i, String tableName, String dbName);
	
	
	
}
