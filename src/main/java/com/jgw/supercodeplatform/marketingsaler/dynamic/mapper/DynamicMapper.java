package com.jgw.supercodeplatform.marketingsaler.dynamic.mapper;

import com.jgw.supercodeplatform.marketing.dao.CommonSql;
import com.jgw.supercodeplatform.marketing.pojo.MarketingOrganizationPortrait;
import com.jgw.supercodeplatform.marketingsaler.order.dto.ColumnnameAndValueDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 动态表
 */
public interface DynamicMapper extends CommonSql {

    @Update(startScript +
            " CREATE TABLE `jgw_marketing_dynamic`.`${tableName}`  ( " +
            " `Id` bigint(20) NOT NULL AUTO_INCREMENT , " +
            " <foreach collection='list' item='item' index='index'  open='  ' close='  ' separator=' ' > " +
            "  ${item} varchar(255) NULL ,  " +
            "  </foreach>  " +
            " PRIMARY KEY (`id`)  " +
            " )"
            + endScript)
    int createTable(@Param("tableName") String tableName, @Param("list") List<String> list);



    @Update(startScript
            + " ALTER TABLE ${tableName} "
            + " <if  test='list !=null and list.size() > 0 ' > "
            + " <foreach collection='list' item='item' index='index'  open='  ' close='  ' separator=',' > "
            + " drop COLUMN ${item} "
            + " </foreach> "
            + " </if> "
            + " <if  test='list1 !=null and list1.size() > 0 ' > "
            + " <if  test='list !=null and list.size() > 0 and list1 !=null and list1.size() > 0 ' > "
            + " , "
            + " </if> "

            + " <foreach collection='list1' item='item' index='index'  open='  ' close='  ' separator=',' >  "
            + " ADD COLUMN ${item} varchar(255) NULL "
            + " </foreach> "
            + " </if> "

            + endScript)
    int alterTableAndDropOrAddColumns(@Param("tableName") String tableName, @Param("list") List<String> deleteColumnNames,@Param("list1") List<String> addcolumnNames);

    @Select(" select count(1) from ${tableName}")
    int selectCount(@Param("tableName") String tableName);

    @Select(" select * from ${tableName} limit #{current},#{pageSize}")
    List<Map<String, Object>> selectPageData(@Param("tableName") String tableName,@Param("current") int current,@Param("pageSize")int pageSize);
    @Insert(startScript
            + " insert into ${tableName} "
            + " <foreach collection='columnnameAndValues' item='item' index='index'  open=' ( ' close=' ) ' separator=',' >  "
            + " ${item.columnName} "
            + " </foreach> "
            + " values "
            + " <foreach collection='columnnameAndValues' item='item' index='index'  open=' ( ' close=' ) ' separator=',' >  "
            + " #{item.columnValue} "
            + " </foreach> "
            + endScript)
    void saveOrder(@Param("columnnameAndValues") List<ColumnnameAndValueDto> columnnameAndValues,@Param("tableName") String tableName);
}
