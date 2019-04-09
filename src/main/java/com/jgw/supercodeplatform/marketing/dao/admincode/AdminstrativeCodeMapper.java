package com.jgw.supercodeplatform.marketing.dao.admincode;


import com.jgw.supercodeplatform.marketing.pojo.admincode.MarketingAdministrativeCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminstrativeCodeMapper {
    String selectSql = " AreaCode as areaCode,CityName as cityName,ParentAreaCode as parentAreaCode ";


    /**
     * 查询省市区
     * @param map
     * @return
     */
    @Select(" <script>"
            + " SELECT  "+selectSql+"  FROM marketing_administrative_code "
            + " <where> "
            + " <if test='areaCode !=null and areaCode != &apos;&apos; '> and AreaCode = #{areaCode} </if>"
            + " <if test='cityName !=null and cityName != &apos;&apos; '> and CityName = #{cityName} </if> "
            + " <if test='parentAreaCode !=null and parentAreaCode != &apos;&apos; '> and ParentAreaCode = #{parentAreaCode} </if>"
            + " </where>"
            + " </script>")
    MarketingAdministrativeCode getAdminCodeByAreaCode (Map<String,Object> map);

    @Select(  " <script> "
            + " SELECT  "+selectSql+"  FROM marketing_administrative_code where id in "
            + " <foreach collection='areaCodes' index='index' item='item' open='(' separator=',' close=')'> #{item} </foreach> "
            + " </script>")
    List<MarketingAdministrativeCode> getCodesName (List areaCodes);
  }
