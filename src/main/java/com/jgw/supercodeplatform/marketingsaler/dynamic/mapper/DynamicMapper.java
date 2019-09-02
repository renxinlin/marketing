package com.jgw.supercodeplatform.marketingsaler.dynamic.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 动态表
 */
public interface DynamicMapper {

    @Update("CREATE TABLE `jgw_marketing_dynamic`.`${tableName}`  ( " +
            " `Id` bigint(20) NOT NULL, " +
            " `CreateDate` datetime(0) NULL, " +
            " PRIMARY KEY (`id`) " +
            " )")
    int createTable(@Param("tableName")String tableName);
}
